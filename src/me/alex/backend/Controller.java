package me.alex.backend;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class Controller {
	
	@FXML
	private ComboBox<String> currency;
	
	@FXML
	private TableView<Stock> list;
	
	@FXML
	private TableColumn<Stock, String> currencyColumn;
	
	@FXML
	private TableColumn<Stock, Double> amountColumn;
	
	@FXML
	private TableColumn<Stock, Double> valueColumn;
	
	@FXML
	private TableColumn<Stock, Double> bitcoinValueColumn;
	
	@FXML
	private TextField amount;
	
	@FXML
	private TextField other;
	
	@FXML
	private Text status;
	
	@FXML
	private Text totalVal;
	
	private Map<String, String> nameURLPair;
	private CurrencyObtainer co;
	private ObservableList<Stock> data;
	private double total;
	private double bitcoinValue;
	private ContextMenu menu;
	
	@FXML
	public void initialize() {
		
		currencyColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		bitcoinValueColumn.setCellValueFactory(new PropertyValueFactory<>("btcValue"));
		
		this.total = 0;
		this.menu = new ContextMenu();
		this.data = FXCollections.observableArrayList();
		this.co = new CurrencyObtainer();
		this.bitcoinValue = this.co.fetchPrice();
		
		createMenu();
		NameURL nu = new NameURL();
		Map<String, String> nameURLPair = nu.getNameURLPair();
		this.nameURLPair = nameURLPair;
		SortedSet<String> currencies = new TreeSet<String>(nameURLPair.keySet());
		
		currency.getItems().add("Other...");
		currency.getItems().addAll(currencies);
	}
	
	private void createMenu() {
		MenuItem item = new MenuItem("Go to website");
		item.setOnAction((ActionEvent event) -> {
			Stock selected = list.getSelectionModel().getSelectedItem();
			String url;
			if (!nameURLPair.containsKey(selected.getName())) {
				url = createURL(selected.getName());
			} else {
				url = nameURLPair.get(selected.getName());
			}
			try {
				Desktop.getDesktop().browse(URI.create(url));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		menu.getItems().add(item);
		list.setContextMenu(menu);
	}
	
	@FXML
	public void addAmount() {
		String curr = currency.getSelectionModel().getSelectedItem();
		boolean isOther = curr.equals("Other...");
		
		if (isOther) {
			String url = createURL(other.getText());
			co.setURL(url);
		} else {		
			co.setURL(nameURLPair.get(curr));
		}
		
		double number = 0;
		
		try {
			number = Double.parseDouble(amount.getText());
		} catch (NumberFormatException e) {
			status.setText("Status: INVALID AMOUNT");
			return;
		}
		
		if (number == 0) {
			status.setText("Status: INVALID AMOUNT");
			return;
		}
		
		double price = co.fetchPrice();
		if (isOther) {
			data.add(new Stock(other.getText(), number, price * number, this.bitcoinValue));
		} else {
			data.add(new Stock(curr, number, price * number, this.bitcoinValue));
		}
		
		total += (double) Math.round(price * number * 100) / 100;
		totalVal.setText("Total Value (USD): " + (total));
		list.setItems(data);
		if (isOther) {
			status.setText("Satus: ADDED " + other.getText());
		} else {
			status.setText("Status: ADDED " + curr);
		}
		clearInputs();
	}
	
	private String createURL(String currencyName) {
		return "https://coinmarketcap.com/currencies/" + currencyName + "/";
	}
	
	private void clearInputs() {
		amount.setText("");
		other.setText("");
	}

}
