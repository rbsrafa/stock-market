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
	
        @Query(value="SELECT * " +
                    "FROM investors i " +
                    "WHERE i.number_of_shares = " +
                    "        (SELECT max(i2.number_of_shares) " +
                    "          FROM investors i2 " +
                    "          WHERE simulation_id = :simulationId " +
                    "        ) " +
                    "  AND simulation_id = :simulationId",
                nativeQuery=true)
        List<Investor>findInvestorWithHighestNumberShares(@Param("simulationId") Long simulationId);
	
        @Query(value="SELECT * " +
                    "FROM investors i " +
                    "WHERE i.number_of_shares = " +
                    "        (SELECT min(i2.number_of_shares) " +
                    "          FROM investors i2 " +
                    "          WHERE simulation_id = :simulationId " +
                    "        ) " +
                    "  AND simulation_id = :simulationId",
                nativeQuery=true)
        List<Investor>findInvestorWithLowestNumberShares(@Param("simulationId") Long simulationId);
	
        @Query(value="SELECT * " +
                    "FROM investors i " +
                    "WHERE i.number_of_companies = " +
                    "        (SELECT max(i2.number_of_companies) " +
                    "          FROM investors i2 " +
                    "          WHERE simulation_id = :simulationId " +
                    "        ) " +
                    "  AND simulation_id = :simulationId",
                nativeQuery=true)
        List<Investor>findInvestorWithHighestNumberCompanies(@Param("simulationId") Long simulationId);
	
        @Query(value="SELECT * " +
                    "FROM investors i " +
                    "WHERE i.number_of_companies = " +
                    "        (SELECT min(i2.number_of_companies) " +
                    "          FROM investors i2 " +
                    "          WHERE simulation_id = :simulationId " +
                    "        ) " +
                    "  AND simulation_id = :simulationId",
                nativeQuery=true)
        List<Investor>findInvestorWithLowestNumberCompanies(@Param("simulationId") Long simulationId);
	
}
