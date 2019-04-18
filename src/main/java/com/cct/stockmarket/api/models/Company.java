package com.cct.stockmarket.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name="companies")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String name;
	
	@Column(nullable=false)
	@Min(500) @Max(1000)
	private Integer numberOfShares;
	
	@Column(nullable=false)
	private Integer availableShares;
	
	@Column(nullable=false)
	private Float sharePrice;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private SizeType type;	
	
	public Company() {}
	
	public Company(
		String name, 
		Integer numberOfShares, 
		Integer availableShares, 
		Float sharePrice,
		SizeType type
	) {
		this.name = name;
		this.numberOfShares = numberOfShares;
		this.availableShares = availableShares;
		this.sharePrice = sharePrice;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

        @Override
        public String toString() {
            return "Company{" + "name=" + name + ", numberOfShares=" + numberOfShares + ", availableShares=" + availableShares + ", sharePrice=" + sharePrice + "}\n";
        }
        
        
}
