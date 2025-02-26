package dev.ngdangkietswe.swetimetrackingservice.grpc.server;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcUtil;
import dev.ngdangkietswe.sweprotobufshared.proto.common.IGrpcServer;
import dev.ngdangkietswe.sweprotobufshared.proto.security.SweGrpcServerInterceptor;
import dev.ngdangkietswe.sweprotobufshared.timetracking.*;
import dev.ngdangkietswe.swetimetrackingservice.grpc.service.impl.TimeTrackingGrpcServiceImpl;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

@GRpcService(interceptors = SweGrpcServerInterceptor.class)
@RequiredArgsConstructor
public class TimeTrackingGrpcServer extends TimeTrackingServiceGrpc.TimeTrackingServiceImplBase {

    private final TimeTrackingGrpcServiceImpl timeTrackingGrpcService;

    @Override
    public void checkIn(CheckInReq request, StreamObserver<CheckInOutResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                timeTrackingGrpcService::checkIn,
                exception -> CheckInOutResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    public void checkOut(CheckOutReq request, StreamObserver<CheckInOutResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                timeTrackingGrpcService::checkOut,
                exception -> CheckInOutResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    public void getTimeTracking(GetTimeTrackingReq request, StreamObserver<GetTimeTrackingResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                timeTrackingGrpcService::getTimeTracking,
                exception -> GetTimeTrackingResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    public void getListTimeTracking(GetListTimeTrackingReq request, StreamObserver<GetListTimeTrackingResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                timeTrackingGrpcService::getListTimeTracking,
                exception -> GetListTimeTrackingResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    public void overtime(OverTimeReq request, StreamObserver<UpsertResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                timeTrackingGrpcService::overtime,
                exception -> UpsertResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    public void approveOvertime(ApproveOvertimeReq request, StreamObserver<EmptyResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                timeTrackingGrpcService::approveOvertime,
                exception -> EmptyResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }
}
