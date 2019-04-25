package com.cct.stockmarket.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.Transaction;
import com.cct.stockmarket.api.payloads.SimulationResponse;
import com.cct.stockmarket.api.repositories.CompanyRepository;
import com.cct.stockmarket.api.repositories.InvestorRepository;
import com.cct.stockmarket.api.repositories.TransactionRepository;

/**
 * 
 * @author rbsrafa
 *
 */
@Component
@Scope("prototype")
public class Simulator {
	
	@Autowired
	CompanyRepository companies;
	
	@Autowired
	InvestorRepository investors;
	
	@Autowired
	TransactionRepository transactions;
	
	private Integer numberOfTransactions;
	
	private Float initialShareMax;
	
	private HashMap<Long, Integer> companySoldAmount;
    
	private List<Investor> availableInvestors;
	private List<Company> availableCompanies;
	private List<Float> budgets;
	private List<Float> shares;
	
	boolean tradeStillPossible;
	boolean isMinShareBiggerThanMaxBudget;
	
	List<Investor> investorList; 
	List<Company> companyList;
	
	/**
	 * Create a Simulator with the provided
	 * list of investors and companies
	 * @param investorList
	 * @param companyList
	 */
	@Autowired
	public Simulator(
		List<Investor> investorList, 
		List<Company> companyList
	) {
		this.investorList = investorList;
		this.companyList = companyList;
	}
	
	/**
	 * Create a default Simulator
	 */
    public Simulator() {}
	
    /**
     * Runs a simulation trading day
     */
	public void runTradingDay() {
		this.setupTradingDay();
		this.startTradingDay();
	}
	
	/**
	 * Setup the trading day simulation
	 */
	private void setupTradingDay() {
    	numberOfTransactions = 0;
    	initialShareMax = 100f;
    	tradeStillPossible = true;
    	companySoldAmount = new HashMap<>();
		
		// Delete all persisted rows in database
		this.clearTables();
		
		// Persist the provided companies on database
		this.companies.saveAll(this.companyList);
		
		// Persist the provided investors on database
		this.investors.saveAll(this.investorList);
		
		// Set available entities for trading
		this.setAvailableTradingEntities(0f, 0);
		
		// Set shares maximum value
		this.shares = new ArrayList<>();
		this.shares.add(this.initialShareMax);
	}
	
	/*
	 * Start the trading day simulation
	 */
	private void startTradingDay() {
		// While there are available investors and
		// available companies and the trade is still
		// possible run the trading transactions
		while(
			availableInvestors.size() > 0 && 
			availableCompanies.size() > 0 &&
			tradeStillPossible
		) {
			// Update available entities for share trade
			this.setAvailableTradingEntities(this.shares.get(0), 0);
			
			// Make a trasaction
			this.makeTransaction();
			
			// Update budgets list
			this.updateBudgetList();
			
			// Update shares list
			this.updateShareList();
			
			// Sort budgets in ascendig order
			Collections.sort(budgets);
			
			// Sort shares in ascendig order
			Collections.sort(shares);
			
			// Check if the min share price is bigger than
			// the max budget value
			isMinShareBiggerThanMaxBudget = 
				this.shares.get(0) >
				this.budgets.get(this.budgets.size()-1);
			
			// If min share price is bigger than the max 
			// budget value set trade still possible to false			   
			if(isMinShareBiggerThanMaxBudget) {this.tradeStillPossible = false;}
			
			// Print trading info to console
			this.printTradingState();
		}
	}

