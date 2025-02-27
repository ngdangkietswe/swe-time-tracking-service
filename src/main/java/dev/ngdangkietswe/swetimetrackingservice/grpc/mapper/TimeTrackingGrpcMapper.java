package dev.ngdangkietswe.swetimetrackingservice.grpc.mapper;

import dev.ngdangkietswe.swejavacommonshared.utils.DateTimeUtil;
import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcMapper;
import dev.ngdangkietswe.sweprotobufshared.timetracking.protobuf.TimeTracking;
import dev.ngdangkietswe.sweprotobufshared.timetracking.protobuf.TimeTrackingStatus;
import dev.ngdangkietswe.swetimetrackingservice.data.entity.TimeTrackingEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

@Component
public class TimeTrackingGrpcMapper extends GrpcMapper<TimeTrackingEntity, TimeTracking, TimeTracking.Builder> {

    @Override
    public TimeTracking.Builder toGrpcBuilder(TimeTrackingEntity from) {
        return TimeTracking.newBuilder()
                .setId(from.getId().toString())
                .setUserId(from.getUser().getId().toString())
                .setDate(from.getDate().toString())
                .setCheckInTime(DateTimeUtil.timestampAsStringWithDefaultZone(from.getCheckInTime()))
                .setCheckOutTime(DateTimeUtil.timestampAsStringWithDefaultZone(from.getCheckOutTime()))
                .setStatus(TimeTrackingStatus.forNumber(from.getStatus()))
                .setIsOvertime(from.isOvertime())
                .setOvertimeHours(ObjectUtils.defaultIfNull(from.getOvertimeHours(), 0.0))
                .setLatitude(ObjectUtils.defaultIfNull(from.getLatitude(), 0.0))
                .setLongitude(ObjectUtils.defaultIfNull(from.getLongitude(), 0.0))
                .setLocation(StringUtils.defaultString(from.getLocation()));
    }

    @Override
    public TimeTracking toGrpcMessage(TimeTrackingEntity from) {
        return toGrpcBuilder(from).build();
    }

    @Override
    public List<TimeTracking> toGrpcMessages(List<TimeTrackingEntity> from) {
        return super.toGrpcMessages(from);
    }
}
