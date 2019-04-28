package com.cct.stockmarket.simulation;

public class SimulatorSettings {
	private Integer numberOfInvestors;
	private Integer numberOfCompanies;
	private Float minBudget;
	private Float maxBudget;
	private Float minSharePrice;
	private Float maxSharePrice;
	private Integer minAmmountShares;
	private Integer maxAmmountShares;
	
	public SimulatorSettings() {}

	/**
	 * @return the numberOfInvestors
	 */
	public Integer getNumberOfInvestors() {
		return numberOfInvestors;
	}

	/**
	 * @param numberOfInvestors the numberOfInvestors to set
	 */
	public void setNumberOfInvestors(Integer numberOfInvestors) {
		this.numberOfInvestors = numberOfInvestors;
	}

	/**
	 * @return the numberOfCompanies
	 */
	public Integer getNumberOfCompanies() {
		return numberOfCompanies;
	}

	/**
	 * @param numberOfCompanies the numberOfCompanies to set
	 */
	public void setNumberOfCompanies(Integer numberOfCompanies) {
		this.numberOfCompanies = numberOfCompanies;
	}

	/**
	 * @return the minBudget
	 */
	public Float getMinBudget() {
		return minBudget;
	}

	/**
	 * @param minBudget the minBudget to set
	 */
	public void setMinBudget(Float minBudget) {
		this.minBudget = minBudget;
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
	 * @return the maxSharePrice
	 */
	public Float getMaxSharePrice() {
		return maxSharePrice;
	}

	/**
	 * @param maxSharePrice the maxSharePrice to set
	 */
	public void setMaxSharePrice(Float maxSharePrice) {
		this.maxSharePrice = maxSharePrice;
	}

	/**
	 * @return the minAmmountShares
	 */
	public Integer getMinAmmountShares() {
		return minAmmountShares;
	}

	/**
	 * @param minAmmountShares the minAmmountShares to set
	 */
	public void setMinAmmountShares(Integer minAmmountShares) {
		this.minAmmountShares = minAmmountShares;
	}

	/**
	 * @return the maxAmmountShares
	 */
	public Integer getMaxAmmountShares() {
		return maxAmmountShares;
	}

	/**
	 * @param maxAmmountShares the maxAmmountShares to set
	 */
	public void setMaxAmmountShares(Integer maxAmmountShares) {
		this.maxAmmountShares = maxAmmountShares;
	}
	
	
}
