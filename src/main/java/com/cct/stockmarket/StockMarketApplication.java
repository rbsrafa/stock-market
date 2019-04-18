package com.cct.stockmarket;

import com.cct.stockmarket.api.generators.CompanyGenerator;
import com.cct.stockmarket.api.generators.InvestorGenerator;
import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.repositories.CompanyRepository;
import com.cct.stockmarket.api.repositories.InvestorRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StockMarketApplication implements CommandLineRunner{
    
    @Autowired
    CompanyRepository companies;
    
    @Autowired
    InvestorRepository investors;


    public static void main(String[] args) {
            SpringApplication.run(StockMarketApplication.class, args);
    }
        
    @Override
    public void run(String... args) throws Exception {
        
        
        List<Company> companiesList = new ArrayList<>(CompanyGenerator.generateCompanies(10));
        this.companies.saveAll(companiesList);
        List<Investor> investorsList = new ArrayList<>(InvestorGenerator.generateInvestors(10));
        this.investors.saveAll(investorsList);
        //companiesList = CompanyGenerator.generateCompanies();
    }

}
