package com.client;

import com.client.service.HelloWorldService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GrpcClientApplication {

	public static void main(String[] args) throws InterruptedException {

        ApplicationContext context = SpringApplication.run(GrpcClientApplication.class, args);

        HelloWorldService service = context.getBean(HelloWorldService.class);

        while (true) {
            String message = service.sayHello("tkach");
            System.out.println("Receive from server - " +message);
            Thread.sleep(1000);
        }

	}


}
