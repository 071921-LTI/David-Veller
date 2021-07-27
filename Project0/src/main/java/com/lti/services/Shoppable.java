package com.lti.services;

import com.lti.exceptions.ItemExists;
import com.lti.exceptions.SearchFailed;
import com.lti.models.Item;

public interface Shoppable {
	
	
	public void getAll();
	//public Item searchItem(String name) throws SearchFailed;
	//public Item searchSeller(String seller) throws SearchFailed;
	public void addItem(String name, int stock, String seller) throws ItemExists;
	public void addStock(int id, int stock);
	public Item getItem(int id) throws SearchFailed;
	public void deleteItem(int id);
	public void getOffers(int id);
	public void makeOffer(int itemId, int customerId);
	public void acceptOffer(int itemId, int employeeId, int choice);
	

}
