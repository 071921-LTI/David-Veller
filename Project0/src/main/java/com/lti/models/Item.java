package com.lti.models;

public class Item {

	
	private int stock;
	private int price;
	private String name;
	private String seller;
	
	
	
	
	public Item(String name, int stock,  String seller, int price) {
		super();
		this.stock = stock;
		this.name = name;
		this.seller = seller;
		this.price = price;
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
