package com.lti.controllers;

import java.util.Scanner;

import com.lti.exceptions.AuthException;
import com.lti.exceptions.UserExistsException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;
import com.lti.services.AuthService;
import com.lti.services.AuthServiceImpl;
import com.lti.services.UserService;
import com.lti.services.UserServiceImpl;

public class MenuScreen {

	static UserService us = new UserServiceImpl();
	static AuthService as = new AuthServiceImpl();
	
	public static void menu() {

		Scanner sc = new Scanner(System.in);
		String choice;
		String input;
		

		do {
			System.out.println("Welcome!");
			System.out.println("If you are a new user, please type 'reg' to register.");
			System.out.println("Else type 'log' to login");
			System.out.println("Type 'exit' to exit");

			choice = sc.nextLine();

			switch (choice) {
			case "reg":
				System.out.println("Please enter a username: ");
				input = sc.next();
				User userAdding = new User();
				userAdding.setUsername(input);
				
				System.out.println("Please enter a password: ");
				input = sc.next();
				userAdding.setPassword(input);
				
				try {
					us.addUser(userAdding);
				} catch(UserExistsException e){
					System.out.println("User already exists");
				}
				
				try {
					System.out.println(us.getUser(userAdding.getUsername()));
				} catch (UserNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sc.nextLine();
				break;
			case "log":
				
				System.out.println("Please enter a username: ");
				input = sc.next();
				User userLogging = new User();
				userLogging.setUsername(input);
				
				System.out.println("Please enter a password: ");
				input = sc.next();
				userLogging.setPassword(input);
				
				try {
					as.login(userLogging);
				} catch (AuthException e) {
					System.out.println("Wrong password");
				}
				
				try {
					System.out.println(us.getUser(userLogging.getUsername()));
				} catch (UserNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sc.nextLine();
				break;
			default:
				System.out.println("not an option");
				break;
			}

		} while (!choice.equals("exit"));

	}

}
