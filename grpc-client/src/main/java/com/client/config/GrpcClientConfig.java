package com.client.config;

import com.grpc.GreeterGrpc;


import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@GrpcClientBean(
        client = @GrpcClient("myGrpcService"),
        clazz = GreeterGrpc.GreeterBlockingStub.class,
        beanName = "greeterBlockingStub"   // имя бина, по которому будем внедрять
)
public class GrpcClientConfig {

}