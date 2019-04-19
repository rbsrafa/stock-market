package com.cct.stockmarket.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cct.stockmarket.api.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	List<Company>findByAvailableSharesGreaterThan(Integer value);
	
}
