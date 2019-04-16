/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cct.stockmarket.api.factories;

import com.cct.stockmarket.api.models.Company;

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
        
        return new Company(name, numberOfShares, availableShares, sharePrice);
    }
}
