package com.lti.daos;

import com.lti.exceptions.ItemExists;
import com.lti.exceptions.SearchFailed;
import com.lti.models.Item;

public interface ItemDao {
	
	public Item getItem(int id) throws SearchFailed;
	public void addItem(String name, String seller, int stock, int price) throws ItemExists;
	public void deleteItem(int id);
	public void getOwnedItems(int id) throws SearchFailed;
	public void addStock(int id, int stock);
	public void getOffers(int itemId);
	public void changeOwner(int customerId);
	

}
