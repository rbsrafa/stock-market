package com.cct.stockmarket.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.payloads.SimulationRequest;
import com.cct.stockmarket.api.payloads.SimulationResponse;
import com.cct.stockmarket.simulation.Simulator;
import com.cct.stockmarket.simulation.generators.CompanyGenerator;
import com.cct.stockmarket.simulation.generators.InvestorGenerator;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {
	
	@Autowired
	Simulator simulator;

	@Autowired
	ApplicationContext context;
	
	@PostMapping("/run")
	public SimulationResponse runTradingDay(
		@Valid @RequestBody SimulationRequest settings
	) {
		
		// Generation process
		
		int numberOfInvestors = settings.getInvestorsQuantity();
    	int numberOfCompanies = settings.getCompaniesQuantity();
    	Float minBudget = (float)settings.getMinBudget();
    	Float maxBudget = (float)settings.getMaxBudget();
    	Float minSharePrice = settings.getMinSharePrice();
    	Float maxSharePrice = settings.getMaxSharePrice();
    	Integer minAmmountShares = settings.getMinAmmountShares();
    	Integer maxAmmountShares = settings.getMaxAmmountShares();
    	
    	List<Investor> investorList = InvestorGenerator
    		.generateInvestors(numberOfInvestors, minBudget, maxBudget);
    	
    	List<Company> companyList = CompanyGenerator
    		.generateCompanies(
    			numberOfCompanies, maxAmmountShares, 
    			minAmmountShares, maxSharePrice, minSharePrice
    		);
    	
    	// Simulation process
    	
    	Simulator simulator = context
			.getBean(Simulator.class, investorList, companyList);
    	
    	simulator.runTradingDay();
    	
    	// Relatories generation process
    	
		return simulator.getResults();
	}
	
}
