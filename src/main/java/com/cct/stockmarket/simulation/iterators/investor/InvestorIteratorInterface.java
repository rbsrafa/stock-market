package com.cct.stockmarket.simulation.iterators.investor;

import com.cct.stockmarket.api.models.Investor;

public interface InvestorIteratorInterface {
	public boolean hasNext();	
    public Investor next();
}
