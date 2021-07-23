package com.lti.daos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.lti.exceptions.UserExistsException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;

public class UserFile implements UserDao{

	File userFile = new File("src/main/resources/user.txt");
	
	@Override
	public User getUser(String username) throws UserNotFoundException {
		
		System.out.println(userFile.exists());
		
		String currLine;
		
		try(BufferedReader reader = new BufferedReader(new FileReader(userFile))){
			do {
				currLine = reader.readLine();
				//System.out.println(currLine);
				String[] userFields = currLine.split(":");
				if (userFields[0].equals(username)) {
					User user = new User(userFields[0], userFields[1]);
					return user;
				}
			}while(currLine != null);
			
			}
		 catch (IOException e) {
			System.out.println("file not found");
		}
		
		
		throw new UserNotFoundException();
	}

	@Override
	public boolean addUser(User user) throws UserExistsException {
		return false;
	}

}
