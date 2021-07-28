package com.lti.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.lti.exceptions.AuthException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;
import com.lti.services.UserServiceImpl;

public class Menu {

	public static void runMenu() {

		Scanner scan = new Scanner(System.in);

		User user = null;

		user = loginMenu(scan, user);

		System.out.println(user);

	}

	private static User loginMenu(Scanner scan, User user) {
		
		String userInput;
		UserServiceImpl us = new UserServiceImpl();
		
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
