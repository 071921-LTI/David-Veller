package com.lti.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lti.models.Item;
import com.lti.util.ConnectionUtil;

public class ItemPostgres implements ItemDao {

	@Override
	public int addItem(Item item) throws IOException, SQLException {
		String sql = "insert into items (item_name, item_seller, item_owner, item_value, item_remaining_value) values (?, ?, ?, ?, ?) returning item_id;";
		int id = -1;

		Connection con = ConnectionUtil.getConnectionFromFile();

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, item.getName());
		ps.setInt(2, item.getSellerId());
		ps.setInt(3, item.getOwnerId());
		ps.setFloat(4, item.getValue());
		ps.setFloat(5, item.getRemainingValue());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			id = rs.getInt("item_id");
		}

		return id;
	}

	@Override
	public int deleteItem(int itemId) throws IOException, SQLException {
		int rowsChanged = -1;

		String sql = "delete from items where item_id = ?;";

		Connection con = ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);

		ps.setInt(1, itemId);

		rowsChanged = ps.executeUpdate();

		return rowsChanged;
	}

	@Override
	public List<Item> viewAllItems() throws IOException, SQLException {
		List<Item> items = new ArrayList<>();
		String sql = "select * from items where item_seller = item_owner;";

		Connection con = ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int itemId = rs.getInt("item_id");
			String name = rs.getString("item_name");
			int sellerId = rs.getInt("item_seller");
			int ownerId = rs.getInt("item_owner");
			float itemValue = rs.getFloat("item_value");
			float itemRemainingValue = rs.getFloat("item_remaining_value");

			Item item = new Item(itemId, name, sellerId, ownerId, itemValue, itemRemainingValue);
			items.add(item);
		}

		return items;
	}

	@Override
	public List<Item> viewOwnedItems(int ownerId) throws IOException, SQLException {
		List<Item> items = new ArrayList<>();
		String sql = "select * from items where item_owner = ?;";

		Connection con = ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);

		ps.setInt(1, ownerId);
		
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int itemId = rs.getInt("item_id");
			String name = rs.getString("item_name");
			int sellerId = rs.getInt("item_seller");
			float itemValue = rs.getFloat("item_value");
			float itemRemainingValue = rs.getFloat("item_remaining_value");

			Item item = new Item(itemId, name, sellerId, ownerId, itemValue, itemRemainingValue);
			items.add(item);
		}

		return items;
	}

	@Override
	public int updateOwner(int itemId, int ownerId) throws IOException, SQLException {
		int rowsChanged = -1;
		String sql = "update items set item_owner = ? where item_id = ?;";
		
		Connection con =  ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setInt(1, ownerId);
		ps.setInt(2,  itemId);
		
		rowsChanged = ps.executeUpdate();
		
		return rowsChanged;
	}

	@Override
	public int updateValue(int itemId, float value) throws IOException, SQLException {
		int rowsChanged = -1;
		String sql = "update items set item_value = ? where item_id = ?;";
		
		Connection con =  ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setFloat(1, value);
		ps.setInt(2,  itemId);
		
		rowsChanged = ps.executeUpdate();
		
		return rowsChanged;
	}

	@Override
	public int updateRemainingValue(int itemId, float remainingValue) throws IOException, SQLException {
		int rowsChanged = -1;
		String sql = "update items set item_remaining_value = ? where item_id = ?;";
		
		Connection con =  ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setFloat(1, remainingValue);
		ps.setInt(2,  itemId);
		
		rowsChanged = ps.executeUpdate();
		
		return rowsChanged;
	}

	@Override
	public Item getItemById(int itemId) throws IOException, SQLException {
		Item item = null;
		String sql = "select * from items where item_id = ?";
		
		Connection con = ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setInt(1, itemId);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			String name = rs.getString("item_name");
			int sellerId = rs.getInt("item_seller");
			int ownerId = rs.getInt("item_owner");
			float itemValue = rs.getFloat("item_value");
			float itemRemainingValue = rs.getFloat("item_remaining_value");

			item = new Item(itemId, name, sellerId, ownerId, itemValue, itemRemainingValue);
		}
		
		
		return item;
	}

	@Override
	public List<Item> viewSoldItems(int sellerId) throws IOException, SQLException {
		List<Item> items = new ArrayList<>();
		String sql = "select * from items where item_seller = ? AND item_seller <> item_owner;";

		Connection con = ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);

		ps.setInt(1, sellerId);
		
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int itemId = rs.getInt("item_id");
			String name = rs.getString("item_name");
			int ownerId = rs.getInt("item_owner");
			float itemValue = rs.getFloat("item_value");
			float itemRemainingValue = rs.getFloat("item_remaining_value");

			Item item = new Item(itemId, name, sellerId, ownerId, itemValue, itemRemainingValue);
			items.add(item);
		}

		return items;
	}

}
