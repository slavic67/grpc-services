package com.client.service;

import com.grpc.*;
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

    public void placeBulkOrders() {
        StreamObserver<OrderSummary> responseObserver = new StreamObserver<OrderSummary>() {
            @Override
            public void onNext(OrderSummary summary) {
                System.out.println("Order Summary Received from Server:");
                System.out.println("Total Orders: " + summary.getTotalOrders());
                System.out.println("Successful Orders: " + summary.getSuccessCount());
                System.out.println("Total Amount: $" + summary.getTotalAmount());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Order Summary Receivedn error from Server:" + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed , server is done sending summary !");
            }
        };

        StreamObserver<StockOrder> requestObserver = serviceStub.bulkStockOrder(responseObserver);

        try {

            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("1")
                    .setStockSymbol("AAPL")
                    .setOrderType("BUY")
                    .setPrice(150.5)
                    .setQuantity(10)
                    .build());

            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("2")
                    .setStockSymbol("GOOGL")
                    .setOrderType("SELL")
                    .setPrice(2700.0)
                    .setQuantity(5)
                    .build());

            requestObserver.onNext(StockOrder.newBuilder()
                    .setOrderId("3")
                    .setStockSymbol("TSLA")
                    .setOrderType("BUY")
                    .setPrice(700.0)
                    .setQuantity(8)
                    .build());

            //done sending orders
            requestObserver.onCompleted();
        } catch (Exception ex) {
            requestObserver.onError(ex);
        }

    }


    public void startLiveTrading() throws InterruptedException {
        StreamObserver<StockOrder> requestObserver = serviceStub.liveTrading(new StreamObserver<>() {

            @Override
            public void onNext(TradeStatus tradeStatus) {
                System.out.println("server response : " + tradeStatus);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error : " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("stream completed. ");
            }
        });

        //sending multiple order request from client

        for (int i = 1; i <= 10; i++) {
            StockOrder stockOrder = StockOrder.newBuilder()
                    .setOrderId("ORDER-" + i)
                    .setStockSymbol("APPL")
                    .setQuantity(i * 10)
                    .setPrice(150.0 + i)
                    .setOrderType("BUY")
                    .build();
            requestObserver.onNext(stockOrder);
            Thread.sleep(500);
        }
        requestObserver.onCompleted();
    }



}
