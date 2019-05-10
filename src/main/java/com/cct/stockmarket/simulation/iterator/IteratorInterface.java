package com.cct.stockmarket.simulation.iterator;

import com.cct.stockmarket.api.models.Investor;

public interface IteratorInterface<T> {
	public boolean hasNext();	
    public T next();
}
