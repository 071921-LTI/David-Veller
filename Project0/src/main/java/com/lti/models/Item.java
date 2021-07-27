package com.lti.models;

import java.util.ArrayList;

public class Item {

	
	private int stock;
	private int price;
	private String name;
	private String seller;
	private int id;
	private boolean owned;
	private String owner;
	
	
	
	
	public Item(int id, String name, int stock,  String seller, int price) {
		super();
		this.id = id;
		this.stock = stock;
		this.name = name;
		this.seller = seller;
		this.price = price;
		this.owned = false;
		this.owner = seller;
	}
	






	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public boolean isOwned() {
		return owned;
	}


	public void setOwned(boolean owned) {
		this.owned = owned;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	
	
}
