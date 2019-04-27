package com.cct.stockmarket.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cct.stockmarket.api.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	List<Company> findByAvailableSharesGreaterThan(Integer value);
	
	@Query("select c from Company c "
			+ "inner join c.simulation s "
			+ "where s.id = :id")
	List<Company> findCompaniesBySimulation(@Param("id") Long id);
	
        @Query(value="SELECT * " +
                "FROM companies c " +
                "WHERE (c.number_of_shares * c.share_price) = " +
                "        (SELECT max(c2.number_of_shares * c2.share_price) " +
                "          FROM companies c2 " +
                "          WHERE simulation_id = :simulationId " +
                "        )",
                nativeQuery = true)
        List<Company> findCompanyWithHighestCapital(@Param("simulationId") Long simulationId);
	
        @Query(value="SELECT * " +
                "FROM companies c " +
                "WHERE (c.number_of_shares * c.share_price) = " +
                "        (SELECT min(c2.number_of_shares * c2.share_price) " +
                "          FROM companies c2 " +
                "          WHERE simulation_id = :simulationId " +
                "        )",
                nativeQuery = true)
        List<Company> findCompanyWithLowestCapital(@Param("simulationId") Long simulationId);
}
