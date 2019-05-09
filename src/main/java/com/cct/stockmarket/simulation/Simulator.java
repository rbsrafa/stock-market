package com.cct.stockmarket.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.Simulation;
import com.cct.stockmarket.api.models.Transaction;
import com.cct.stockmarket.api.payloads.SimulationResponse;
import com.cct.stockmarket.api.repositories.CompanyRepository;
import com.cct.stockmarket.api.repositories.InvestorRepository;
import com.cct.stockmarket.api.repositories.SimulationRepository;
import com.cct.stockmarket.simulation.generators.CompanyGenerator;
import com.cct.stockmarket.simulation.generators.InvestorGenerator;
import com.cct.stockmarket.simulation.iterators.investor.InvestorCollection;
import com.cct.stockmarket.simulation.iterators.investor.InvestorIteratorInterface;

/**
 * 
 * @author rbsrafa
 *
 */
@Component
@Scope("prototype")
public class Simulator implements ISimulator{

	@Autowired
	private CompanyRepository companies;
	
	@Autowired
	private InvestorRepository investors;
	
	@Autowired 
	private SimulationRepository simulations;
	
	private Simulation simulation;
	
	private SimulatorSettings settings;
	
	private HashMap<Long, Integer> companySoldAmount;
	private HashMap<Long, Set<Long>> investorCompany;
    
	private List<Investor> investorList; 
	private List<Company> companyList;
	
	private InvestorCollection availableInvestors2;
	
	private List<Company> availableCompanies;
	private List<Transaction> transactionList;
	private List<Float> budgets;
	private List<Float> shares;
	
	private boolean tradeStillPossible;
	private boolean isMinShareBiggerThanMaxBudget;
	
	public Simulator(SimulatorSettings settings) {
		this.settings = settings;
	}
	
	/**
	 * Create a default Simulator
	 */
    public Simulator() {

    }
    
    /**
	 * Setup the trading day simulation
	 */
	private void setupTradingDay() {
		tradeStillPossible = true;
		this.transactionList = new ArrayList<>();
		this.companySoldAmount = new HashMap<>();
		this.investorCompany = new HashMap<>();
    	
    	this.investorList = InvestorGenerator
			.generateInvestors(
				settings.getNumberOfInvestors(), 
				settings.getMinBudget(), 
				settings.getMaxBudget()
			);

		this.companyList = CompanyGenerator
			.generateCompanies(
				settings.getNumberOfCompanies(),
				settings.getMaxAmmountShares(), 
				settings.getMinAmmountShares(), 
				settings.getMaxSharePrice(), 
				settings.getMinSharePrice()
			);
	}
	
    /**
     * Runs a simulation trading day
     */
	@Override
	public void runTradingDay() {
		this.setupTradingDay();
		
		this.simulation = this.simulations
			.save(new Simulation());
		
		this.companyList.forEach(company -> {
			company.setSimulation(this.simulation);
		});
		
		this.investorList.forEach(investor -> {
			investor.setSimulation(this.simulation);
		});
		
		// Set initial available companies
		this.availableCompanies = this.companies.saveAll(this.companyList);
    	
		// Set initial available investors
		//this.availableInvestors = this.investors.saveAll(this.investorList);
		this.availableInvestors2 = new InvestorCollection(this.investors.saveAll(this.investorList));

		// Set available entities for trading
		this.setAvailableTradingEntities(0f);
		
		// Set shares maximum value
		this.shares = new ArrayList<>();
		this.shares.add(this.settings.getMaxSharePrice());
		
		// While there are available investors and
		// available companies and the trade is still
		// possible run the trading transactions
		while(
			availableInvestors2.size() > 0 && 
			availableCompanies.size() > 0 &&
			tradeStillPossible
		) {
			// Update available entities for share trade
			this.setAvailableTradingEntities(this.shares.get(0));
			
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
			if(isMinShareBiggerThanMaxBudget) {
				this.tradeStillPossible = false;
				
				this.investorList.forEach(i -> {
					i.setNumberOfCompanies(this.investorCompany.get(i.getId()).size());
				});
				
				this.simulation.setNumberOfTransactions(this.transactionList.size());
				this.simulations.save(this.simulation);
				this.companies.saveAll(this.companyList);
				this.investors.saveAll(this.investorList);
			}
			
			// Print trading info to console
			this.printTradingState();
		}
	}

