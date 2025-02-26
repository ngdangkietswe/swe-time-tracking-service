package dev.ngdangkietswe.swetimetrackingservice.grpc.validator;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetListTimeTrackingReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetListTimeTrackingResp;
import dev.ngdangkietswe.sweprotobufshared.timetracking.OverTimeReq;

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

public interface ITimeTrackingGrpcValidator {

    GetListTimeTrackingResp.Builder validateGetListTimeTracking(GetListTimeTrackingReq req);

    UpsertResp.Builder validateOvertime(OverTimeReq req);
}
