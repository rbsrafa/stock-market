package com.cct.stockmarket.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cct.stockmarket.api.models.abstracts.AuditModel;

@Entity
@Table(name="investors")
public class Investor extends AuditModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public String toString() {
        return "Investor{" + " budget=" + budget + ", firstName=" + firstName + ", lastName=" + lastName + "}\n";
    }

}
