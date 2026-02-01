package com.server_demo.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name="stocks")
@Getter
@Setter
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="stock_symbol", unique=true, nullable=false)
    private String stockSymbol;

    private double price;

    @Column(name="last_updated")
    private LocalDateTime lastUpdated;

}
