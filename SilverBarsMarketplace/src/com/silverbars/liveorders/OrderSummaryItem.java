package com.silverbars.liveorders;

public class OrderSummaryItem {

	private final double quantity;
	private final double price;

	public OrderSummaryItem(double price, Double quantity) {
		this.price = price;
		this.quantity = quantity;
	}

	public double getTotalQuantity() {
		return quantity;
	}
	
	public double getPrice(){
		return price;
	}

}
