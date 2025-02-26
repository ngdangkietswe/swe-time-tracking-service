package dev.ngdangkietswe.swetimetrackingservice.grpc.service;

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

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

public interface ITimeTrackingGrpcService {

    CheckInOutResp checkIn(CheckInReq req, SweGrpcPrincipal principal);

    CheckInOutResp checkOut(CheckOutReq req, SweGrpcPrincipal principal);

    GetListTimeTrackingResp getListTimeTracking(GetListTimeTrackingReq req, SweGrpcPrincipal principal);

    GetTimeTrackingResp getTimeTracking(GetTimeTrackingReq req, SweGrpcPrincipal principal);

    UpsertResp overtime(OverTimeReq req, SweGrpcPrincipal principal);

    EmptyResp approveOvertime(ApproveOvertimeReq req, SweGrpcPrincipal principal);
}
