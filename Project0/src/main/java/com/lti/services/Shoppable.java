package com.lti.services;

import com.lti.exceptions.ItemExists;
import com.lti.exceptions.ItemHasOffer;
import com.lti.exceptions.SearchFailed;
import com.lti.models.Item;
import com.lti.models.Offer;

public interface Shoppable {
	
	
	public void getAll();
	//public Item searchItem(String name) throws SearchFailed;
	//public Item searchSeller(String seller) throws SearchFailed;
	public void addItem(String name, int stock, String seller) throws ItemExists;
	public void addStock(int Itemid, int stock);
	public Item getItem(int Itemid) throws SearchFailed;
	public void deleteItem(int Itemid);
	public void getOffers(int Itemid);
	public void makeOffer(int itemId, int customerId, float amount) throws ItemHasOffer;
	public void acceptOffer(Offer offer, int choice);
	public void viewOwnedItems(int id);
	

}
