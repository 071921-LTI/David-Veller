package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.revature.exceptions.CalculatorException;

//@TestMethodOrder(OrderAnnotation.class)
public class CalculatorTest {

	/*
	 * JUnit annotations
	 * 	- @BeforeEach
	 * 	- @AfterEach
	 * 	- @BeforeAll
	 * 	- @AfterAll
	 * 	- @Test
	 * 	- @Ignore
	 *  - @Order
	 */
	
	private static Calculator calc;
	
	
	@BeforeAll
	public static void setUp() {
		calc = new Calculator();
	}
	
	@AfterAll
	public static void tearDown() {
	}
	
	@Test
	public void addTwoAndTwo() {
		double expected = 4;
		double actualResult = calc.add(2, 2);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void addNegative() {
		double expected = -4;
		double actualResult = calc.add(-2, -2);
		assertEquals(expected, actualResult, 0.001);
	}

	
	@Test
	public void addDecimal() {
		double expected = 4.7;
		double actualResult = calc.add(2.5, 2.2);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void add1Negative() {
		double expected = 0;
		double actualResult = calc.add(-2, 2);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void add1Decimal() {
		double expected = 4.5;
		double actualResult = calc.add(2.5, 2);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void subPos() {
		double expected = -3;
		double actualResult = calc.subtract(2, 5);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void subNeg() {
		double expected = 3;
		double actualResult = calc.subtract(-2, -5);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void subDec() {
		double expected = .5;
		double actualResult = calc.subtract(2.5, 2);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void sub1Neg() {
		double expected = -7;
		double actualResult = calc.subtract(-2, 5);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void sub1Dec() {
		double expected = -0.3;
		double actualResult = calc.subtract(2.2, 2.5);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void multplyPos() {
		double expected = 10;
		double actualResult = calc.multiply(2, 5);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void multplyNeg() {
		double expected = 10;
		double actualResult = calc.multiply(-2, -5);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void multplyDec() {
		double expected = 5;
		double actualResult = calc.multiply(2.5, 2);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void multply1Neg() {
		double expected = -10;
		double actualResult = calc.multiply(-2, 5);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void multply1Dec() {
		double expected = 5.5;
		double actualResult = calc.multiply(2.2, 2.5);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void divPos() {
		double expected = 10;
		double actualResult = calc.divide(20, 2);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void divNeg() {
		double expected = 10;
		double actualResult = calc.divide(-20, -2);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void divDec() {
		double expected = 1;
		double actualResult = calc.divide(2.5, 2.5);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void div1Neg() {
		double expected = -10;
		double actualResult = calc.divide(20,-2);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void div1Dec() {
		double expected = 2;
		double actualResult = calc.divide(5, 2.5);
		assertEquals(expected, actualResult, 0.001);
	}
	
	@Test
	public void divideBy0() {
		assertThrows(CalculatorException.class, () -> calc.divide(1,0));
	}
	
	@Test
	public void isPrime2() {
		boolean expected = true;
		boolean actualResult = calc.isPrime(2);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void isPrime10() {
		boolean expected = false;
		boolean actualResult = calc.isPrime(10);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void isPrime3() {
		boolean expected = true;
		boolean actualResult = calc.isPrime(3);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void isPrime15() {
		boolean expected = false;
		boolean actualResult = calc.isPrime(15);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void isPrime53() {
		boolean expected = true;
		boolean actualResult = calc.isPrime(53);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void compareThreeDecimalwhole() {
		boolean expected = true;
		boolean actualResult = calc.compareThreeDecimal(53, 53);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void compareThreeDecimal3() {
		boolean expected = true;
		boolean actualResult = calc.compareThreeDecimal(1.234, 1.234);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void compareThreeDecimal5() {
		boolean expected = true;
		boolean actualResult = calc.compareThreeDecimal(1.23467, 1.23446);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void compareThreeDecimal1() {
		boolean expected = true;
		boolean actualResult = calc.compareThreeDecimal(2.3, 2.3);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void compareThreeDecimalbad() {
		boolean expected = false;
		boolean actualResult = calc.compareThreeDecimal(2.345, 2.344);
		assertEquals(expected, actualResult);
	}
	
	
}
