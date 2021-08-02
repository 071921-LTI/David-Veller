package com.lti.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPass {
	
	private static HashPass hasher;
	
	private HashPass() {}
	
	public static HashPass getHasher() {
		if (hasher == null) {
			hasher = new HashPass();
		}
		return hasher;
	}
	

	public String hashPass(String password) {
		byte[] byteData = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(password.getBytes());
			byteData = md.digest();
			StringBuffer sb = new StringBuffer();
		      for (int i = 0; i < byteData.length; i++)
		      {
		        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100,
		                                   16).substring(1));
		      }
		      return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
	
}
