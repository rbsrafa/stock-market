package com.cct.stockmarket.api.models;

import com.cct.stockmarket.api.models.abstracts.AuditModel;
import com.fasterxml.jackson.annotation.JsonInclude;

//@Entity
//@Table(name="transactions")
public class Transaction extends AuditModel{
	
	private static Long counter = 0L;
	
//	@Id
	private final Long id;
	
//	@Column(nullable=false)
	private Float value;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="investor_id", nullable=false)
//	@OnDelete(action=OnDeleteAction.CASCADE)
	private Investor investor;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="company_id", nullable=false)
//	@OnDelete(action=OnDeleteAction.CASCADE)
	private Company company;
	
	public Transaction() {
		this.id = ++counter;
	}
	
	public Transaction(
		Investor investor, 
		Company company, 
		Float value
	) {
		this.id = ++counter;
		this.investor = investor;
		this.company = company;
		this.value = value;
	}

	/**
	 * @return the investor
	 */
	public Investor getInvestor() {
		return investor;
	}

	/**
	 * @param investor the investor to set
	 */
	public void setInvestor(Investor investor) {
		this.investor = investor;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the value
	 */
	public Float getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Float value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", value=" + value + ", investor=" + investor + ", company=" + company
				+ ", createdAt=" + getCreatedAt() + ", updatedAt=" + getUpdatedAt() + "]";
	}
	
	

}
