package me.alex.eth;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Parent root;
	private Stage mainStage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.mainStage = primaryStage;
		try {
			this.root = FXMLLoader.load(getClass().getResource("/me/alex/graphics/Graphics.fxml"));
			this.mainStage.setScene(new Scene(this.root));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mainStage.setTitle("Currency/Asset Calculator");
		this.mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/me/alex/graphics/icon.jpg")));
		this.mainStage.setResizable(false);
		this.mainStage.show();
	}

}
