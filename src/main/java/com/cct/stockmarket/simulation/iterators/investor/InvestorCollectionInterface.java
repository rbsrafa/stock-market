package com.cct.stockmarket.simulation.iterators.investor;

import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.SizeType;

public interface InvestorCollectionInterface {
	public void add(Investor i);
    public void remove(Investor i);
    public InvestorIteratorInterface iterator(SizeType type);
}
