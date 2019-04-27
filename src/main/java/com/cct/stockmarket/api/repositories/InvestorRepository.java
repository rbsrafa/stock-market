package com.cct.stockmarket.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.models.Investor;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long>{
	
	@Query("select i from Investor i "
			+ "inner join i.simulation s "
			+ "where s.id = :id")
	List<Investor> findInvestorsBySimulation(@Param("id") Long id);
	
	List<Investor>findByBudgetGreaterThan(Float value);
	
	
}
