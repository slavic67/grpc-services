package com.server_demo.service;

import com.grpc.GreeterGrpc;
import com.grpc.HelloWorldProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloWorldService
        extends GreeterGrpc.GreeterImplBase
{

    @Override
    public void sayHello(HelloWorldProto.HelloRequest request, StreamObserver<HelloWorldProto.HelloReply> responseObserver) {
        String responseMessage = "Hello " + request.getName() + "!";
        HelloWorldProto.HelloReply reply = HelloWorldProto.HelloReply.newBuilder()
                .setMessage(responseMessage)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}