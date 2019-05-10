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
	
	private Investor(InvestorBuilder investor) {
		this.budget = investor.budget;
		this.firstName = investor.firstName;
		this.lastName = investor.lastName;
		this.type = investor.type;
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
	 * @return the type
	 */
	public SizeType getType() {
		return type;
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
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Investor [id=" + id + ", budget=" + budget + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", type=" + type + ", createdAt=" + getCreatedAt() + ", updatedAt=" + getUpdatedAt() + "]";
	}

        public static class InvestorBuilder {
        private Float budget;
        private String firstName;
        private String lastName;
        private SizeType type;
        
        /**
     * Create a new Investor object
     * @param budget initial value the Investor has to spend
     * @param firstName Investor first name
     * @param lastName Investor last name
     * @return 
     */
        public InvestorBuilder(
                Float budget,
                String firstName,
                String lastName
        ) {
            this.budget = budget;
            this.firstName = firstName;
            this.lastName = lastName;
            this.type = initializeType(budget);
        }
        
        /**
         * Initialize Company type based on the company's number of shares
         * @param budget
         * @return 
         */
        private SizeType initializeType(Float budget){
                if(budget <= 4000) {
                    return SizeType.SMALL;
                }else if(budget > 4000 && budget <= 7000){
                    return SizeType.MEDIUM;
                }else{
                    return SizeType.LARGE;
                }
        }
        
        /**
         * Build a new Investor object based on the InvestorBuilder object
         * used
         * @return 
         */
        public Investor build() {
            return new Investor(this);
        }
        
    }
    

}
