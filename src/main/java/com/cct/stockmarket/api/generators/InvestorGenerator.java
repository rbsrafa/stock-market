/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cct.stockmarket.api.generators;

import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.factories.InvestorFactory;
import java.util.ArrayList;

/**
 *
 * @author lucivalnascimento
 */
public abstract class InvestorGenerator {
    
    /**
     * Generates a n number of random investors
     * @param n number of investors to be generated
     * @return 
     */
    public static ArrayList generateInvestors(int n){
        ArrayList<Investor> investorsList = new ArrayList<>();
        int numberOfShares[] = new int[n];
        int maxBudget = 10000;
        int minBudget = 1000;
        
        for(int i = 0; i < n; i++){
            numberOfShares[i] = (int)randomNumberGenerator(maxBudget, minBudget);
            investorsList.add(InvestorFactory.createInvestor(
                    randomNumberGenerator(maxBudget, minBudget),
                    "John"+numberOfShares[i]+i,
                    "Doe"+numberOfShares[i]+i
                )
            );
        }
        System.out.println("\n" + investorsList.toString() + " \n");
        return investorsList;
    }
    
    /**
     * Generates a random number for a range of numbers
     * @param min minimum value for the random number generator
     * @param max maximum value for the random number generator
     * @return 
     */
    private static float randomNumberGenerator(int min, int max) {
        return (float)(Math.random() * ((max - min) + 1)) + min;
    }
}
