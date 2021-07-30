package com.lti.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.lti.exceptions.AuthException;
import com.lti.exceptions.NoOffersException;
import com.lti.exceptions.PaymentException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.Item;
import com.lti.models.Offer;
import com.lti.models.User;
import com.lti.services.Shop;
import com.lti.services.UserService;
import com.lti.services.UserServiceImpl;

public class Menu {

	public static void runMenu() {

		Scanner scan = new Scanner(System.in);

		User user = null;

		user = loginMenu(scan, user);
		if (user == null) {
			return;
		}

		System.out.println(user);

		if (user.getRole().equals("customer")) {
			customerMenu(scan, user);
		} else if (user.getRole().equals("employee")) {
			employeeMenu(scan, user);
		}

	}

	private static void employeeMenu(Scanner scan, User user) {
		String userInput;
		Shop shop = new Shop();
		UserService us = new UserServiceImpl();

		while (true) {

			displayOwnedItems(user, shop, us);
			System.out.println(
					"Enter 'add' to add an item\n'delete' to delete an item\n'payments' to view payments\n'offers' to view offers\n'exit' to exit");
			userInput = scan.nextLine();

			if (userInput.equals("add")) {

				try {
					System.out.println("Please enter an item name: ");
					String name = scan.nextLine();
					System.out.println("Please enter a value for your item: ");
					float value = Float.parseFloat(scan.nextLine());

					Item item = new Item(0, name, user.getId(), user.getId(), value, value);
					item.setId(shop.addItem(item));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Your item has been added!");

			} else if (userInput.equals("delete")) {
				System.out.println("Please enter the item id that you would like to delete");
				int id;
				while (true) {
					try {
						id = Integer.parseInt(scan.nextLine());
						break;
					} catch (NumberFormatException e) {
						System.out.println("Please enter a number");
					}
				}
				try {
					shop.deleteItem(new Item(id));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Your item has been deleted!");

			} else if (userInput.equals("payments")) {

				dispTop();
				try {
					List<Item> items = shop.viewSoldItems(user);
					for (Item i : items) {
						formatItem(i, shop, us);
					}
				} catch (IOException e) {
					System.out.println("Properties file not found");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dispBottom();
				System.out.println("Press enter to go back to menu");
				scan.nextLine();

			} else if (userInput.equals("offers")) {

				System.out.println("Please enter an item id");
				int id;
				while (true) {
					try {
						id = Integer.parseInt(scan.nextLine());
						break;
					} catch (NumberFormatException e) {
						System.out.println("Please enter a number");
					}
				}
				for (int i = 0; i < 55; i++) {
					System.out.print('-');
				}

				System.out.print("\n");
				try {
					List<Offer> offers = shop.getOffers(id);
					List<Integer> offerIds = new ArrayList<>();
					String s = String.format("|%-10.10s|%-15.15s|%-10.10s|%-15.15s|", "Offer ID", "Item", "Amount",
							"Customer");
					System.out.println(s);
					for (int i = 0; i < 55; i++) {
						System.out.print('-');
					}
					System.out.print("\n");
					for (Offer o : offers) {
						String off = String.format("|%-10d|%-15.15s|%-10f|%-15.15s|", o.getId(),
								shop.getItem(new Item(o.getItemId())).getName(), o.getOfferAmount(),
								us.getUsernameById(o.getCustomerId()));
						System.out.println(off);
						offerIds.add(o.getId());
					}
				
					System.out.println("Please enter an offer id that you would like to accept/reject");
					int offerId;
					while (true) {
						try {
							offerId = Integer.parseInt(scan.nextLine());
							break;
						} catch (NumberFormatException e) {
							System.out.println("Please enter a number");
						}
					}
					if(!offerIds.contains(offerId)) {
						System.out.println("That offer does not exist");
						break;
					}
				
				
					System.out.println("Type 'accept' to accept the offer or 'reject' to reject the offer");
					while (true) {
						userInput = scan.nextLine();
						if (userInput.equals("accept")) {
							shop.acceptOffer(new Offer(offerId));
							System.out.println("The offer was accepted!");
							break;
						} else if (userInput.equals("reject")) {
							shop.rejectOffer(new Offer(offerId));
							System.out.println("Offer has been rejected!");
							break;
						} else {
							System.out.println("Please enter a valid input");
						}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AuthException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoOffersException e1) {
					System.out.println("This item has no offers\nPress enter to continue");
					scan.nextLine();

				}

			} else if (userInput.equals("exit")) {
				break;
			}

		}

	}

	private static void customerMenu(Scanner scan, User user) {
		String userInput;
		Shop shop = new Shop();
		UserService us = new UserServiceImpl();

		while (true) {
			displayAllItems(shop, us);

			System.out.println("Please enter 'offer' to choose an item\n'owned' to view owned items\n'exit' to exit");

			userInput = scan.nextLine();

			if (userInput.equals("offer")) {
				while (true) {
					System.out.println("Please enter an item ID");
					int itemId;
					while (true) {
						try {
							itemId = Integer.parseInt(scan.nextLine());
							break;
						} catch (NumberFormatException e) {
							System.out.println("Please enter a number");
						}
					}

					System.out.println("Please enter and offer amount");
					int offerAmount;
					while (true) {
						try {
							offerAmount = Integer.parseInt(scan.nextLine());
							break;
						} catch (NumberFormatException e) {
							System.out.println("Please enter a number");
						}
					}
					try {
						shop.makeOffer(new Item(itemId), user, offerAmount);
						break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						System.out.println("Item was not found");
					}
				}
				System.out.println("You have made an offer!");

			} else if (userInput.equals("owned")) {

				displayOwnedItems(user, shop, us);
				System.out.println("Enter the item id for which to view remaining payments");
				int itemId;
				while (true) {
					try {
						itemId = Integer.parseInt(scan.nextLine());
						break;
					} catch (NumberFormatException e) {
						System.out.println("Please enter a number");
					}
				}
				System.out.println("Please enter the number of weeks you would like to take to repay the item");
				int weeks;
				while (true) {
					while (true) {
						try {
							weeks = Integer.parseInt(scan.nextLine());
							break;
						} catch (NumberFormatException e) {
							System.out.println("Please enter a number");
						}
					}
					try {
						Float weeklyAmount = shop.calcWeeklyPayment(weeks, new Item(itemId));
						if (!shop.isItemOwned(user.getId(), itemId)) {
							System.out.println("You do not own this item");
							break;
						}
						System.out.println("Your weekly payment is " + weeklyAmount.toString());
						break;
					} catch (PaymentException e) {
						System.out.println("Please enter a valid number of weeks");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else if (userInput.equals("exit")) {
				System.out.println("Have a nice day!");
				return;
			} else {
				System.out.println("Please enter a valid input");
			}
		}

	}

	private static void displayAllItems(Shop shop, UserService us) {
		dispTop();
		try {
			List<Item> items = shop.viewAllItems();
			for (Item i : items) {
				formatItem(i, shop, us);
			}
		} catch (IOException e) {
			System.out.println("Properties file not found");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispBottom();
	}

	private static void displayOwnedItems(User user, Shop shop, UserService us) {
		dispTop();
		try {
			List<Item> items = shop.viewOwnedItems(user);
			for (Item i : items) {
				formatItem(i, shop, us);
			}
		} catch (IOException e) {
			System.out.println("Properties file not found");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispBottom();
	}

	private static void dispTop() {
		for (int i = 0; i < 82; i++) {
			System.out.print('_');
		}
		System.out.println("\n");
		String s = String.format("|%-10.10s|%-15.15s|%-15.15s|%-15.15s|%-10.10s|%-10.10s|", "Item ID", "Name", "Seller",
				"Owner", "Value", "Remaining");
		System.out.println(s);
		for (int i = 0; i < 82; i++) {
			System.out.print('-');
		}
		System.out.println("\n");
	}

	private static String formatItem(Item item, Shop shop, UserService us) {
		String s = null;
		try {
			s = String.format("|%-10d|%-15.15s|%-15.15s|%-15.15s|%-10f|%-10f|", item.getId(), item.getName(),
					us.getUsernameById(item.getSellerId()), us.getUsernameById(item.getOwnerId()), item.getValue(),
					item.getRemainingValue());
			System.out.println(s);
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return s;
	}

	private static void dispBottom() {
		for (int i = 0; i < 82; i++) {
			System.out.print('-');
		}
		System.out.println("\n");
	}

	private static User loginMenu(Scanner scan, User user) {

		String userInput;
		UserService us = new UserServiceImpl();

		while (true) {
			System.out.println("Welcome to Not Ebay!");
			System.out.println("Please type 'login', 'register', or 'exit'");
			userInput = scan.nextLine();

			if (userInput.equals("login")) {
				System.out.println("Please enter your username: ");
				String username = scan.nextLine();
				System.out.println("Please enter your password: ");
				String password = scan.nextLine();
				try {
					user = us.login(username, password);
				} catch (IOException e) {
					System.out.println("Properties file was not found");
					continue;
				} catch (SQLException e) {
					System.out.println("Username was not found");
					continue;
				} catch (AuthException e) {
					System.out.println("Wrong password");
					continue;
				} catch (UserNotFoundException e) {
					System.out.println("Username was not found");
					continue;
				}

				System.out.println("Successfully logged in!");
				return user;

			} else if (userInput.equals("register")) {
				System.out.println("Please enter your username: ");
				String username = scan.nextLine();
				System.out.println("Please enter your password: ");
				String password = scan.nextLine();
				try {
					user = us.registerCustomer(username, password);
				} catch (IOException e) {
					System.out.println("Properties file was not found");
					continue;
				} catch (SQLException e) {
					System.out.println("Username already exists");
					continue;
				} catch (AuthException e) {
					System.out.println("Password too short (6 chars min)");
					continue;
				}

				System.out.println("Succesfully registered!");
				return user;
			} else if (userInput.equals("exit")) {
				System.out.println("Have a nice day!");
				break;

			} else {
				System.out.println("Please enter a valid option");
			}
		}
		return null;
	}

}
