package com.cct.stockmarket.simulation.iterator;

import java.util.List;

import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.SizeType;

public interface CollectionInterface<T> {

    public void remove(T i);
    public T get(int position);
	public List<T> getAll();
	public IteratorInterface<T> iterator();
	public int size();
    public IteratorInterface<T> iterator(SizeType type);
	void add(T i);
}
