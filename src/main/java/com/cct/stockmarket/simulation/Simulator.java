package com.cct.stockmarket.simulation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.Transaction;
import com.cct.stockmarket.api.repositories.CompanyRepository;
import com.cct.stockmarket.api.repositories.InvestorRepository;
import com.cct.stockmarket.api.repositories.TransactionRepository;
import com.cct.stockmarket.simulation.generators.CompanyGenerator;
import com.cct.stockmarket.simulation.generators.InvestorGenerator;

@Component
public class Simulator {
	
	@Autowired
	CompanyRepository companies;
	
	@Autowired
	InvestorRepository investors;
	
	@Autowired
	TransactionRepository transactions;
	
	private Integer numberOfCompanies = 100;
	private Integer numberOfInvestors = 100;
    
    public Simulator() {}
	
	public void runTradingDay() {
		
		this.createCompanies();
		this.createInvestors();
		this.makeTransactions();
		
	}
	
	private void makeTransactions() {
		
		
		
		// Find company id 1
		Company cOne = companies.findById(1L).orElse(null);
		System.out.println("********");
		System.out.println(cOne);
		System.out.println("********");
		  
		// Find investor id 1
		Investor iOne = investors.findById(1L).orElse(null);
		System.out.println("********");
		System.out.println(iOne);
		System.out.println("********");
		  
		// Investor one is buying a share from Company one
		Transaction tOne = new Transaction(iOne, cOne, cOne.getSharePrice());
	  
		// Update company 1 available shares
		cOne.setAvailableShares(cOne.getAvailableShares()-1);
		// Update investor 1 budget
		iOne.setBudget(iOne.getBudget()-cOne.getSharePrice());
	  
		// Save all changes
		tOne = this.transactions.save(tOne);
		cOne = this.companies.save(cOne);
		iOne = this.investors.save(iOne);
	  
		// Print all updated entities
	  
		System.out.println("********");
		System.out.println(tOne);
		System.out.println("********");
	  
		cOne = companies.findById(1L).orElse(null);
		System.out.println("********");
		System.out.println(cOne);
		System.out.println("********");
	  
		iOne = investors.findById(1L).orElse(null);
		System.out.println("********");
		System.out.println(iOne);
		System.out.println("********");
		
		
		
	}
	
	private void createCompanies(){
		@SuppressWarnings("unchecked")
		List<Company> companyList = new ArrayList<>(
			CompanyGenerator.generateCompanies(
				this.numberOfCompanies
			)
		);
		this.companies.saveAll(companyList);
	}
	
	private void createInvestors() {
		@SuppressWarnings("unchecked")
		List<Investor> investorList = new ArrayList<>(
			InvestorGenerator.generateInvestors(
				this.numberOfInvestors)
		);
		this.investors.saveAll(investorList);
	}
	
}
