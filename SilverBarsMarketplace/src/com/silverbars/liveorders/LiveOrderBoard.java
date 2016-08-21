package com.silverbars.liveorders;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LiveOrderBoard {

	private final List<Order> orders = new ArrayList<>();

	public boolean contains(Order order) {
		return orders.contains(order) ;
	}

	public void registerOrder(Order order) {
		orders.add(order);
	}

	public void cancelOrder(Order order) {
		orders.remove(order);
	}

	public List<OrderSummaryItem> summariseOrdersByType(OrderType type){
		return groupOrdersByPrice(type)
				.entrySet()
				.stream()
				.map(e -> new OrderSummaryItem(e.getKey(), e.getValue()))
				.sorted(sortOrder(type))
				.collect(toList());
	}

	private Comparator<OrderSummaryItem> sortOrder(OrderType type) {
		Comparator<OrderSummaryItem> comparing = comparing(OrderSummaryItem::getPrice);
		if(type == OrderType.BUY)
			comparing = comparing.reversed();
		return comparing;
	}

	private Map<Double, Double> groupOrdersByPrice(OrderType type) {
		return filterByOrderType(type)
				.collect(Collectors.groupingBy(
						Order::getPrice,
						aggregateQuantityForPrice())); 
	}

	private Collector<Order, ?, Double> aggregateQuantityForPrice() {
		return Collectors.reducing(
				0.0, 
				Order::getQuantity, 
				Double::sum);
	}

	private Stream<Order> filterByOrderType(OrderType type) {
		return orders
				.stream()
				.filter(order -> order.getType() == type);
	}

}
