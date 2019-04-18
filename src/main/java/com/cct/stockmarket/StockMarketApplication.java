package com.cct.stockmarket;

import com.cct.stockmarket.api.generators.CompanyGenerator;
import com.cct.stockmarket.api.generators.InvestorGenerator;
import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.Transaction;
import com.cct.stockmarket.api.repositories.CompanyRepository;
import com.cct.stockmarket.api.repositories.InvestorRepository;
import com.cct.stockmarket.api.repositories.TransactionRepository;

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
    
    @Autowired
    TransactionRepository transactions;


    public static void main(String[] args) {
            SpringApplication.run(StockMarketApplication.class, args);
    }
        
    @Override
    public void run(String... args) throws Exception {
        
        
        List<Company> companiesList = new ArrayList<>(CompanyGenerator.generateCompanies(100));
        this.companies.saveAll(companiesList);
        List<Investor> investorsList = new ArrayList<>(InvestorGenerator.generateInvestors(100));
        this.investors.saveAll(investorsList);
        
        // Find company id 1
        Company cOne = companies.findById(1L).orElse(null);
        System.out.println("********");
        System.out.println(cOne);
        System.out.println("********");
        
        // Find investor id 1
        Investor iOne = investors.findById(1L).orElse(null);
        System.out.println("********");
        System.out.println(iOne);
        System.out.println("********");
        
        // Investor one is buying a share from Company one
        Transaction tOne = new Transaction(iOne, cOne, cOne.getSharePrice());
        
        // Update company 1 available shares
        cOne.setAvailableShares(cOne.getAvailableShares()-1);
        // Update investor 1 budget
        iOne.setBudget(iOne.getBudget()-cOne.getSharePrice());
        
        // Save all changes
        tOne = this.transactions.save(tOne);
        cOne = this.companies.save(cOne);
        iOne = this.investors.save(iOne);
        
        // Print all updated entities
        
        System.out.println("********");
        System.out.println(tOne);
        System.out.println("********");
        
        cOne = companies.findById(1L).orElse(null);
        System.out.println("********");
        System.out.println(cOne);
        System.out.println("********");
        
        iOne = investors.findById(1L).orElse(null);
        System.out.println("********");
        System.out.println(iOne);
        System.out.println("********");
    }

}
