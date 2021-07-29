package com.lti.models;

public class Offer {
	
	private int id;
	private float offerAmount;
	private int itemId;
	private int customerId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getOfferAmount() {
		return offerAmount;
	}
	public void setOfferAmount(float offerAmount) {
		this.offerAmount = offerAmount;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerId;
		result = prime * result + id;
		result = prime * result + itemId;
		result = prime * result + Float.floatToIntBits(offerAmount);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offer other = (Offer) obj;
		if (customerId != other.customerId)
			return false;
		if (id != other.id)
			return false;
		if (itemId != other.itemId)
			return false;
		if (Float.floatToIntBits(offerAmount) != Float.floatToIntBits(other.offerAmount))
			return false;
		return true;
	}
	public Offer(int id, float offerAmount, int itemId, int customerId) {
		super();
		this.id = id;
		this.offerAmount = offerAmount;
		this.itemId = itemId;
		this.customerId = customerId;
	}
	@Override
	public String toString() {
		return "Offer [id=" + id + ", offerAmount=" + offerAmount + ", itemId=" + itemId + ", customerId=" + customerId
				+ "]";
	}
	
	

}
