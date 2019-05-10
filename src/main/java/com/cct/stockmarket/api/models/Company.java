package com.cct.stockmarket.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cct.stockmarket.api.models.abstracts.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="companies")
public class Company extends AuditModel{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	@Min(500) @Max(1000)
	private Integer numberOfShares;
	
	@Column(nullable=false)
	private Integer availableShares;
	
	@Column(nullable=false, columnDefinition="decimal(12,2)")
	private Float sharePrice;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private SizeType type;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="simulation_id", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JsonIgnore()
	private Simulation simulation;
	
	
	public Company() {}
	
	private Company(CompanyBuilder company) {
		this.name = company.name;
		this.numberOfShares = company.numberOfShares;
		this.availableShares = company.availableShares;
		this.sharePrice = company.sharePrice;
		this.type = company.type;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the numberOfShares
	 */
	public Integer getNumberOfShares() {
		return numberOfShares;
	}

	/**
	 * @return the availableShares
	 */
	public Integer getAvailableShares() {
		return availableShares;
	}

	/**
	 * @param availableShares the availableShares to set
	 */
	public void setAvailableShares(Integer availableShares) {
		this.availableShares = availableShares;
	}

	/**
	 * @return the sharePrice
	 */
	public Float getSharePrice() {
		return sharePrice;
	}

	/**
	 * @param sharePrice the sharePrice to set
	 */
	public void setSharePrice(Float sharePrice) {
		this.sharePrice = sharePrice;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", numberOfShares=" + numberOfShares + ", availableShares="
				+ availableShares + ", sharePrice=" + sharePrice + ", type=" + type + ", createdAt="
				+ getCreatedAt() + ", updatedAt=" + getUpdatedAt() + "]";
	}

	public static class CompanyBuilder {
        private String name;
        private Integer numberOfShares;
        private Integer availableShares;
        private Float sharePrice;
        private SizeType type;
        
        /**
         * Create a new CompanyBuilder object
         * @param name Company name
         * @param numberOfShares initial number of shares of the Company
         * @param availableShares initial number of available shares to be sold
         * @param sharePrice initial price of the shares
         * @return 
         */
        public CompanyBuilder(
                String name, 
                Integer numberOfShares, 
                Integer availableShares, 
                Float sharePrice
        ) {
            this.name = name;
            this.numberOfShares = numberOfShares;
            this.availableShares = availableShares;
            this.sharePrice = sharePrice;
            this.type = initializeType(numberOfShares);
        }
        
        /**
         * Initialize Company type based on the company's number of shares
         * @param numberOfShares
         * @return 
         */
        private SizeType initializeType(Integer numberOfShares){
                if(numberOfShares * sharePrice <= 30000) {
                    return SizeType.SMALL;
                }else if(numberOfShares * sharePrice > 30000 && numberOfShares * sharePrice <= 80000){
                    return SizeType.MEDIUM;
                }else{
                    return SizeType.LARGE;
                }
        }
        
        /**
         * Build a new Company object based on the CompanyBuilder object
         * used
         * @return 
         */
        public Company build() {
            return new Company(this);
        }
        
    }

        
        
}	
