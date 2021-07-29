package com.lti.models;

public class Item {

	private int id;
	private String name;
	private int sellerId;
	private int ownerId;
	private float value;
	private float remainingValue;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public float getRemainingValue() {
		return remainingValue;
	}
	public float setRemainingValue(float remainingValue) {
		this.remainingValue = remainingValue;
		return remainingValue;
	}
	public Item(int id, String name, int sellerId, int ownerId, float value, float remainingValue) {
		super();
		this.id = id;
		this.name = name;
		this.sellerId = sellerId;
		this.ownerId = ownerId;
		this.value = value;
		this.remainingValue = remainingValue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ownerId;
		result = prime * result + Float.floatToIntBits(remainingValue);
		result = prime * result + sellerId;
		result = prime * result + Float.floatToIntBits(value);
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
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownerId != other.ownerId)
			return false;
		if (Float.floatToIntBits(remainingValue) != Float.floatToIntBits(other.remainingValue))
			return false;
		if (sellerId != other.sellerId)
			return false;
		if (Float.floatToIntBits(value) != Float.floatToIntBits(other.value))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", sellerId=" + sellerId + ", ownerId=" + ownerId + ", value="
				+ value + ", remainingValue=" + remainingValue + "]";
	}
	
	
	
	
}
