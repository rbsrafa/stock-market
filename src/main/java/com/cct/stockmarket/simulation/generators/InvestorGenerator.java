/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cct.stockmarket.simulation.generators;

import com.cct.stockmarket.api.models.Investor;

import java.text.DecimalFormat;
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
    public static ArrayList<Investor> generateInvestors(int n, float min, float max){
        ArrayList<Investor> investorsList = new ArrayList<>();
        int numberOfShares[] = new int[n];
        float maxBudget = max;
        float minBudget = min;
        
        for(int i = 0; i < n; i++){
            numberOfShares[i] = (int)randomNumberGenerator(maxBudget, minBudget);
            Investor.InvestorBuilder tempBuilder = new Investor.InvestorBuilder(
                    randomNumberGenerator(maxBudget, minBudget),
                    "John"+numberOfShares[i]+i,
                    "Doe"+numberOfShares[i]+i
            );
            investorsList.add(tempBuilder.build());
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
    private static float randomNumberGenerator(float min, float max) {
    	DecimalFormat df = new DecimalFormat("0.00");
    	return Float.parseFloat(df.format((float)(Math.random() * ((max - min) + 1)) + min));
    }
}
