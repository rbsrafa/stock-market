package com.cct.stockmarket.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cct.stockmarket.api.models.abstracts.AuditModel;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="simulations")
public class Simulation extends AuditModel{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Integer numberOfTransactions;
	
	public Simulation() {}
	
	public Simulation(Integer numberOfTransaction) {
		this.numberOfTransactions = numberOfTransaction;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Simulation [id=" + id + ", numberOfTransactions=" + numberOfTransactions + ", createdAt="
				+ getCreatedAt() + ", updatedAt=" + getUpdatedAt() + "]";
	}



}
