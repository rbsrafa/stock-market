package com.cct.stockmarket.simulation.iterators.investor;

import java.util.ArrayList;
import java.util.List;

import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.SizeType;

public class InvestorCollection implements InvestorCollectionInterface{

	private List<Investor> investors;
	
	public InvestorCollection() {
		investors = new ArrayList<>();
	}
	
	public InvestorCollection(List<Investor> investors) {
		this.investors = investors;
	}
	
	@Override
	public void add(Investor i) {
		this.investors.add(i);
	}

	@Override
	public void remove(Investor i) {
		this.investors.remove(i);
	}

	@Override
	public InvestorIteratorInterface iterator(SizeType type) {
		return new InvestorIterator(this.investors, type);
	}
	
	public Investor get(int position){
		return this.investors.get(position);
	}
	
	public List<Investor> getAll(){
		return this.investors;
	}
	
	public InvestorIteratorInterface iterator() {
		return new InvestorIterator(this.investors);
	}
	
	public int size() {
		return this.investors.size();
	}

}
