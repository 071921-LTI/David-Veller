package com.lti.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lti.models.Offer;
import com.lti.util.ConnectionUtil;

public class OfferPostgres implements OfferDao{

	@Override
	public List<Offer> getOffers(int itemId) throws IOException, SQLException {
		List<Offer> offers = new ArrayList<>();
		String sql = "select * from offers where offer_on = ? and offer_id is not null;";
		
		Connection con = ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setInt(1, itemId);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			int offerId = rs.getInt("offer_id");
			float offerAmount = rs.getFloat("offer_amount");
			int customerId = rs.getInt("offer_from");
			
			Offer offer = new Offer(offerId, offerAmount, itemId, customerId);
			offers.add(offer);
		}
		
		return offers;
	}

	@Override
	public int newOffer(float offerAmount, int itemId, int customerId) throws IOException, SQLException{
		int offerId = -1;
		String sql = "insert into offers (offer_amount, offer_on, offer_from) values (?, ?, ?) returning offer_id;";
		
		Connection con = ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setFloat(1, offerAmount);
		ps.setInt(2,  itemId);
		ps.setInt(3, customerId);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			offerId = rs.getInt("offer_id");
		}
		
		return offerId;
	}

	@Override
	public int deleteOffer(int offerId) throws IOException, SQLException{
		int rowsChanged  = -1;
		String sql = "delete from offers where offer_id = ?;";
		
		Connection con = ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setInt(1,  offerId);
		
		rowsChanged = ps.executeUpdate();
		
		return rowsChanged;
	}

	@Override
	public Offer getOffer(int offerId) throws IOException, SQLException {
		Offer offer = null;
		String sql = "select * from offers where offer_id = ?;";
		
		Connection con = ConnectionUtil.getConnectionFromFile();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setInt(1, offerId);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			int itemId = rs.getInt("offer_on");
			float offerAmount = rs.getFloat("offer_amount");
			int customerId = rs.getInt("offer_from");
			
			offer = new Offer(offerId, offerAmount, itemId, customerId);
		}
		
		return offer;
	}

}
