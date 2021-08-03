package com.lti.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class HashPassTest {
	
	HashPass hasher = HashPass.getHasher();
	
	@Test
	public void getHasher() {
		assertEquals(hasher, HashPass.getHasher());
	}
	
	@Test
	public void checkSamePass() {
		assertEquals(hasher.hashPass("hello"), hasher.hashPass("hello"));
	}
	
	@Test
	public void checkWrongPass() {
		assertNotEquals(hasher.hashPass("hello"), hasher.hashPass("helloworld"));
	}
	

}
