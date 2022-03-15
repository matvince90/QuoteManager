package com.monit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class TestSolution  {
	public static void main(String Args[]) throws ParseException  {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		QuoteManager qm = new QuoteManager();
		
		Quote q1 = new Quote(UUID.randomUUID(), "test1", 1.2, 1000, sdf.parse("3/13/2022"));
		Quote q2 = new Quote(UUID.randomUUID(), "test1", 1.0, 500, sdf.parse("3/13/2023"));
		Quote q3 = new Quote(UUID.randomUUID(), "test2", 0.8, 500, sdf.parse("3/13/2022"));
		Quote q4 = new Quote(UUID.randomUUID(), "test1", 0.8, 500, sdf.parse("3/13/2021"));
		Quote q5 = new Quote(UUID.randomUUID(), "test2", 800.0, 10, sdf.parse("3/13/2021"));

		qm.AddOrUpdateQuote(q1);
		qm.AddOrUpdateQuote(q2);
		qm.AddOrUpdateQuote(q3);
		qm.AddOrUpdateQuote(q4);
		qm.AddOrUpdateQuote(q5);
		System.out.println("----TEST 1 ----");
		System.out.println(qm.getAllAvailableQuotes("test1").toString());
		System.out.println("----TEST 2 ----");
		System.out.println(qm.getAllAvailableQuotes("test2"));
		
		q4.setExpiration(sdf.parse("3/13/2024"));
		qm.AddOrUpdateQuote(q1);
		System.out.println("----TEST 3 ----");
		System.out.println(qm.GetBestQuoteWithAvailableVolume("test1").toString());
		System.out.println("----TEST 4 ----");
		System.out.println(qm.ExecuteTrade("test1", 2000));
		System.out.println("----TEST 5 ----");
		System.out.println(qm.ExecuteTrade("test1", 1));
		System.out.println("----TEST 6 ----");
		System.out.println(qm.ExecuteTrade("test2", 1000));
	}
}