	/**
	 * Makes investors x companies transactions
	 */
	private void makeTransaction() {
		// Choose a random investor from list of possible investors
		Investor i = this.availableInvestors2.get(random(0,availableInvestors2.size()-1));

		// Choose a random company from list of possible companies
		Company c = availableCompanies.get(random(0, availableCompanies.size() -1));
		
		// If investor budget is greater or equal the company's 
		// share price and the company still has available shares
		// make a new transaction
		if(i.getBudget() >= c.getSharePrice() && c.getAvailableShares() > 0) {
			// Create transaction
			Transaction t = new Transaction(i, c, c.getSharePrice());
			
			// Save transaction in transactions made
			this.transactionList.add(t);
			
			// Update investor budget
			i.setBudget(i.getBudget() - c.getSharePrice());

			// Update investor number of shares
			i.setNumberOfShares(i.getNumberOfShares() + 1);
			
			// Update company available shares
			c.setAvailableShares(c.getAvailableShares()-1);
			
			// Keep track of investor different companies;
			if(this.investorCompany.get(i.getId()) == null) {
				Set<Long> companyIds = new HashSet<>();
				companyIds.add(c.getId());
				this.investorCompany.put(i.getId(), companyIds);
			}else {
				Set<Long> companiesId = this.investorCompany.get(i.getId());
				companiesId.add(c.getId());
				this.investorCompany.put(i.getId(), companiesId);
			}
			
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
			}
			
			// If the transactions made are multiple of 10
			// check which companies have not sold any share
			// and reduce their share value by 2%
			if(this.transactionList.size() % 10 == 0) {
				// Get all companies in the simulation
				List<Company> allCompanies = new ArrayList<Company>(this.companyList);
				
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
				
			}
						
		}
		
	}
	
	private void updateBudgetList() {
		// Create / Clear the budgets list info 
		this.budgets = new ArrayList<>();
		
		// For each available investor add its
		// budget to list
		InvestorIteratorInterface iterator = this.availableInvestors2.iterator();
		while(iterator.hasNext()) {
			budgets.add(iterator.next().getBudget());
		}
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
		System.out.println("Number of transactions: " + this.transactionList.size());
		System.out.println("Max investor budget: " + this.budgets.get(this.budgets.size()-1));
		System.out.println("Min share price: " + this.shares.get(0));
		System.out.println("Number of companies that have not sold any share: " + (this.companyList.size() - this.companySoldAmount.size()));
		System.out.println("Companies that have sold shares: " + this.companySoldAmount);
		System.out.println("Possible investors budgets: " + this.budgets);
		System.out.println("Budget list size: " + (this.budgets.size()-1));
		System.out.println("Is min share value bigger than max investor budget: " + isMinShareBiggerThanMaxBudget);
		System.out.println("Trade still possible: " + this.tradeStillPossible);
		System.out.println("******************");
	}
	
	public SimulationResponse getResults() {
		SimulationResponse results = new SimulationResponse(
			this.transactionList.size(),
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
	private void setAvailableTradingEntities(Float minSharePrice) {
		
		this.availableCompanies.removeIf(company -> {
			return company.getNumberOfShares() <= 0;
		});
		
		InvestorIteratorInterface iterator = this.availableInvestors2.iterator();
		while(iterator.hasNext()) {
			Investor i = iterator.next();
			if(i.getBudget() < minSharePrice) {
				this.availableInvestors2.remove(i);
			}
		}
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
	 * @return the simulations
	 */
	public SimulationRepository getSimulations() {
		return simulations;
	}

	/**
	 * @param simulations the simulations to set
	 */
	public void setSimulations(SimulationRepository simulations) {
		this.simulations = simulations;
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
	 * @return the investorCompany
	 */
	public HashMap<Long, Set<Long>> getInvestorCompany() {
		return investorCompany;
	}

	/**
	 * @param investorCompany the investorCompany to set
	 */
	public void setInvestorCompany(HashMap<Long, Set<Long>> investorCompany) {
		this.investorCompany = investorCompany;
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
	 * @return the transactionList
	 */
	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	/**
	 * @param transactionList the transactionList to set
	 */
	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
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
	 * @return the simulation
	 */
	public Simulation getSimulation() {
		return simulation;
	}

	/**
	 * @param simulation the simulation to set
	 */
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
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

}
