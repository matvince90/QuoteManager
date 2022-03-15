package com.monit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class QuoteManager implements IQuoteManager {

	//contains a Hashset of quotes mapped by their Symbol. Allows for easy searches based on Symbol
	private HashMap<String, List<UUID>> quoteBook;
	//contains a quote mapped by its id. Allows for easy searches based on UUID
	private HashMap<UUID, Quote> quoteMap;
	
	public QuoteManager() {
		quoteBook = new HashMap<String, List<UUID>>();
		quoteMap = new HashMap<UUID, Quote>();
	}
	
	@Override
	public void AddOrUpdateQuote(Quote quote) {
		//Remove quote based on id
		RemoveQuote(quote.getId());
		
		//Add Quote to quoteMap
		quoteMap.put(quote.getId(), quote);
		
		// IF 		quoteBook doesn't contain symbol add, add symbol and make new quoteList containing quote and add to quoteBook
		if (!quoteBook.containsKey(quote.getSymbol())) {
			quoteBook.put(quote.getSymbol(), new ArrayList<UUID>());
		}
		//Ad Quote to quotebook
		quoteBook.get(quote.getSymbol()).add(quote.getId());

	}

	@Override
	public void RemoveQuote(UUID id) {
		//Remove quote from quoteList.
		Quote removedQuote = quoteMap.remove(id);
		
		//IF 	it existed in quoteMap, also remove from quotebook based on symbol of quote
		if (removedQuote != null) {
			quoteBook.get(removedQuote.getSymbol()).remove(removedQuote.getId());
		}
	}

	@Override
	public void RemoveAllQuotes(String symbol) {
		//Loop through quoteBook and remove all from quoteMap based on id
		for (UUID id : quoteBook.get(symbol)) {
			quoteMap.remove(id);
		}
		//remove symbol from quoteBook
		quoteBook.remove(symbol);
	}


	@Override
	public Quote GetBestQuoteWithAvailableVolume(String symbol) {
		List<Quote> availableQuoteList = getAllAvailableQuotes(symbol);
		if (availableQuoteList.size() > 0) {
			Collections.sort(availableQuoteList);
			return availableQuoteList.get(0);
		}
		return null;
	}

	@Override
	public TradeResult ExecuteTrade(String symbol, int volumeRequested) {
		TradeResult tr = new TradeResult();
		tr.setId(UUID.randomUUID());
		tr.setSymbol(symbol);
		tr.setVolumeRequested(volumeRequested);
		
		int remainingToBuy = volumeRequested;
		double totalSpent = 0;
		
		while (remainingToBuy > 0) {
			Quote q = GetBestQuoteWithAvailableVolume(symbol);
			if (q == null) {
				break;
			}
			// IF	less volume than remainingToBuy buy all : otherwise, buy remainingToBuy
			int totalBought = (remainingToBuy > q.getAvailableVolume() ? q.getAvailableVolume() : remainingToBuy);
			
			//Update values based on bought amount
			remainingToBuy -= totalBought;
			totalSpent += totalBought*q.getPrice();
			
			//Update the quote
			q.setAvailableVolume(q.getAvailableVolume() - totalBought);
			AddOrUpdateQuote(q);
		}
		tr.setVolumePurchased(volumeRequested-remainingToBuy);
		tr.setVolumeWeightedAveragePrice(totalSpent/(volumeRequested-remainingToBuy));
		return tr;
	}
	
	//used to determine availability of a quote. Allows us to easily change definition of availability in the future
	public boolean isAvailable(Quote quote) {
		return quote.getAvailableVolume() > 0 && quote.getExpiration().after(new Date());
	}
	
	//Returns a list of quotes that are available
	public List<Quote> getAllAvailableQuotes(String symbol) {
		List<Quote> resultList = new ArrayList<Quote>();
		//Loop through quoteBook based on symbol
		for (UUID id : quoteBook.get(symbol)) {
			Quote q = (Quote) quoteMap.get(id);
			// add to resultList if isAvailable
			if (isAvailable(q)) {
				resultList.add(q);
			}
		}
		return resultList;
	}

}
