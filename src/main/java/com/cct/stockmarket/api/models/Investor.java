package com.cct.stockmarket.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cct.stockmarket.api.models.abstracts.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="investors")
public class Investor extends AuditModel{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private Float budget;
	
	@Column(nullable=false, length=15)
	private String firstName;
	
	@Column(nullable=false, length=20)
	private String lastName;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private SizeType type;
	
	@Column
	private Integer numberOfCompanies = 0;
	
	@Column
	private Integer numberOfShares = 0;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="simulation_id", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
    @JsonIgnore()
	private Simulation simulation;
	
	public Investor() {}
	
	public Investor(
		Float budget,
		String firstName,
		String lastName,
		SizeType type
	) {
		this.budget = budget;
		this.firstName = firstName;
		this.lastName = lastName;
		this.type = type;
	}
	

	/**
	 * @return the numberOfShares
	 */
	public Integer getNumberOfShares() {
		return numberOfShares;
	}

	/**
	 * @param numberOfShares the numberOfShares to set
	 */
	public void setNumberOfShares(Integer numberOfShares) {
		this.numberOfShares = numberOfShares;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public SizeType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SizeType type) {
		this.type = type;
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the budget
	 */
	public Float getBudget() {
		return budget;
	}

	/**
	 * @param budget the budget to set
	 */
	public void setBudget(Float budget) {
		this.budget = budget;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Investor [id=" + id + ", budget=" + budget + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", type=" + type + ", createdAt=" + getCreatedAt() + ", updatedAt=" + getUpdatedAt() + "]";
	}

    

}
