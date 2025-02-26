package dev.ngdangkietswe.swetimetrackingservice.grpc.validator.impl;

import com.google.rpc.Code;
import dev.ngdangkietswe.swejavacommonshared.constants.CommonConstant;
import dev.ngdangkietswe.swejavacommonshared.utils.ResponseUtil;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetListTimeTrackingReq;
import dev.ngdangkietswe.sweprotobufshared.timetracking.GetListTimeTrackingResp;
import dev.ngdangkietswe.sweprotobufshared.timetracking.OverTimeReq;
import dev.ngdangkietswe.swetimetrackingservice.grpc.validator.ITimeTrackingGrpcValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

@Component
public class TimeTrackingGrpcValidatorImpl implements ITimeTrackingGrpcValidator {

    @Override
    public GetListTimeTrackingResp.Builder validateGetListTimeTracking(GetListTimeTrackingReq req) {
        if (StringUtils.isNotEmpty(req.getStartDate()) && isInvalidDateFormat(req.getStartDate())) {
            return GetListTimeTrackingResp.newBuilder()
                    .setError(ResponseUtil.asFailedResponse(Code.INVALID_ARGUMENT_VALUE, "Invalid start date"));
        }

        if (StringUtils.isNotEmpty(req.getEndDate()) && isInvalidDateFormat(req.getEndDate())) {
            return GetListTimeTrackingResp.newBuilder()
                    .setError(ResponseUtil.asFailedResponse(Code.INVALID_ARGUMENT_VALUE, "Invalid end date"));
        }

        if (StringUtils.isNotEmpty(req.getStartDate()) && StringUtils.isNotEmpty(req.getEndDate()) && req.getStartDate().compareTo(req.getEndDate()) > 0) {
            return GetListTimeTrackingResp.newBuilder()
                    .setError(ResponseUtil.asFailedResponse(Code.INVALID_ARGUMENT_VALUE, "Start date must be before end date"));
        }

        return null;
    }

    @Override
    public UpsertResp.Builder validateOvertime(OverTimeReq req) {
        if (req.getOvertimeHours() <= 0) {
            return UpsertResp.newBuilder()
                    .setError(ResponseUtil.asFailedResponse(Code.INVALID_ARGUMENT_VALUE, "Overtime hours must be greater than 0"));
        }

        if (StringUtils.isEmpty(req.getReason())) {
            return UpsertResp.newBuilder()
                    .setError(ResponseUtil.asFailedResponse(Code.INVALID_ARGUMENT_VALUE, "Reason is required"));
        }

        if (StringUtils.isEmpty(req.getId())) {
            return UpsertResp.newBuilder()
                    .setError(ResponseUtil.asFailedResponse(Code.INVALID_ARGUMENT_VALUE, "Time tracking id is required"));
        }

        if (StringUtils.isEmpty(req.getApproverId())) {
            return UpsertResp.newBuilder()
                    .setError(ResponseUtil.asFailedResponse(Code.INVALID_ARGUMENT_VALUE, "Approver id is required"));
        }

        return null;
    }

    private boolean isInvalidDateFormat(String date) {
        return !date.matches(CommonConstant.YYYY_MM_DD_REGEX);
    }
}
