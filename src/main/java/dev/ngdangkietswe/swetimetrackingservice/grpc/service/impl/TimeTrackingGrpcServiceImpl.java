package dev.ngdangkietswe.swetimetrackingservice.grpc.service.impl;

import dev.ngdangkietswe.swejavacommonshared.utils.DateTimeUtil;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.domain.SweGrpcPrincipal;
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
import dev.ngdangkietswe.swetimetrackingservice.data.repository.jpa.TimeTrackingRepository;
import dev.ngdangkietswe.swetimetrackingservice.grpc.service.ITimeTrackingGrpcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class TimeTrackingGrpcServiceImpl implements ITimeTrackingGrpcService {

    private final TimeTrackingRepository timeTrackingRepository;

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

        timeTracking.setLatitude(req.getLatitude());
        timeTracking.setLongitude(req.getLongitude());
        timeTracking.setLocation(null); // TODO: Implement calculate lat/long to location

        timeTrackingRepository.save(timeTracking);

        return CheckInOutResp.newBuilder()
                .setSuccess(true)
                .setData(CheckInOutResp.Data.newBuilder()
                        .setId(timeTracking.getId().toString())
                        .setDate(timeTracking.getDate().toString())
                        .setCheckInTime(DateTimeUtil.timestampAsStringWithDefaultZone(timeTracking.getCheckInTime()))
                        .setCheckOutTime(DateTimeUtil.timestampAsStringWithDefaultZone(timeTracking.getCheckOutTime()))
                        .setStatus(TimeTrackingStatus.forNumber(timeTracking.getStatus()))
                        .build())
                .build();
    }

    @Override
    public GetListTimeTrackingResp getListTimeTracking(GetListTimeTrackingReq req, SweGrpcPrincipal principal) {
        return null;
    }

    @Override
    public GetTimeTrackingResp getTimeTracking(GetTimeTrackingReq req, SweGrpcPrincipal principal) {
        return null;
    }

    @Override
    public UpsertResp overtime(OverTimeReq req, SweGrpcPrincipal principal) {
        return null;
    }

    @Override
    public EmptyResp approveOvertime(ApproveOvertimeReq req, SweGrpcPrincipal principal) {
        return null;
    }
}
