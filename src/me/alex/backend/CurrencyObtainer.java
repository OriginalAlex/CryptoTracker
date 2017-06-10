package me.alex.backend;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CurrencyObtainer {
	
	private String url;
	
	public CurrencyObtainer() {
		this.url = "https://coinmarketcap.com/currencies/bitcoin/";
	}
	
	public CurrencyObtainer(String URL) {
		this.url = URL;
	}
	
	public double fetchPrice() {
		double val = 0;
		try {
			Document doc = Jsoup.connect(this.url).get();
			Elements price = doc.select("span#quote_price");
			for (Element e : price) {
				try {
					val = Double.parseDouble(e.text().replace("$", ""));
				} catch (NumberFormatException nfe) {
					System.out.println("Number format exception!");
				}
			}
		} catch (IOException e) {
			System.out.println("IO ERROR");
			e.printStackTrace();
		}
		return val;
	}
	
	public void setURL(String newURL) {
		this.url = newURL;
	}

}
