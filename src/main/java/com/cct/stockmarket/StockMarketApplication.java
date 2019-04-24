package com.cct.stockmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author rbsrafa
 *
 */
@SpringBootApplication
@EnableJpaAuditing
public class StockMarketApplication {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    	SpringApplication.run(StockMarketApplication.class, args);
    }

}
