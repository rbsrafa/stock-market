package com.cct.stockmarket.api.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.Simulation;
import com.cct.stockmarket.api.payloads.RelatoryResponse;
import com.cct.stockmarket.api.payloads.SimulationRequest;
import com.cct.stockmarket.api.payloads.SimulationResponse;
import com.cct.stockmarket.api.repositories.CompanyRepository;
import com.cct.stockmarket.api.repositories.InvestorRepository;
import com.cct.stockmarket.api.repositories.SimulationRepository;
import com.cct.stockmarket.simulation.Simulator;
import com.cct.stockmarket.simulation.generators.CompanyGenerator;
import com.cct.stockmarket.simulation.generators.InvestorGenerator;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {
	
	@Autowired
	Simulator simulator;
	
	@Autowired
	SimulationRepository simulations;
	
	@Autowired 
	CompanyRepository companies;
	
	@Autowired
	InvestorRepository investors;

	@Autowired
	ApplicationContext context;
	
        Sort sort = new Sort(new Sort.Order(Direction.ASC, "id"));
        
	List<RelatoryResponse> simulationRelatories = new ArrayList();
	
	
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
	
	@GetMapping("/relatories")
	public List<RelatoryResponse> getSimulationsRelatories(){
		this.simulationRelatories.clear();
		
		List<Simulation> simulationList = this.simulations.findAll(sort);
		System.out.println(simulationList);
		
		simulationList.forEach(simulation -> {
			List<Company> companyHighestCapital = this.companies.findCompanyWithHighestCapital(simulation.getId());
			List<Company> companyLowestCapital = this.companies.findCompanyWithLowestCapital(simulation.getId());
                        List<Investor> investorHighestNumberShares = this.investors.findInvestorWithHighestNumberShares(simulation.getId());
                        List<Investor> investorLowestNumberShares = this.investors.findInvestorWithLowestNumberShares(simulation.getId());
                        List<Investor> investorHighestNumberCompanies = this.investors.findInvestorWithHighestNumberCompanies(simulation.getId());
                        List<Investor> investorLowestNumberCompanies = this.investors.findInvestorWithLowestNumberCompanies(simulation.getId());
                        float highestCapital = companyHighestCapital.get(0).getSharePrice() * 
                                companyHighestCapital.get(0).getNumberOfShares();
                        float lowestCapital = companyLowestCapital.get(0).getSharePrice() * 
                                companyLowestCapital.get(0).getNumberOfShares();
                        
                        RelatoryResponse response = new RelatoryResponse();
			response.setSimulationId(simulation.getId());
			response.setNumberOfTransactions(simulation.getNumberOfTransactions());
			response.setCompanyHighestCapital(highestCapital);
			response.setCompaniesWithHighestCapital(companyHighestCapital);
			response.setCompanyLowestCapital(lowestCapital);
			response.setCompaniesWithLowestCapital(companyLowestCapital);
			response.setInvestorsWithHighestNumberOfShares(investorHighestNumberShares);
			response.setInvestorsWithLowestNumberOfShares(investorLowestNumberShares);
			response.setInvestorWithLeastNumberOfCompanies(investorHighestNumberCompanies);
			response.setInvestorWithLeastNumberOfCompanies(investorLowestNumberCompanies);
                        
                        this.simulationRelatories.add(response);
		});
		
		return this.simulationRelatories;
	}
	
	@GetMapping("/{id}/relatories")
	public RelatoryResponse getRelatoryBySimulationId(
		@PathVariable Long id
	) {
            
                System.out.println("BEFORE TESTEEEE!!!!  ");
		Simulation simulation = this.simulations.getOne(id);
                List<Company> companyHighestCapital = this.companies.findCompanyWithHighestCapital(simulation.getId());
                List<Company> companyLowestCapital = this.companies.findCompanyWithLowestCapital(simulation.getId());
                List<Investor> investorHighestNumberShares = this.investors.findInvestorWithHighestNumberShares(simulation.getId());
                List<Investor> investorLowestNumberShares = this.investors.findInvestorWithLowestNumberShares(simulation.getId());
                List<Investor> investorHighestNumberCompanies = this.investors.findInvestorWithHighestNumberCompanies(simulation.getId());
                List<Investor> investorLowestNumberCompanies = this.investors.findInvestorWithLowestNumberCompanies(simulation.getId());
                float highestCapital = companyHighestCapital.get(0).getSharePrice() * 
                        companyHighestCapital.get(0).getNumberOfShares();
                float lowestCapital = companyLowestCapital.get(0).getSharePrice() * 
                        companyLowestCapital.get(0).getNumberOfShares();

                RelatoryResponse response = new RelatoryResponse();
                response.setSimulationId(simulation.getId());
                response.setNumberOfTransactions(simulation.getNumberOfTransactions());
                response.setCompanyHighestCapital(highestCapital);
                response.setCompaniesWithHighestCapital(companyHighestCapital);
                response.setCompanyLowestCapital(lowestCapital);
                response.setCompaniesWithLowestCapital(companyLowestCapital);
                response.setInvestorsWithHighestNumberOfShares(investorHighestNumberShares);
                response.setInvestorsWithLowestNumberOfShares(investorLowestNumberShares);
                response.setInvestorWithLeastNumberOfCompanies(investorHighestNumberCompanies);
                response.setInvestorWithLeastNumberOfCompanies(investorLowestNumberCompanies);
		
		return response;
	}
	
}
