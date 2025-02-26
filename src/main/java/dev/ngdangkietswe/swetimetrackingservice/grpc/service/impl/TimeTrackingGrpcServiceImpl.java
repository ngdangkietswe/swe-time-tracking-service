package dev.ngdangkietswe.swetimetrackingservice.grpc.service.impl;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.domain.SweGrpcPrincipal;
import dev.ngdangkietswe.sweprotobufshared.timetracking.ApproveOvertimeReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.CheckInOutResp;
import dev.ngdangkietswe.sweprotobufshared.timetracking.CheckInReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.CheckOutReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetListTimeTrackingReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetListTimeTrackingResp;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetTimeTrackingReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetTimeTrackingResp;
import dev.ngdangkietswe.sweprotobufshared.timetracking.OverTimeReq;
import dev.ngdangkietswe.swetimetrackingservice.grpc.service.ITimeTrackingGrpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

@Service
@RequiredArgsConstructor
public class TimeTrackingGrpcServiceImpl implements ITimeTrackingGrpcService {

    @Override
    public CheckInOutResp checkIn(CheckInReq req, SweGrpcPrincipal principal) {
        return null;
    }

    @Override
    public CheckInOutResp checkOut(CheckOutReq req, SweGrpcPrincipal principal) {
        return null;
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
