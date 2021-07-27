package com.lti.models;

public class Offer {

	private int offerId;
	private int amount;
	private int itemId;
	private int customerId;
	
	
	
	public Offer(int offerId, int amount, int itemId, int customerId) {
		super();
		this.offerId = offerId;
		this.amount = amount;
		this.itemId = itemId;
		this.customerId = customerId;
	}
	
	public int getOfferId() {
		return offerId;
	}
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	
}
