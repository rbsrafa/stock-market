package com.cct.stockmarket;

import com.cct.stockmarket.simulation.Simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author rbsrafa
 *
 */
@SpringBootApplication
@EnableJpaAuditing
public class StockMarketApplication implements CommandLineRunner{

	@Autowired
	Simulator simulator;
    

    /* (non-Javadoc)
     * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
     */
    @Override   
    public void run(String... args) throws Exception {
    	simulator.runTradingDay();
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    	SpringApplication.run(StockMarketApplication.class, args);
    }

}
