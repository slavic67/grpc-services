package com.client.service;


import com.grpc.GreeterGrpc;
import com.grpc.HelloWorldProto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    @GrpcClient("hello-world-client")
    private GreeterGrpc.GreeterBlockingStub blockingStub;


    public String sayHello(String name) {
        HelloWorldProto.HelloRequest request = HelloWorldProto.HelloRequest.newBuilder()
                .setName(name)
                .build();

        HelloWorldProto.HelloReply reply = blockingStub.sayHello(request);

        return reply.getMessage();
    }
}
