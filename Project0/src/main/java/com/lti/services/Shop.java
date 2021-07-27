package com.lti.services;

import com.lti.exceptions.ItemExists;
import com.lti.exceptions.ItemHasOffer;
import com.lti.exceptions.SearchFailed;
import com.lti.models.Item;
import com.lti.models.Offer;

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
	public Item getItem(int Itemid) throws SearchFailed{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteItem(int Itemid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStock(int Itemid, int stock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getOffers(int Itemid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeOffer(int itemId, int customerId, float amount) throws ItemHasOffer{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptOffer(Offer offer, int choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewOwnedItems(int customerId) {
		// TODO Auto-generated method stub
		
	}

}
