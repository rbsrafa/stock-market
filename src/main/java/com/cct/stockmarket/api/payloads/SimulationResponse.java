package com.cct.stockmarket.api.payloads;

import java.util.HashMap;
import java.util.List;

public class SimulationResponse {
	
	private Integer numberOfTransactions;
	
	private Float maxBudget;
	
	private Float minSharePrice;
	
	private Integer companiesNotSold;

	private HashMap<Long, Integer> companySoldAmount;
	
	private List<Float> budgets;

	public SimulationResponse(Integer numberOfTransactions, Float maxBudget, Float minSharePrice,
			Integer companiesNotSold, HashMap<Long, Integer> companySoldAmount, List<Float> budgets) {
		super();
		this.numberOfTransactions = numberOfTransactions;
		this.maxBudget = maxBudget;
		this.minSharePrice = minSharePrice;
		this.companiesNotSold = companiesNotSold;
		this.companySoldAmount = companySoldAmount;
		this.budgets = budgets;
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
	 * @return the maxBudget
	 */
	public Float getMaxBudget() {
		return maxBudget;
	}

	/**
	 * @param maxBudget the maxBudget to set
	 */
	public void setMaxBudget(Float maxBudget) {
		this.maxBudget = maxBudget;
	}

	/**
	 * @return the minSharePrice
	 */
	public Float getMinSharePrice() {
		return minSharePrice;
	}

	/**
	 * @param minSharePrice the minSharePrice to set
	 */
	public void setMinSharePrice(Float minSharePrice) {
		this.minSharePrice = minSharePrice;
	}

	/**
	 * @return the companiesNotSold
	 */
	public Integer getCompaniesNotSold() {
		return companiesNotSold;
	}

	/**
	 * @param companiesNotSold the companiesNotSold to set
	 */
	public void setCompaniesNotSold(Integer companiesNotSold) {
		this.companiesNotSold = companiesNotSold;
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
	
	
}
