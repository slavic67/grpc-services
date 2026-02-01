package com.client.service;

import com.grpc.GreeterGrpc;
import com.grpc.StockRequest;
import com.grpc.StockResponse;
import com.grpc.StockTradingServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class StockClientService {

    @GrpcClient("develop-client")
    private StockTradingServiceGrpc.StockTradingServiceBlockingStub blockingStub;

    public StockResponse getStockPrice(String symbol){
        StockRequest request = StockRequest.newBuilder().setStockSymbol(symbol).build();

        return blockingStub.getStockPrice(request);
    }


}
