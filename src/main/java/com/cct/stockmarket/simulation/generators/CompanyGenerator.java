/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cct.stockmarket.simulation.generators;

import com.cct.stockmarket.api.models.Company;
import com.cct.stockmarket.api.factories.CompanyFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author lucivalnascimento
 */
public abstract class CompanyGenerator {
    
    /**
     * Generates a n number of random companies
     * @param n number of companies to be generated
     * @return 
     */
    public static ArrayList generateCompanies(int n){
        ArrayList<Company> companiesList = new ArrayList<>();
        int numberOfShares[] = new int[n];
        int maxShare = 1000;
        int minShare = 500;
        int maxPrice = 100;
        int minPrice = 10;
        
        for(int i = 0; i < n; i++){
            numberOfShares[i] = (int)randomNumberGenerator(minShare, maxShare);
            companiesList.add(CompanyFactory.createCompany(
                    "test CO."+numberOfShares[i]+i, 
                    numberOfShares[i], 
                    numberOfShares[i], 
                    randomNumberGenerator(minPrice, maxPrice)
                )
            );
        }
        System.out.println("\n" + companiesList.toString() + " \n");
        return companiesList;
    }
    
    /**
     * Generates a random number for a range of numbers
     * @param min minimum value for the random number generator
     * @param max maximum value for the random number generator
     * @return 
     */
    private static float randomNumberGenerator(int min, int max) {
    	DecimalFormat df = new DecimalFormat("0.00");
    	return Float.parseFloat(df.format((float)(Math.random() * ((max - min) + 1)) + min));
    }
}
