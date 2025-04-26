package com.samnart.billing_service.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(
        billing.BillingRequest billingRequest, 
        StreamObserver<billing.BillingResponse> responseObserver) {

            logger.info("createBillingAccount request received {}", billingRequest.toString());

            // Business logic - e.g save to database, perform calculations etc

            BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("1234")
                .setStatus("ACTIVE")
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
}
