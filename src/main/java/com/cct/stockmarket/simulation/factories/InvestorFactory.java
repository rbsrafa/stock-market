/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cct.stockmarket.simulation.factories;

import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.SizeType;

/**
 *
 * @author lucivalnascimento
 */
public class InvestorFactory {
    /**
     * Create a new Investor object
     * @param budget initial value the Investor has to spend
     * @param firstName Investor first name
     * @param lastName Investor last name
     * @return 
     */
    public static Investor createInvestor(
            Float budget,
            String firstName,
            String lastName
    ) {
    	
    	SizeType type;
    	
    	if(budget <= 4000) {
    		type = SizeType.SMALL;
    	}else if(budget > 4000 && budget <= 7000){
    		type = SizeType.MEDIUM;
    	}else{
    		type = SizeType.LARGE;
    	}
        
        return new Investor(budget, firstName, lastName, type);
    }
}
