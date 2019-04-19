package com.cct.stockmarket.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

/**
 * 
 * @author rbsrafa
 *
 */
@Component
public class Simulator {
	
	@Autowired
	CompanyRepository companies;
	
	@Autowired
	InvestorRepository investors;
	
	@Autowired
	TransactionRepository transactions;
	
	private Integer numberOfCompanies;
	private Integer numberOfInvestors;
	
	private Integer numberOfTransactions;
	
	private Float initialShareMax;
	
	private HashMap<Long, Integer> companySoldAmount;
    
	private List<Investor> availableInvestors;
	private List<Company> availableCompanies;
	private List<Float> budgets;
	private List<Float> shares;
	
	boolean tradeStillPossible;
	boolean isMinShareBiggerThanMaxBudget;
	
	/**
	 * Create a default Simulator
	 */
    public Simulator() {
    	numberOfCompanies = 10;
    	numberOfInvestors = 10;
    	numberOfTransactions = 0;
    	initialShareMax = 100f;
    	tradeStillPossible = true;
    	companySoldAmount = new HashMap<>();
    }
	
    /**
     * Runs a simulation trading day
     */
	public void runTradingDay() {
		
		// Delete all persisted rows in database
		this.clearTables();
		
		// Create random companies
		this.createCompanies();
		
		// Create random investors
		this.createInvestors();
		
		// Set available entities for trading
		this.setAvailableTradingEntities(0f, 0);
		
		// Set shares maximum value
		this.shares = new ArrayList<>();
		this.shares.add(this.initialShareMax);
		
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
			
			
			
		}
		
		// if 10 shares are sold and a company hasn't sold any
		// its share should reduce in 2%

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
		System.out.println("Max investor budget: " + this.budgets.get(this.budgets.size()-1));
		System.out.println("Min share price: " + this.shares.get(0));
		System.out.println("Possible investors budgets: " + this.budgets);
		System.out.println("Budget list size: " + (this.budgets.size()-1));
		System.out.println("Is min share value bigger than max investor budget: " + isMinShareBiggerThanMaxBudget);
		System.out.println("Trade still possible: " + this.tradeStillPossible);
		System.out.println("Companies that have sold shares: " + this.companySoldAmount);
		System.out.println("Number of transactions: " + this.numberOfTransactions);
		System.out.println("******************");
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
	
	/**
	 * Creates companies based on numberOfCompanies property
	 */
	private void createCompanies(){
		// Create a list of companies
		@SuppressWarnings("unchecked")
		List<Company> companyList = new ArrayList<>(
			CompanyGenerator.generateCompanies(
				this.numberOfCompanies
			)
		);
		// Persist companies on database
		this.companies.saveAll(companyList);
	}
	
	/**
	 * Creates investors based on numberOfInvestors property
	 */
	private void createInvestors() {
		// Create a list of investors
		@SuppressWarnings("unchecked")
		List<Investor> investorList = new ArrayList<>(
			InvestorGenerator.generateInvestors(
				this.numberOfInvestors)
		);
		// Persist investors on database
		this.investors.saveAll(investorList);
	}
	
}
