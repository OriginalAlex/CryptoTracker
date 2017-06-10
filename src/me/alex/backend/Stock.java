package me.alex.backend;

public class Stock {
	
	private String name;
	private double amount;
	private double value;
	private double btcValue;
	
	public Stock(String name, double amount, double value, double btcValue) {
		this.name = name;
		this.amount = amount;
		this.value = value;
		this.btcValue = Math.round((value / btcValue) * 100.0) / 100.0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public double getBtcValue() {
		return this.btcValue;
	}

}
