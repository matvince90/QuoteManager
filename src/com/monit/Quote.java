package com.monit;

import java.util.Date;
import java.util.UUID;

public class Quote implements IQuote, Comparable<Quote>{

	private UUID id;
	private String symbol;
	private double price;
	private int availableVolume;
	private Date expiration;
	
	public Quote(UUID id, String symbol, double price, int availableVolume, Date expiration) {
		setId(id);
		setSymbol(symbol);
		setPrice(price);
		setAvailableVolume(availableVolume);
		setExpiration(expiration);
	}
	
	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String getSymbol() {
		return this.symbol;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public int getAvailableVolume() {
		return this.availableVolume;
	}

	@Override
	public void setAvailableVolume(int availableVolume) {
		this.availableVolume = availableVolume;
	}

	@Override
	public Date getExpiration() {
		return expiration;
	}

	@Override
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	@Override
	public int compareTo(Quote o) {
		return Double.compare(this.getPrice(), o.getPrice()); 
	}
	
	@Override
	public String toString() {
		String output = "Quote[id: " + id.toString()
							+ "\nsymbol: " + symbol
							+ "\nprice: " + price
							+ "\navailableVolume: " + availableVolume
							+ "\nexpiration: " + expiration + "]";
		return output;
	}
}