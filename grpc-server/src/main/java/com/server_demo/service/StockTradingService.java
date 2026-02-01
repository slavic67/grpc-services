package com.server_demo.service;


import com.grpc.StockRequest;
import com.grpc.StockResponse;
import com.grpc.StockTradingServiceGrpc;
import com.server_demo.entity.Stock;
import com.server_demo.repository.StockRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class StockTradingService extends StockTradingServiceGrpc.StockTradingServiceImplBase {

    private final StockRepository stockRepository;


    @Override
    public void getStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {

        String stockSymbol = request.getStockSymbol();
        Stock entity = stockRepository.findByStockSymbol(stockSymbol);

        StockResponse stockResponse = StockResponse.newBuilder()
                .setStockSymbol(entity.getStockSymbol())
                .setPrice(entity.getPrice())
                .setTimestamp(entity.getLastUpdated().toString())
                .build();

        responseObserver.onNext(stockResponse);
        responseObserver.onCompleted();
    }
}
