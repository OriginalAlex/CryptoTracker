package me.alex.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class NameURL {
	
	private Map<String, String> nameURL;
	
	public NameURL() {
		this.nameURL = new HashMap<String, String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(NameURL.class.getResourceAsStream("data")));
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(" ");
				if (split.length != 2) {
					throw new RuntimeException("Error in file!");
				}
				nameURL.put(split[0].replace("_", " "), split[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getNameURLPair() {
		return this.nameURL;
	}

}
