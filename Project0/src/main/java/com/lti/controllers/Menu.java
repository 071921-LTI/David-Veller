package com.lti.controllers;

import java.util.Scanner;

import com.lti.exceptions.LoginFail;
import com.lti.models.User;
import com.lti.services.AuthService;

public class Menu {

	public static void runDisplay() {

		User user = null;
		Scanner scan = new Scanner(System.in);
		AuthService as = new AuthService();

		System.out.println("Welcome to Not Ebay! Please choose one of the following:");
		System.out.println("1: Register\n2: Login\n3: Browse\n4: Exit");

		String userInput;
		
		while (user.equals(null)) {
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

		
		dispTop();
		dispItems();
		System.out.println("Please enter an item number, or search item/search seller");
		userInput = scan.nextLine();
		
		
		
		
		scan.close();

	}

	private static void dispTop() {

	}

	private static void dispItems() {

	}

	private static void refresh() {

	}
}
