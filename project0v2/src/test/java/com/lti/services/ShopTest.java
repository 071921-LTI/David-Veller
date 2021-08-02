package com.lti.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lti.daos.ItemDao;
import com.lti.daos.OfferDao;
import com.lti.exceptions.NoOffersException;
import com.lti.exceptions.NotYourItemException;
import com.lti.exceptions.PaymentException;
import com.lti.models.Item;
import com.lti.models.Offer;
import com.lti.models.User;

@ExtendWith(MockitoExtension.class)
public class ShopTest {
	
	@Mock
	private ItemDao id;
	
	@Mock
	private OfferDao od;
	
	@InjectMocks
	private Shop shop;
	
	Item item = new Item(1, "thing", 1, 1, 10, 10);
	User user = new User(1, "david", "password", "customer");
	Offer offer = new Offer(1, 20, 1, 1);
	
	@Test
	public void addItem() {
		try {
			Mockito.when(id.addItem(item)).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.addItem(item), 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteItemExists() {
		try {
			Mockito.when(id.deleteItem(1)).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.deleteItem(item), 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteItemNotExists() {
		try {
			Mockito.when(id.deleteItem(1)).thenReturn(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.deleteItem(item), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	public void viewAllItems() {
		List<Item> expected = new ArrayList<>();
		expected.add(item);
		try {
			Mockito.when(id.viewAllItems()).thenReturn(expected);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(expected.size(), shop.viewAllItems().size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	public void viewOwnedItems() {
		List<Item> expected = new ArrayList<>();
		expected.add(item);
		try {
			Mockito.when(id.viewOwnedItems(1)).thenReturn(expected);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(expected.size(), shop.viewOwnedItems(new User(1, "david", "password", "customer")).size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	public void viewSoldItems() {
		List<Item> expected = new ArrayList<>();
		expected.add(item);
		try {
			Mockito.when(id.viewSoldItems(1)).thenReturn(expected);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(expected.size(), shop.viewSoldItems(user).size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateOwnerExists() {
		try {
			Mockito.when(id.updateOwner(1, 1)).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.updateOwner(item, user), 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateOwnerNotExists() {
		try {
			Mockito.when(id.updateOwner(1, 1)).thenReturn(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.updateOwner(item, user), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateValueExists() {
		try {
			Mockito.when(id.updateValue(1, 10)).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.updateValue(item, 10), 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateValueNotExists() {
		try {
			Mockito.when(id.updateValue(1, 10)).thenReturn(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.updateValue(item, 10), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void updateRemainingValueExists() {
		try {
			Mockito.when(id.updateRemainingValue(1, 10)).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.updateRemainingValue(item, 10), 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateRemainingValueNotExists() {
		try {
			Mockito.when(id.updateRemainingValue(1, 10)).thenReturn(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.updateRemainingValue(item, 10), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getItemExists() {
		try {
			Mockito.when(id.getItemById(1)).thenReturn(item);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(item, shop.getItem(item));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getItemNotExists() {
		try {
			Mockito.when(id.getItemById(1)).thenReturn(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(null, shop.getItem(item));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void calcWeekly() {
		try {
			Mockito.when(id.getItemById(1)).thenReturn(item);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.calcWeeklyPayment(2, item), 5);
		} catch (PaymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void calcWeeklyNoWeeks() {
		assertThrows(PaymentException.class, () -> shop.calcWeeklyPayment(-1, item));
	}
	
	@Test
	public void makeOffer() {
		try {
			Mockito.when(od.newOffer(20, 1, 1)).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.makeOffer(item, user, 20), 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void makeOfferNoItem() {
		try {
			Mockito.when(od.newOffer(20, 1, 1)).thenThrow(SQLException.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThrows(SQLException.class, () -> shop.makeOffer(item, user, 20));
	}
	
	@Test
	public void acceptOffer() {
		int expected = 9;
		List<Offer> offers = new ArrayList<>();
		offers.add(offer);
		for (int i = 2;i<10;i++) {
			offers.add(new Offer(i, 20, 1, i));
		}
		try {
			Mockito.when(od.getOffer(1)).thenReturn(offer);
			Mockito.when(id.updateOwner(1, 1)).thenReturn(1);
			Mockito.when(id.updateValue(1, offer.getOfferAmount())).thenReturn(1);
			Mockito.when(id.updateRemainingValue(1, offer.getOfferAmount())).thenReturn(1);
			Mockito.when(od.getOffers(1)).thenReturn(offers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(expected, shop.acceptOffer(offer));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void rejectOfferExist() {
		try {
			Mockito.when(od.deleteOffer(1)).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(1, shop.rejectOffer(offer));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void rejectOfferNotExist() {
		try {
			Mockito.when(od.deleteOffer(1)).thenReturn(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(0, shop.rejectOffer(offer));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//not good test
	@Test
	public void makeYourPayment() {
		try {
			Mockito.when(id.updateRemainingValue(1, 5)).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.makePayment(user, item, 5), 0);
		} catch (NotYourItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void makeNotYourPayment() {
		assertThrows(NotYourItemException.class, () -> shop.makePayment(user, new Item(1, "thing", 1, 2, 10, 10), 3));
	}
	
	@Test
	public void makeTooMuchPayment() {
		try {
			Mockito.when(id.updateRemainingValue(1, 0)).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(shop.makePayment(user, item, 20), 0);
		} catch (NotYourItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getOffers() {
		List<Offer> offers = new ArrayList<>();
		offers.add(offer);
		try {
			Mockito.when(od.getOffers(1)).thenReturn(offers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			assertEquals(shop.getOffers(1), offers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoOffersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getNoOffers() {
		List<Offer> offers = new ArrayList<>();
		try {
			Mockito.when(od.getOffers(1)).thenReturn(offers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThrows(NoOffersException.class, () -> shop.getOffers(1));
	}
	
	@Test
	public void isOwned() {
		try {
			Mockito.when(id.getItemById(1)).thenReturn(item);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(true, shop.isItemOwned(1, 1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void isNotOwnedNotFound() {
		try {
			Mockito.when(id.getItemById(1)).thenReturn(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(false, shop.isItemOwned(1, 1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void isNotOwned() {
		try {
			Mockito.when(id.getItemById(1)).thenReturn(item);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(false, shop.isItemOwned(2, 1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}


