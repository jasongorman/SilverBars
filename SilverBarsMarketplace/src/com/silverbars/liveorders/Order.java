package com.silverbars.liveorders;

public class Order {

	private final OrderType type;
	private final double quantity;
	private final double price;
	private final String userID;

	public Order(OrderType type, double price, double quantity, String userID) {
		this.type = type;
		this.quantity = quantity;
		this.price = price;
		this.userID = userID;
	}

	public OrderType getType() {
		return type;
	}

	public double getQuantity() {
		return quantity;
	}
	
	public double getPrice(){
		return price;
	}
	
	public String getUserID(){
		return userID;
	}

}
