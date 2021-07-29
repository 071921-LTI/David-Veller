package com.lti.daos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.lti.models.Offer;

public interface OfferDao {
	
	public abstract List<Offer> getOffers(int itemId) throws IOException, SQLException;
	public abstract int newOffer(float offerAmount, int itemId, int customerId) throws IOException, SQLException;
	public abstract int deleteOffer(int offerId) throws IOException, SQLException;

}
