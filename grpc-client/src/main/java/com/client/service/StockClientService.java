package com.client.service;

import com.grpc.GreeterGrpc;
import com.grpc.StockRequest;
import com.grpc.StockResponse;
import com.grpc.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockClientService {

    @GrpcClient("develop-client")
    private StockTradingServiceGrpc.StockTradingServiceBlockingStub blockingStub;

    @GrpcClient("develop-client")
    private StockTradingServiceGrpc.StockTradingServiceStub serviceStub;

    public StockResponse getStockPrice(String symbol){
        StockRequest request = StockRequest.newBuilder().setStockSymbol(symbol).build();

        return blockingStub.getStockPrice(request);
    }

    public void subscribeStockPrice(String symbol){
        StockRequest request = StockRequest.newBuilder()
                .setStockSymbol(symbol)
                .build();


        serviceStub.subscribeStockPrice(request, new StreamObserver<StockResponse>() {
            @Override
            public void onNext(StockResponse stockResponse) {
                System.out.println("Stock Price Update: " + stockResponse.getStockSymbol() +
                        " Price: " + stockResponse.getPrice() + " " +
                        " Time: " + stockResponse.getTimestamp());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error : " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("stock price stream live update completed !");
            }
        });
    }

}
