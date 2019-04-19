/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cct.stockmarket.simulation.factories;

import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.models.SizeType;

/**
 *
 * @author lucivalnascimento
 */
public class CompanyFactory {
    /**
     * Create a new Company object
     * @param name Company name
     * @param numberOfShares initial number of shares of the Company
     * @param availableShares initial number of available shares to be sold
     * @param sharePrice initial price of the shares
     * @return 
     */
    public static Company createCompany(
            String name, 
            Integer numberOfShares, 
            Integer availableShares, 
            Float sharePrice
    ) {
    	
    	SizeType type;
    	
    	if(numberOfShares * sharePrice <= 30000) {
    		type = SizeType.SMALL;
    	}else if(numberOfShares * sharePrice > 30000 && numberOfShares * sharePrice <= 80000){
    		type = SizeType.MEDIUM;
    	}else{
    		type = SizeType.LARGE;
    	}
        
        return new Company(name, numberOfShares, availableShares, sharePrice, type);
    }

}



















