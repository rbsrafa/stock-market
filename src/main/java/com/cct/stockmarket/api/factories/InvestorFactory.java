/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cct.stockmarket.api.factories;

import com.cct.stockmarket.api.models.Investor;

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
        
        return new Investor(budget, firstName, lastName);
    }
}
