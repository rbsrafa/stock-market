package com.cct.stockmarket.api.payloads;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SimulationRequest {
	
	@NotNull
	@Min(1) @Max(100)
	private Integer investorsQuantity;
	
	@NotNull
	@Min(1) @Max(100)
	private Integer companiesQuantity;
	
	@NotNull
	@Min(1000) @Max(10000)
	private Float maxBudget;
	
	@NotNull
	@Min(1000) @Max(10000)
	private Float minBudget;
	
	@NotNull
	@Min(10) @Max(100)
	private Float maxSharePrice;
	
	@NotNull
	@Min(10) @Max(100)
	private Float minSharePrice;

	@NotNull
	@Min(500) @Max(1000)
	private Integer maxAmmountShares;
	
	@NotNull
	@Min(500) @Max(1000)
	private Integer minAmmountShares;

	/**
	 * @return the investorsQuantity
	 */
	public Integer getInvestorsQuantity() {
		return investorsQuantity;
	}

	/**
	 * @param investorsQuantity the investorsQuantity to set
	 */
	public void setInvestorsQuantity(Integer investorsQuantity) {
		this.investorsQuantity = investorsQuantity;
	}

	/**
	 * @return the companiesQuantity
	 */
	public Integer getCompaniesQuantity() {
		return companiesQuantity;
	}

	/**
	 * @param companiesQuantity the companiesQuantity to set
	 */
	public void setCompaniesQuantity(Integer companiesQuantity) {
		this.companiesQuantity = companiesQuantity;
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

	
}
