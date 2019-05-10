package com.cct.stockmarket.simulation.iterator;

import java.util.List;

import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.SizeType;

public class Iterator<T> implements IteratorInterface{

	private List<T> list;
	private SizeType type;
	private int position;
	
	public Iterator() {	}
	
	public Iterator(List<T> list) {
		this.list = list;
	}
	
	public Iterator(List<T> list, SizeType type) {
		this.list = list;
		this.type = type;
	}
	
	@Override
	public boolean hasNext() {
		while(position < list.size()) {
			return true;
		}
		
		return false;
	}

	@Override
	public T next() {
		T i = this.list.get(position);
		position++;
		return i;
	}

}
