package com.lti.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.lti.daos.ItemDao;
import com.lti.daos.ItemPostgres;
import com.lti.daos.OfferDao;
import com.lti.daos.OfferPostgres;
import com.lti.exceptions.NoOffersException;
import com.lti.exceptions.NotYourItemException;
import com.lti.exceptions.PaymentException;
import com.lti.models.Item;
import com.lti.models.Offer;
import com.lti.models.User;

public class Shop implements Shoppable {
	
	ItemDao id = new ItemPostgres();
	OfferDao od = new OfferPostgres();

	@Override
	public int addItem(Item item) throws IOException, SQLException {
		
		return id.addItem(item);
	}

	@Override
	public int deleteItem(Item item) throws IOException, SQLException {
		
		return id.deleteItem(item.getId());
	}

	@Override
	public List<Item> viewAllItems() throws IOException, SQLException {
		
		return id.viewAllItems();
	}

	@Override
	public List<Item> viewOwnedItems(User user) throws IOException, SQLException {
		
		return id.viewOwnedItems(user.getId());
	}

	@Override
	public int updateOwner(Item item, User user) throws IOException, SQLException {
		
		return id.updateOwner(item.getId(), user.getId());
	}

	@Override
	public int updateValue(Item item, float value) throws IOException, SQLException {
		
		return id.updateValue(item.getId(), value);
	}

	@Override
	public int updateRemainingValue(Item item, float remainingValue) throws IOException, SQLException {
		
		return id.updateRemainingValue(item.getId(), remainingValue);
	}

	@Override
	public Item getItem(Item item) throws IOException, SQLException {
		
		return id.getItemById(item.getId());
	}

	@Override
	public float calcWeeklyPayment(int weeks, Item item) throws PaymentException, IOException, SQLException {
		
		item = id.getItemById(item.getId());
		
		if (weeks <= 0) {
			throw new PaymentException();
		} else {
			return item.getRemainingValue() / weeks;
		}
	}

	@Override
	public int makeOffer(Item item, User user, float offerAmount) throws IOException, SQLException {
		
		
		return od.newOffer(offerAmount, item.getId(), user.getId());
	}

	@Override
	public int acceptOffer(Offer offer) throws IOException, SQLException {
		
		
		
		//can maybe make this a transaction?
		//is this efficient?
		//dao.begin
		offer = od.getOffer(offer.getId());
		
		int offersDeleted = 0;
		
		id.updateOwner(offer.getItemId(), offer.getCustomerId());
		id.updateValue(offer.getItemId(), offer.getOfferAmount());
		id.updateRemainingValue(offer.getItemId(), offer.getOfferAmount());
		List<Offer> otherOffers = od.getOffers(offer.getItemId());
		for (Offer o : otherOffers) {
			od.deleteOffer(o.getId());
			offersDeleted++;
		}
		//dao.exit
		
		return offersDeleted;
	}

	@Override
	public int rejectOffer(Offer offer) throws IOException, SQLException {
		return od.deleteOffer(offer.getId());
	}

	@Override
	public int makePayment(User user, Item item, float amount) throws NotYourItemException, IOException, SQLException{
		
		
		if (item.getOwnerId() != user.getId()) {
			throw new NotYourItemException();
		}else if(item.getRemainingValue() < amount) {
			System.out.println("Thanks for the free money");
			item.setRemainingValue(0);
			id.updateRemainingValue(item.getId(), 0);
		}else {
			id.updateRemainingValue(item.getId(), item.setRemainingValue(item.getRemainingValue()-amount));
		}
		return 0;
	}

	@Override
	public List<Item> viewSoldItems(User user) throws IOException, SQLException {
		
		return id.viewSoldItems(user.getId());
	}

	@Override
	public List<Offer> getOffers(int itemId) throws IOException, SQLException, NoOffersException {
		
		List<Offer> offers = od.getOffers(itemId);
		if (offers.isEmpty()) {
			throw new NoOffersException();
		}

		
		return offers;
	}

	@Override
	public boolean isItemOwned(int userId, int itemId) throws IOException, SQLException {
		
		
		if (id.getItemById(itemId) == null) {
			return false;
		}
		
		return id.getItemById(itemId).getOwnerId() == userId;
	}

}
