package com.silverbars.liveorders;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class LiveOrderBoardTests {

	private LiveOrderBoard orderBoard;

	@Before
	public void setUp() {
		orderBoard = new LiveOrderBoard();
	}

	@Test
	public void registeringOrderAddsItToOrderBoard() {
		Order order = mock(Order.class);
		registerOrder(order);
		assertTrue(orderBoard.contains(order));
	}

	@Test
	public void cancelingOrderRemovesItFromBoard() {
		Order order = mock(Order.class);
		registerOrder(order);
		orderBoard.cancelOrder(order);
		assertFalse(orderBoard.contains(order));
	}

	@Test
	@Parameters(method="orders")
	public void orderQuantitiesAreSummarisedByPrice(List<Order> orders, double totalQuantity) {
		for (Order order : orders) {
			registerOrder(order);
		}
		List<OrderSummaryItem> summaries = orderBoard.summariseOrdersByType(OrderType.BUY);
		assertThat(summaries.get(0).getTotalQuantity(), is(totalQuantity));
	}

	@Test
	@Parameters(method="orderTypeSortOrder")
	public void summariesAreSortedByOrderType(OrderType type, 
			double firstPrice, 
			double secondPrice) {
		registerOrder(createOrder(type, 100, 1.0));
		registerOrder(createOrder(type, 200, 1.0));
		List<OrderSummaryItem> summaries = orderBoard.summariseOrdersByType(type);
		assertEquals(firstPrice, summaries.get(0).getPrice(), 0);
		assertEquals(secondPrice, summaries.get(1).getPrice(), 0);
	}	

	private Object[] orders(){
		OrderType buy = OrderType.BUY;
		return new Object[][]{
			{Arrays.asList(
					createOrder(buy, 300, 1.0)),
				1.0},
			{Arrays.asList(
					createOrder(buy, 300, 1.0),
					createOrder(buy, 300, 1.0)),
					2.0}
		};
	}

	private Object[] orderTypeSortOrder(){
		return new Object[][]{
			{OrderType.BUY, 200, 100},
			{OrderType.SELL, 100, 200}
		};
	}

	private Order createOrder(OrderType type, double price, double quantity) {
		return new Order(type, price, quantity, null);
	}	

	private void registerOrder(Order order) {
		orderBoard.registerOrder(order);
	}

}
