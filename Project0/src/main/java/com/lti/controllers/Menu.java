package com.lti.controllers;

import java.util.Scanner;

import com.lti.exceptions.LoginFail;
import com.lti.exceptions.SearchFailed;
import com.lti.models.Item;
import com.lti.models.User;
import com.lti.services.AuthService;
import com.lti.services.Shop;

public class Menu {

	public static void runDisplay() {

		while (true) {

			User user = null;
			Scanner scan = new Scanner(System.in);
			AuthService as = new AuthService();

			System.out.println("Welcome to Not Ebay! Please choose one of the following:");
			System.out.println("1: Register\n2: Login\n3: Browse\n4: Exit");

			String userInput;

			while (user == null) {
				userInput = scan.nextLine();
				if (userInput.equals("1")) {
					String pass1;
					String pass2;
					String username;
					while (true) {
						System.out.println("Please enter a username:");
						username = scan.nextLine();
						System.out.println("Please enter a password:");
						pass1 = scan.nextLine();
						System.out.println("Please verify password:");
						pass2 = scan.nextLine();
						if (pass1.equals(pass2)) {
							break;
						} else {
							System.out.println("Password do not match. Please try again.");
						}
					}
					try {
						user = as.register(username, pass1);
					} catch (LoginFail e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (userInput.equals("2")) {
					System.out.println("Please enter your username:");
					String username = scan.nextLine();
					System.out.println("Please enter your password:");
					String password = scan.nextLine();
					try {
						user = as.login(username, password);
					} catch (LoginFail e) {
						// TODO
						e.printStackTrace();
					}
				} else if (userInput.equals("3")) {
					break;
				} else if (userInput.equals("4")) {
					System.out.println("Have a nice day!");
					return;
				} else {
					System.out.println("Please enter a valid option");
				}
			}

			clearScreen();

			Shop shop = new Shop();

			while (true) {
				System.out.println("Please enter an item ID");
				dispTop();
				dispItems(shop);
				dispBottom();

				Item item;

				while (true) {
					userInput = scan.nextLine();
					try {
						item = shop.getItem(Integer.parseInt(userInput));
						break;
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SearchFailed e) {
						System.out.println("Please enter a valid item ID");
					}
				}

				clearScreen();
				dispTop();
				System.out.println(formatItem(item));

				if (user == null) {
					System.out.println("You need to be logged in to make an offer. Press enter to view item list.");
					userInput = scan.nextLine();
					continue;
				}

				System.out.println("Would you like to make an offer? y/n");

				while (true) {
					userInput = scan.nextLine();
					if (userInput.equals("y")) {
						shop.makeOffer(item.getId(), user.getId());
						break;
					} else if (userInput.equals("n")) {
						System.out.println("You will be returned to the item list");
						break;
					} else {
						System.out.println("Please enter y/n");
					}

				}

				System.out.println(
						"Type 'logout' to be returned to the main menu. Otherwise type 'items' to view the item list");
				userInput = scan.nextLine();
				if (userInput.equals("items")) {
					continue;
				} else if (userInput.equals("logout")) {
					break;
				} else {
					System.out.println("Please type a valid input");
				}

			}

			scan.close();
		}

	}

	private static void dispTop() {
		for (int i = 0; i < 66; i++) {
			System.out.print('-');
		}
		System.out.println("\n");
		String s = String.format("|%-10.10s|%-15.15s|%-15.15s|%-10.10s|%-10.10s|", "Item ID", "Name", "Seller", "Price",
				"Stock");
		System.out.println(s);
		for (int i = 0; i < 66; i++) {
			System.out.print('-');
		}
		System.out.println("\n");
	}

	private static String formatItem(Item item) {
		String itemLine = String.format("|%-10.10d|%-15.15s|%-15.15s|%-10.10d|%-10.10d|", item.getId(), item.getName(),
				item.getSeller(), item.getPrice(), item.getStock());
		;

		return itemLine;
	}

	private static void dispItems(Shop shop) {
		// get all items
		// foreach item format item
	}

	private static void refresh() {

	}

	private static void dispBottom() {
		for (int i = 0; i < 66; i++) {
			System.out.print('-');
		}
		System.out.println("\n");
	}

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
