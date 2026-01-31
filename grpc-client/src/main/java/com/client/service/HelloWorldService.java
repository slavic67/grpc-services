package com.client.service;


import com.grpc.GreeterGrpc;
import com.grpc.HelloWorldProto;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {


    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public HelloWorldService(
            GreeterGrpc.GreeterBlockingStub greeterBlockingStub) {  // имя совпадает с beanName
        this.blockingStub = greeterBlockingStub;
    }



    public String sayHello(String name) {
        HelloWorldProto.HelloRequest request = HelloWorldProto.HelloRequest.newBuilder()
                .setName(name)
                .build();

        HelloWorldProto.HelloReply reply = blockingStub.sayHello(request);

        return reply.getMessage();
    }
}