	/**
	 * Makes investors x companies transactions
	 */
	private void makeTransaction() {
		// Choose a random investor from list of possible investors
		Investor i = availableInvestors.get(random(0,availableInvestors.size()-1));
		
		// Choose a random company from list of possible companies
		Company c = availableCompanies.get(random(0, availableCompanies.size() -1));
		
		// If investor budget is greater or equal the company's 
		// share price and the company still has available shares
		// make a new transaction
		if(i.getBudget() >= c.getSharePrice() && c.getAvailableShares() > 0) {
			// Create transaction
			Transaction t = new Transaction(i, c, c.getSharePrice());
			
			// Save transaction in database
			this.transactions.save(t);
			
			// Update investor budget
			i.setBudget(i.getBudget() - c.getSharePrice());
			
			// Save investor changes in database
			this.investors.save(i);
			
			// Update company available shares
			c.setAvailableShares(c.getAvailableShares()-1);
			
			// Save company changes in database
			this.companies.save(c);
			
			// Raise the number of transactions by 1
			++this.numberOfTransactions;
			
			// Update mapping of companies with sold shares
			this.companySoldAmount.put(c.getId(), c.getNumberOfShares()-c.getAvailableShares());
			
			// Check the number of shares a company has sold
			int ammountSold = this.companySoldAmount.get(c.getId());
			
			// Check if the ammount sold is multiple of 10
			boolean hasSold10 = ammountSold % 10 == 0;
			
			// If company has sold 10 shares or its multiples
			// double up its share price
			if(hasSold10) {
				// Update company share price
				c.setSharePrice(c.getSharePrice() * 2f);
				
				// Print double up message to console
				System.out.println("$$$$$$$$$$$$$$$$$");
				System.out.println("Doubled up: " + ammountSold + " " + c);
				System.out.println("$$$$$$$$$$$$$$$$$");
				
				// Save company change to database
				this.companies.save(c);
			}
			
			// If the transactions made are multiple of 10
			// check which companies have not sold any share
			// and reduce their share value by 2%
			if(this.numberOfTransactions % 10 == 0) {
				// Get all companies in the simulation
				List<Company> allCompanies = this.companies.findAll();
				
				// For each company remove from the list the 
				// one that has not sold any share
				allCompanies.removeIf(company -> {
					Set<Long> keys = this.companySoldAmount.keySet();
					return keys.contains(company.getId());
				});
				// Create a list with non sold shares companies
				List<Company> haveNotSold = allCompanies;
				
				// for any company that hasn't sold a share decrease
				// its share value by 2%
				haveNotSold.forEach(company -> {
					company.setSharePrice(company.getSharePrice() * 0.98f);
					System.out.println("Lost 2% of value: " + company);
				});
				
				// Save changed companies in database
				this.companies.saveAll(haveNotSold);
			}
						
		}
		
	}
	
	private void updateBudgetList() {
		// Create / Clear the budgets list info 
		this.budgets = new ArrayList<>();
		
		// For each available investor add its
		// budget to list
		availableInvestors.forEach(i -> {
			budgets.add(i.getBudget());
		});
	}
	
	private void updateShareList() {
		// Create / Clear the shares list info 
		this.shares = new ArrayList<>();
		
		// For each available company add its
		// share price to list
		availableCompanies.forEach(c -> {
			shares.add(c.getSharePrice());
		});
	}
	
	/**
	 * Print the trading info to console
	 */
	private void printTradingState() {
		System.out.println("******************");
		System.out.println("Number of transactions: " + this.numberOfTransactions);
		System.out.println("Max investor budget: " + this.budgets.get(this.budgets.size()-1));
		System.out.println("Min share price: " + this.shares.get(0));
		System.out.println("Number of companies that have not sold any share: " + (this.investorList.size() - this.companySoldAmount.size()));
		System.out.println("Companies that have sold shares: " + this.companySoldAmount);
		System.out.println("Possible investors budgets: " + this.budgets);
		System.out.println("Budget list size: " + (this.budgets.size()-1));
		System.out.println("Is min share value bigger than max investor budget: " + isMinShareBiggerThanMaxBudget);
		System.out.println("Trade still possible: " + this.tradeStillPossible);
		System.out.println("******************");
	}
	
	public SimulationResponse getResults() {
		SimulationResponse results = new SimulationResponse(
			this.numberOfTransactions,
			this.budgets.get(this.budgets.size()-1),
			this.shares.get(0),
			this.investorList.size() - this.companySoldAmount.size(),
			this.companySoldAmount,	
			this.budgets
		);
		
		return results;
	}
	
	/**
	 * Set available entities to perform a share trade
	 * @param budget
	 * @param share
	 */
	private void setAvailableTradingEntities(Float budget, Integer share) {
		availableInvestors = this.investors.findByBudgetGreaterThan(budget);
		availableCompanies = this.companies.findByAvailableSharesGreaterThan(share);
	}
	
	/**
	 * Clear all entity rows in database
	 */
	private void clearTables() {
		this.transactions.deleteAllInBatch();
		this.companies.deleteAllInBatch();
		this.investors.deleteAllInBatch();
	}
		
