package com.cct.stockmarket.api.payloads;

import java.util.List;

import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.models.Investor;

public class RelatoryResponse {
	
	private Long simulationId;
	
	private Integer numberOfTransactions;
	
	private Float companyHighestCapital;
	
	private List<Company> companiesWithHighestCapital;
	
	private List<Company> companiesWithLowestCapital;
	
	private Float companyLowestCapital;
	
	private List<Investor> investorsWithHighestNumberOfShares;
	
	private List<Investor> investorsWithLowestNumberOfShares;
	
	private List<Investor> investorWithLeastNumberOfCompanies;
	
	public RelatoryResponse() {}
	
	





	public RelatoryResponse(Long simulationId, Integer numberOfTransactions, Float companyHighestCapital,
			List<Company> companiesWithHighestCapital, List<Company> companiesWithLowestCapital,
			Float companyLowestCapital, List<Investor> investorsWithHighestNumberOfShares,
			List<Investor> investorsWithLowestNumberOfShares, List<Investor> investorWithLeastNumberOfCompanies) {
		super();
		this.simulationId = simulationId;
		this.numberOfTransactions = numberOfTransactions;
		this.companyHighestCapital = companyHighestCapital;
		this.companiesWithHighestCapital = companiesWithHighestCapital;
		this.companiesWithLowestCapital = companiesWithLowestCapital;
		this.companyLowestCapital = companyLowestCapital;
		this.investorsWithHighestNumberOfShares = investorsWithHighestNumberOfShares;
		this.investorsWithLowestNumberOfShares = investorsWithLowestNumberOfShares;
		this.investorWithLeastNumberOfCompanies = investorWithLeastNumberOfCompanies;
	}


	/**
	 * @return the simulationId
	 */
	public Long getSimulationId() {
		return simulationId;
	}







	/**
	 * @param simulationId the simulationId to set
	 */
	public void setSimulationId(Long simulationId) {
		this.simulationId = simulationId;
	}







	/**
	 * @return the companyHighestCapital
	 */
	public Float getCompanyHighestCapital() {
		return companyHighestCapital;
	}



	/**
	 * @param companyHighestCapital the companyHighestCapital to set
	 */
	public void setCompanyHighestCapital(Float companyHighestCapital) {
		this.companyHighestCapital = companyHighestCapital;
	}



	/**
	 * @return the companyLowestCapital
	 */
	public Float getCompanyLowestCapital() {
		return companyLowestCapital;
	}



	/**
	 * @param companyLowestCapital the companyLowestCapital to set
	 */
	public void setCompanyLowestCapital(Float companyLowestCapital) {
		this.companyLowestCapital = companyLowestCapital;
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
	 * @return the companiesWithHighestCapital
	 */
	public List<Company> getCompaniesWithHighestCapital() {
		return companiesWithHighestCapital;
	}

	/**
	 * @param companiesWithHighestCapital the companiesWithHighestCapital to set
	 */
	public void setCompaniesWithHighestCapital(List<Company> companiesWithHighestCapital) {
		this.companiesWithHighestCapital = companiesWithHighestCapital;
	}

	/**
	 * @return the companiesWithLowestCapital
	 */
	public List<Company> getCompaniesWithLowestCapital() {
		return companiesWithLowestCapital;
	}

	/**
	 * @param companiesWithLowestCapital the companiesWithLowestCapital to set
	 */
	public void setCompaniesWithLowestCapital(List<Company> companiesWithLowestCapital) {
		this.companiesWithLowestCapital = companiesWithLowestCapital;
	}

	/**
	 * @return the investorsWithHighestNumberOfShares
	 */
	public List<Investor> getInvestorsWithHighestNumberOfShares() {
		return investorsWithHighestNumberOfShares;
	}

	/**
	 * @param investorsWithHighestNumberOfShares the investorsWithHighestNumberOfShares to set
	 */
	public void setInvestorsWithHighestNumberOfShares(List<Investor> investorsWithHighestNumberOfShares) {
		this.investorsWithHighestNumberOfShares = investorsWithHighestNumberOfShares;
	}

	/**
	 * @return the investorsWithLowestNumberOfShares
	 */
	public List<Investor> getInvestorsWithLowestNumberOfShares() {
		return investorsWithLowestNumberOfShares;
	}

	/**
	 * @param investorsWithLowestNumberOfShares the investorsWithLowestNumberOfShares to set
	 */
	public void setInvestorsWithLowestNumberOfShares(List<Investor> investorsWithLowestNumberOfShares) {
		this.investorsWithLowestNumberOfShares = investorsWithLowestNumberOfShares;
	}

	/**
	 * @return the investorWithLeastNumberOfCompanies
	 */
	public List<Investor> getInvestorWithLeastNumberOfCompanies() {
		return investorWithLeastNumberOfCompanies;
	}

	/**
	 * @param investorWithLeastNumberOfCompanies the investorWithLeastNumberOfCompanies to set
	 */
	public void setInvestorWithLeastNumberOfCompanies(List<Investor> investorWithLeastNumberOfCompanies) {
		this.investorWithLeastNumberOfCompanies = investorWithLeastNumberOfCompanies;
	}

}
