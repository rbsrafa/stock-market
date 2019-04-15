package com.cct.stockmarket.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cct.stockmarket.api.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
