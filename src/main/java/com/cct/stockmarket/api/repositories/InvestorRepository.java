package com.cct.stockmarket.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cct.stockmarket.api.models.Investor;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long>{

}
