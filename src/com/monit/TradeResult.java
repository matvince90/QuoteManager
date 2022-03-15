package com.monit;
import java.util.UUID;

public class TradeResult implements ITradeResult {

	private UUID id;
	private String symbol;
	private double avgPrice;
	private int volumeRequested;
	private int volumePurchased;
	
	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public String getSymbol() {
		return this.symbol;
	}

	@Override
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public double getVolumeWeightedAveragePrice() {
		return this.avgPrice;
	}

	@Override
	public void setVolumeWeightedAveragePrice(double avgPrice) {
		this.avgPrice = avgPrice;
	}

	@Override
	public int getVolumeRequested() {
		return this.volumeRequested;
	}

	@Override
	public void setVolumeRequested(int volumeRequested) {
		this.volumeRequested = volumeRequested;
	}
	
	public int getVolumePurchased() {
		return this.volumePurchased;
	}

	public void setVolumePurchased(int volumePurchased) {
		this.volumePurchased = volumePurchased;
	}
	
	@Override
	public String toString() {
		String output = "TradeResult[id: " + id.toString()
							+ "\nsymbol: " + symbol
							+ "\navgPrice: " + avgPrice
							+ "\nvolumeRequested: " + volumeRequested
							+ "\nvolumePurchased: " + volumePurchased + "]";
		return output;
	}
}
