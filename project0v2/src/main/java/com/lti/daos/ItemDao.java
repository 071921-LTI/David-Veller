package com.lti.daos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.lti.models.Item;

public interface ItemDao {
	
	public abstract int addItem(Item item) throws IOException, SQLException;
	public abstract int deleteItem(int itemId) throws IOException, SQLException;
	public abstract List<Item> viewAllItems() throws IOException, SQLException;
	public abstract List<Item> viewOwnedItems(int ownerId) throws IOException, SQLException;
	public abstract int updateOwner(int itemId, int ownerId) throws IOException, SQLException;
	public abstract int updateValue(int itemId, float value) throws IOException, SQLException;
	public abstract int updateRemainingValue(int itemId, float remainingValue) throws IOException, SQLException;
	public abstract Item getItemById(int itemId) throws IOException, SQLException;

}
