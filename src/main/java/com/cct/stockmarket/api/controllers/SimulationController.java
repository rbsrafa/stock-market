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
	
	Float h = 0f;
	Float l = 10000000f;
	Integer leastNumberOfCompanies = 101;
	Integer highestNumberOfShares = 0;
	Integer lowestNumberOfShares = 10000;
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
		
		List<Simulation> simulationList = this.simulations.findAll();
		System.out.println(simulationList);
		
		simulationList.forEach(simulation -> {
			
			List<Company> companiesInSimulation = this.companies.findCompaniesBySimulation(simulation.getId());
			List<Investor> investorsInSimulation = this.investors.findInvestorsBySimulation(simulation.getId());
			
			List<Company> highestCapital = new ArrayList<Company>(companiesInSimulation);
			
			highestCapital.forEach(c -> {
				if((c.getSharePrice() * c.getNumberOfShares()) > h) {
					this.h = (c.getSharePrice() * c.getNumberOfShares());
				}
			});
			
			highestCapital.removeIf(c1 -> {
				return (c1.getSharePrice() * c1.getNumberOfShares()) != this.h;
			});
			
			List<Company> lowestCapital = new ArrayList<Company>(companiesInSimulation);
			lowestCapital.forEach(c -> {
				if((c.getSharePrice() * c.getNumberOfShares()) < l) {
					this.l = (c.getSharePrice() * c.getNumberOfShares());
				}
			});
			
			lowestCapital.removeIf(c1 -> {
				return (c1.getSharePrice() * c1.getNumberOfShares()) != this.l;
			});
			
			List<Investor> leastNumberOfCompanies = new ArrayList(investorsInSimulation);
			leastNumberOfCompanies.forEach(i -> {
				if(i.getNumberOfCompanies() < this.leastNumberOfCompanies) {
					this.leastNumberOfCompanies = i.getNumberOfCompanies();
				}
			});
			
			leastNumberOfCompanies.removeIf(i -> {
				return i.getNumberOfCompanies() != this.leastNumberOfCompanies;
			});
			
			List<Investor> highestNumberOfShares = new ArrayList(investorsInSimulation);

			highestNumberOfShares.forEach(i -> {
				if(i.getNumberOfShares() > this.highestNumberOfShares) {
					this.highestNumberOfShares = i.getNumberOfShares();
				}
			});
			
			highestNumberOfShares.removeIf(i -> {
				return i.getNumberOfShares() != this.highestNumberOfShares;
			});
			
			List<Investor> lowestNumberOfShares = new ArrayList(investorsInSimulation);
			
			lowestNumberOfShares.forEach(i -> {
				if(i.getNumberOfShares() < this.lowestNumberOfShares) {
					this.lowestNumberOfShares = i.getNumberOfShares();
				}
			});
			
			lowestNumberOfShares.removeIf(i -> {
				return i.getNumberOfShares() != this.lowestNumberOfShares;
			});
			
			RelatoryResponse response = new RelatoryResponse();
			response.setSimulationId(simulation.getId());
			response.setNumberOfTransactions(simulation.getNumberOfTransactions());
			response.setCompanyHighestCapital(this.h);
			response.setCompaniesWithHighestCapital(highestCapital);
			response.setCompanyLowestCapital(this.l);
			response.setCompaniesWithLowestCapital(lowestCapital);
			response.setInvestorWithLeastNumberOfCompanies(leastNumberOfCompanies);
			response.setInvestorsWithHighestNumberOfShares(highestNumberOfShares);
			response.setInvestorsWithLowestNumberOfShares(lowestNumberOfShares);
			
			this.simulationRelatories.add(response);
			
			this.h = 0f;
			this.l = 10000000f;
			this.leastNumberOfCompanies = 101;
			this.highestNumberOfShares = 0;
			this.lowestNumberOfShares = 10000;
		});
		
		return this.simulationRelatories;
	}
	
	@GetMapping("/{id}/relatories")
	public RelatoryResponse getRelatoryBySimulationId(
		@PathVariable Long id
	) {
		Simulation simulation = this.simulations.getOne(id);

		List<Company> companiesInSimulation = this.companies.findCompaniesBySimulation(id);
		List<Investor> investorsInSimulation = this.investors.findInvestorsBySimulation(id);
		
		List<Company> highestCapital = new ArrayList<Company>(companiesInSimulation);
		
		highestCapital.forEach(c -> {
			if((c.getSharePrice() * c.getNumberOfShares()) > h) {
				this.h = (c.getSharePrice() * c.getNumberOfShares());
			}
		});
		
		highestCapital.removeIf(c1 -> {
			return (c1.getSharePrice() * c1.getNumberOfShares()) != this.h;
		});
		
		List<Company> lowestCapital = new ArrayList<Company>(companiesInSimulation);
		lowestCapital.forEach(c -> {
			if((c.getSharePrice() * c.getNumberOfShares()) < l) {
				this.l = (c.getSharePrice() * c.getNumberOfShares());
			}
		});
		
		lowestCapital.removeIf(c1 -> {
			return (c1.getSharePrice() * c1.getNumberOfShares()) != this.l;
		});
		
		List<Investor> leastNumberOfCompanies = new ArrayList(investorsInSimulation);
		leastNumberOfCompanies.forEach(i -> {
			if(i.getNumberOfCompanies() < this.leastNumberOfCompanies) {
				this.leastNumberOfCompanies = i.getNumberOfCompanies();
			}
		});
		
		leastNumberOfCompanies.removeIf(i -> {
			return i.getNumberOfCompanies() != this.leastNumberOfCompanies;
		});
		
		List<Investor> highestNumberOfShares = new ArrayList(investorsInSimulation);

		highestNumberOfShares.forEach(i -> {
			if(i.getNumberOfShares() > this.highestNumberOfShares) {
				this.highestNumberOfShares = i.getNumberOfShares();
			}
		});
		
		highestNumberOfShares.removeIf(i -> {
			return i.getNumberOfShares() != this.highestNumberOfShares;
		});
		
		List<Investor> lowestNumberOfShares = new ArrayList(investorsInSimulation);
		
		lowestNumberOfShares.forEach(i -> {
			if(i.getNumberOfShares() < this.lowestNumberOfShares) {
				this.lowestNumberOfShares = i.getNumberOfShares();
			}
		});
		
		lowestNumberOfShares.removeIf(i -> {
			return i.getNumberOfShares() != this.lowestNumberOfShares;
		});
		
		RelatoryResponse response = new RelatoryResponse();
		response.setSimulationId(id);
		response.setNumberOfTransactions(simulation.getNumberOfTransactions());
		response.setCompanyHighestCapital(this.h);
		response.setCompaniesWithHighestCapital(highestCapital);
		response.setCompanyLowestCapital(this.l);
		response.setCompaniesWithLowestCapital(lowestCapital);
		response.setInvestorWithLeastNumberOfCompanies(leastNumberOfCompanies);
		response.setInvestorsWithHighestNumberOfShares(highestNumberOfShares);
		response.setInvestorsWithLowestNumberOfShares(lowestNumberOfShares);
		
		return response;
	}
	
}
