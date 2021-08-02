package com.lti.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.lti.exceptions.NoOffersException;
import com.lti.exceptions.NotYourItemException;
import com.lti.exceptions.PaymentException;
import com.lti.models.Item;
import com.lti.models.Offer;
import com.lti.models.User;

public interface Shoppable {
	
	public abstract int addItem(Item item) throws IOException, SQLException;
	public abstract int deleteItem(Item item) throws IOException, SQLException;
	public abstract List<Item> viewAllItems() throws IOException, SQLException;
	public abstract List<Item> viewOwnedItems(User user) throws IOException, SQLException;
	public abstract List<Item> viewSoldItems(User user) throws IOException, SQLException;
	public abstract int updateOwner(Item item, User user) throws IOException, SQLException;
	public abstract int updateValue(Item item, float value) throws IOException, SQLException;
	public abstract int updateRemainingValue(Item item, float remainingValue) throws IOException, SQLException;
	public abstract Item getItem(Item item) throws IOException, SQLException;
	public abstract float calcWeeklyPayment(int weeks, Item item) throws PaymentException, IOException, SQLException, NotYourItemException;
	public abstract int makeOffer(Item item, User user, float offerAmount) throws IOException, SQLException;
	public abstract int acceptOffer(Offer offer) throws IOException, SQLException;
	public abstract int rejectOffer(Offer offer) throws IOException, SQLException;
	public abstract int makePayment(User user, Item item, float amount) throws NotYourItemException, IOException, SQLException;
	public abstract List<Offer> getOffers(int itemId) throws IOException, SQLException, NoOffersException;
	public abstract boolean isItemOwned(int userId, int itemId) throws IOException, SQLException;

}
