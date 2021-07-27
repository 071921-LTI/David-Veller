package com.lti.services;

import com.lti.exceptions.ItemExists;
import com.lti.exceptions.SearchFailed;
import com.lti.models.Item;

public class Shop implements Shoppable{

	@Override
	public void getAll() {
		// TODO Auto-generated method stub
		
	}

	/*
	@Override
	public Item searchItem(String name) throws SearchFailed {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item searchSeller(String seller) throws SearchFailed {
		// TODO Auto-generated method stub
		return null;
	}
	*/

	@Override
	public void addItem(String name, int stock, String seller) throws ItemExists {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item getItem(int id) throws SearchFailed{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteItem(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStock(int id, int stock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getOffers(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeOffer(int itemId, int customerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptOffer(int itemId, int employeeId, int choice) {
		// TODO Auto-generated method stub
		
	}

}
