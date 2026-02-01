package com.server_demo.repository;

import com.server_demo.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {

    Stock findByStockSymbol(String stockSymbol);
}
