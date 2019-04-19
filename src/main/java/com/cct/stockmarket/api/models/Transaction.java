package com.cct.stockmarket.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name="transactions")
public class Transaction extends AuditModel{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private Float value;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="investor_id", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Investor investor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Company company;
	
	public Transaction() {}
	
	public Transaction(
		Investor investor, 
		Company company, 
		Float value
	) {
		this.investor = investor;
		this.company = company;
		this.value = value;
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
