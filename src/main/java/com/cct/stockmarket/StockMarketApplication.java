package com.cct.stockmarket;

import com.cct.stockmarket.simulation.Simulator;
import com.cct.stockmarket.simulation.generators.CompanyGenerator;
import com.cct.stockmarket.simulation.generators.InvestorGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
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
    	ApplicationContext context = SpringApplication.run(StockMarketApplication.class, args);
    	
    	int numberOfInvestors = 5;
    	int numberOfCompanies = 5;
    	
    	Simulator simulator = context
			.getBean(
				Simulator.class, 
				InvestorGenerator.generateInvestors(numberOfInvestors), 
				CompanyGenerator.generateCompanies(numberOfCompanies)
			);
    	
    	simulator.runTradingDay();
    	
    }

}