	/**
	 * Returns a random number between the provided
	 * min and max boundaries
	 * @param min
	 * @param max
	 * @return int
	 */
	private int random(int min, int max) {
    	return (int)(Math.random() * ((max - min) + 1) + min);
    }
	
	
	
	
	
	
	// Getters and Setters	

	/**
	 * @return the companies
	 */
	public CompanyRepository getCompanies() {
		return companies;
	}

	/**
	 * @param companies the companies to set
	 */
	public void setCompanies(CompanyRepository companies) {
		this.companies = companies;
	}

	/**
	 * @return the investors
	 */
	public InvestorRepository getInvestors() {
		return investors;
	}

	/**
	 * @param investors the investors to set
	 */
	public void setInvestors(InvestorRepository investors) {
		this.investors = investors;
	}

	/**
	 * @return the transactions
	 */
	public TransactionRepository getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(TransactionRepository transactions) {
		this.transactions = transactions;
	}

	/**
	 * @return the numberOfTransactions
	 */
	public Integer getNumberOfTransactions() {
		return numberOfTransactions;
	}

	/**
	 * @param numberOfTransactions the numberOfTransactions to set
	 */
	public void setNumberOfTransactions(Integer numberOfTransactions) {
		this.numberOfTransactions = numberOfTransactions;
	}

	/**
	 * @return the initialShareMax
	 */
	public Float getInitialShareMax() {
		return initialShareMax;
	}

	/**
	 * @param initialShareMax the initialShareMax to set
	 */
	public void setInitialShareMax(Float initialShareMax) {
		this.initialShareMax = initialShareMax;
	}

	/**
	 * @return the companySoldAmount
	 */
	public HashMap<Long, Integer> getCompanySoldAmount() {
		return companySoldAmount;
	}

	/**
	 * @param companySoldAmount the companySoldAmount to set
	 */
	public void setCompanySoldAmount(HashMap<Long, Integer> companySoldAmount) {
		this.companySoldAmount = companySoldAmount;
	}

	/**
	 * @return the availableInvestors
	 */
	public List<Investor> getAvailableInvestors() {
		return availableInvestors;
	}

	/**
	 * @param availableInvestors the availableInvestors to set
	 */
	public void setAvailableInvestors(List<Investor> availableInvestors) {
		this.availableInvestors = availableInvestors;
	}

	/**
	 * @return the availableCompanies
	 */
	public List<Company> getAvailableCompanies() {
		return availableCompanies;
	}

	/**
	 * @param availableCompanies the availableCompanies to set
	 */
	public void setAvailableCompanies(List<Company> availableCompanies) {
		this.availableCompanies = availableCompanies;
	}

	/**
	 * @return the budgets
	 */
	public List<Float> getBudgets() {
		return budgets;
	}

	/**
	 * @param budgets the budgets to set
	 */
	public void setBudgets(List<Float> budgets) {
		this.budgets = budgets;
	}

	/**
	 * @return the shares
	 */
	public List<Float> getShares() {
		return shares;
	}

	/**
	 * @param shares the shares to set
	 */
	public void setShares(List<Float> shares) {
		this.shares = shares;
	}

	/**
	 * @return the tradeStillPossible
	 */
	public boolean isTradeStillPossible() {
		return tradeStillPossible;
	}

	/**
	 * @param tradeStillPossible the tradeStillPossible to set
	 */
	public void setTradeStillPossible(boolean tradeStillPossible) {
		this.tradeStillPossible = tradeStillPossible;
	}

	/**
	 * @return the isMinShareBiggerThanMaxBudget
	 */
	public boolean isMinShareBiggerThanMaxBudget() {
		return isMinShareBiggerThanMaxBudget;
	}

	/**
	 * @param isMinShareBiggerThanMaxBudget the isMinShareBiggerThanMaxBudget to set
	 */
	public void setMinShareBiggerThanMaxBudget(boolean isMinShareBiggerThanMaxBudget) {
		this.isMinShareBiggerThanMaxBudget = isMinShareBiggerThanMaxBudget;
	}

	/**
	 * @return the investorList
	 */
	public List<Investor> getInvestorList() {
		return investorList;
	}

	/**
	 * @param investorList the investorList to set
	 */
	public void setInvestorList(List<Investor> investorList) {
		this.investorList = investorList;
	}

	/**
	 * @return the companyList
	 */
	public List<Company> getCompanyList() {
		return companyList;
	}

	/**
	 * @param companyList the companyList to set
	 */
	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}
	
	
	
}
