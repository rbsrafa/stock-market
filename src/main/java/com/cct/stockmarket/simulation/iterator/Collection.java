package com.cct.stockmarket.simulation.iterator;

import java.util.ArrayList;
import java.util.List;

import com.cct.stockmarket.api.models.Investor;
import com.cct.stockmarket.api.models.SizeType;

public class Collection<T> implements CollectionInterface{

	private List<T> list;
	
	public Collection() {
		list = new ArrayList<>();
	}
	
	public Collection(List<T> list) {
		this.list = list;
	}
	
	@Override
	public void add(Object o) {
		this.list.add((T)o);
	}

	@Override
	public void remove(Object o) {
		this.list.remove((T)o);
	}

	@Override
	public IteratorInterface<T> iterator(SizeType type) {
		return new Iterator(this.list, type);
	}
	
	@Override
	public T get(int position){
		return this.list.get(position);
	}
	
	@Override
	public List<T> getAll(){
		return this.list;
	}
	
	@Override
	public IteratorInterface<T> iterator() {
		return new Iterator(this.list);
	}
	
	@Override
	public int size() {
		return this.list.size();
	}

}
