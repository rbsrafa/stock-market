package com.cct.stockmarket.simulation.iterators.investor;

import java.util.List;

import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.SizeType;

public class InvestorIterator implements InvestorIteratorInterface{

	private List<Investor> investors;
	private SizeType type;
	private int position;
	
	public InvestorIterator() {	}
	
	public InvestorIterator(List<Investor> investors) {
		this.investors = investors;
	}
	
	public InvestorIterator(List<Investor> investors, SizeType type) {
		this.investors = investors;
		this.type = type;
	}
	
	@Override
	public boolean hasNext() {
		while(position < investors.size()) {
			return true;
		}
		
		return false;
	}

	@Override
	public Investor next() {
		Investor i = this.investors.get(position);
		position++;
		return i;
	}

}
