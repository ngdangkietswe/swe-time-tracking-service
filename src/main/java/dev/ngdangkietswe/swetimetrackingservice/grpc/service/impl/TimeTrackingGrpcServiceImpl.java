package dev.ngdangkietswe.swetimetrackingservice.grpc.service.impl;

import dev.ngdangkietswe.swejavacommonshared.constants.CommonConstant;
import dev.ngdangkietswe.swejavacommonshared.utils.DateTimeUtil;
import dev.ngdangkietswe.swejavacommonshared.utils.RestTemplateUtil;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.domain.SweGrpcPrincipal;
import dev.ngdangkietswe.sweprotobufshared.proto.exception.GrpcNotFoundException;
import dev.ngdangkietswe.sweprotobufshared.timetracking.ApproveOvertimeReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.CheckInOutReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.CheckInOutResp;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetListTimeTrackingReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetListTimeTrackingResp;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetTimeTrackingReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetTimeTrackingResp;
import dev.ngdangkietswe.sweprotobufshared.timetracking.OverTimeReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.protobuf.TimeTrackingStatus;
import dev.ngdangkietswe.swetimetrackingservice.data.entity.CdcAuthUserEntity;
import dev.ngdangkietswe.swetimetrackingservice.data.entity.TimeTrackingEntity;
import dev.ngdangkietswe.swetimetrackingservice.data.repository.jpa.CdcAuthUserRepository;
import dev.ngdangkietswe.swetimetrackingservice.data.repository.jpa.TimeTrackingRepository;
import dev.ngdangkietswe.swetimetrackingservice.grpc.mapper.TimeTrackingGrpcMapper;
import dev.ngdangkietswe.swetimetrackingservice.grpc.service.ITimeTrackingGrpcService;
import dev.ngdangkietswe.swetimetrackingservice.grpc.validator.ITimeTrackingGrpcValidator;
import dev.ngdangkietswe.swetimetrackingservice.kafka.payload.SendEmailReplyOvertimePayload;
import dev.ngdangkietswe.swetimetrackingservice.kafka.payload.SendEmailRequestOvertimePayload;
import dev.ngdangkietswe.swetimetrackingservice.kafka.producer.TimeTrackingKafkaProducer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class TimeTrackingGrpcServiceImpl implements ITimeTrackingGrpcService {

    private final TimeTrackingGrpcMapper timeTrackingGrpcMapper;
    private final ITimeTrackingGrpcValidator timeTrackingGrpcValidator;
    private final TimeTrackingRepository timeTrackingRepository;
    private final CdcAuthUserRepository cdcAuthUserRepository;

    private final TimeTrackingKafkaProducer timeTrackingKafkaProducer;
    private final RestTemplateUtil restTemplateUtil;

    @Getter
    @Setter
    public static class LocationResp {
        public double latitude;              // Latitude from the response
        public double longitude;             // Longitude from the response
        public String city;                  // City name (e.g., "New York")
        public String countryName;           // Country name (e.g., "United States")
        public String countryCode;           // ISO country code (e.g., "US")
        public String locality;              // More specific area (e.g., neighborhood)
        public String principalSubdivision;  // State or province (e.g., "New York")
        public String postcode;              // Postal code (e.g., "10007")

        /**
         * Get the full location string
         * Ex: "New York, New York, United States"
         *
         * @return Full location string
         */
        public String getFullLocation() {
            StringBuilder fullLocation = new StringBuilder();

            if (StringUtils.isNotEmpty(locality)) {
                fullLocation.append(locality);
            }

            if (StringUtils.isNotEmpty(city)) {
                fullLocation.append(", ").append(city);
            }

            if (StringUtils.isNotEmpty(countryName)) {
                fullLocation.append(", ").append(countryName);
            }

            return StringUtils.defaultIfEmpty(fullLocation.toString(), "Unknown location");
        }
    }

    /**
     * Check in/out
     *
     * @param req       CheckInOutReq
     * @param principal SweGrpcPrincipal
     * @return CheckInOutResp
     */
    @Override
    public CheckInOutResp checkInOut(CheckInOutReq req, SweGrpcPrincipal principal) {
        var userId = principal.getUserId();
        var date = Date.valueOf(LocalDate.now());
        var now = new Timestamp(System.currentTimeMillis());

        TimeTrackingEntity timeTracking = timeTrackingRepository.findByUserIdAndDateAndCheckInTimeIsNotNull(userId, date)
                .map(entity -> {
                    entity.setCheckOutTime(now);
                    entity.preUpdate(userId);
                    return entity;
                })
                .orElseGet(() -> {
                            var newTimeTracking = new TimeTrackingEntity();
                            newTimeTracking.setUser(CdcAuthUserEntity.builder().id(userId).build());
                            newTimeTracking.setDate(date);
                            newTimeTracking.setCheckInTime(now);
                            newTimeTracking.prePersist(userId);
                            return newTimeTracking;
                        }
                );

        // Get location information if latitude and longitude are provided
        if (req.getLatitude() > 0 && req.getLongitude() > 0) {
            timeTracking.setLatitude(req.getLatitude());
            timeTracking.setLongitude(req.getLongitude());

            var locationResp = restTemplateUtil.get(
                    String.format(CommonConstant.BIG_DATA_CLOUD_URL, req.getLatitude(), req.getLongitude()),
                    LocationResp.class
            );

            if (Objects.nonNull(locationResp.getBody())) {
                timeTracking.setLocation(locationResp.getBody().getFullLocation());
            }
        }

        timeTrackingRepository.save(timeTracking);

        return CheckInOutResp.newBuilder()
                .setSuccess(true)
                .setData(CheckInOutResp.Data.newBuilder()
                        .setId(timeTracking.getId().toString())
                        .setDate(timeTracking.getDate().toString())
                        .setCheckInTime(DateTimeUtil.timestampAsStringWithDefaultZone(timeTracking.getCheckInTime()))
                        .setCheckOutTime(DateTimeUtil.timestampAsStringWithDefaultZone(timeTracking.getCheckOutTime()))
                        .setStatus(TimeTrackingStatus.forNumber(timeTracking.getStatus()))
                        .setLocation(timeTracking.getLocation())
                        .build())
                .build();
    }

    /**
     * Get list time tracking
     *
     * @param req       GetListTimeTrackingReq
     * @param principal SweGrpcPrincipal
     * @return GetListTimeTrackingResp
     */
    @Override
    public GetListTimeTrackingResp getListTimeTracking(GetListTimeTrackingReq req, SweGrpcPrincipal principal) {
        var respBuilder = timeTrackingGrpcValidator.validateGetListTimeTracking(req);
        if (Objects.nonNull(respBuilder)) {
            return respBuilder.build();
        }

        var startDate = StringUtils.isNotEmpty(req.getStartDate())
                ? Date.valueOf(req.getStartDate())
                : DateTimeUtil.firstDateOfMonth();

        var endDate = StringUtils.isNotEmpty(req.getEndDate())
                ? Date.valueOf(req.getEndDate())
                : DateTimeUtil.lastDateOfMonth();

        var timeTrackings = timeTrackingRepository.findAllByUserIdAndDateIsGreaterThanEqualAndDateIsLessThanEqual(
                principal.getUserId(),
                startDate,
                endDate
        );

        return GetListTimeTrackingResp.newBuilder()
                .setSuccess(true)
                .setData(GetListTimeTrackingResp.Data.newBuilder()
                        .addAllTimeTrackings(timeTrackingGrpcMapper.toGrpcMessages(timeTrackings))
                        .build())
                .build();
    }

    @Override
    public GetTimeTrackingResp getTimeTracking(GetTimeTrackingReq req, SweGrpcPrincipal principal) {
        return null;
    }

    /**
     * Overtime
     *
     * @param req       OvertimeReq
     * @param principal SweGrpcPrincipal
     * @return UpsertResp
     */
    @Override
    public UpsertResp overtime(OverTimeReq req, SweGrpcPrincipal principal) {
        var respBuilder = timeTrackingGrpcValidator.validateOvertime(req);
        if (Objects.nonNull(respBuilder)) {
            return respBuilder.build();
        }

        var timeTracking = timeTrackingRepository.findByIdAndUserId(UUID.fromString(req.getId()), principal.getUserId())
                .orElseThrow(() -> new GrpcNotFoundException(TimeTrackingEntity.class, "id", req.getId()));

        var approver = cdcAuthUserRepository.findById(UUID.fromString(req.getApproverId()))
                .orElseThrow(() -> new GrpcNotFoundException(CdcAuthUserEntity.class, "id", req.getApproverId()));

        // Send email to approver for overtime request
        timeTrackingKafkaProducer.sendEmailRequestOvertime(SendEmailRequestOvertimePayload.builder()
                .date(timeTracking.getDate().toString())
                .requester(principal.getUsername())
                .approver(approver.getUsername())
                .approverEmail(approver.getEmail())
                .totalHours(req.getOvertimeHours())
                .reason(req.getReason())
                .build());

        return UpsertResp.newBuilder()
                .setSuccess(true)
                .setData(UpsertResp.Data.newBuilder()
                        .setId(timeTracking.getId().toString())
                        .build())
                .build();
    }

    @Override
    public EmptyResp approveOvertime(ApproveOvertimeReq req, SweGrpcPrincipal principal) {
        var timeTracking = timeTrackingRepository.findByIdFetchUser(UUID.fromString(req.getId()))
                .orElseThrow(() -> new GrpcNotFoundException(TimeTrackingEntity.class, "id", req.getId()));

        if (req.getIsApproved()) {
            timeTracking.setOvertime(true);
            timeTracking.setOvertimeHours(req.getOvertimeHours());
            timeTracking.preUpdate(principal.getUserId());
            timeTrackingRepository.save(timeTracking);
        }

        // Send email to requester for overtime request
        timeTrackingKafkaProducer.sendEmailReplyOvertime(SendEmailReplyOvertimePayload.builder()
                .date(timeTracking.getDate().toString())
                .approver(principal.getUsername())
                .requester(timeTracking.getUser().getUsername())
                .requesterEmail(timeTracking.getUser().getEmail())
                .isApproved(req.getIsApproved())
                .reason(req.getReason())
                .build());

        return EmptyResp.newBuilder()
                .setSuccess(true)
                .build();
    }
}
