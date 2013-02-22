package com.swiftmind.toggle;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main-Program, starting the JavaFX.
 * 
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
public class ToggleMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		URL url = getClass().getResource("view.fxml");
		Parent root = FXMLLoader.load(url);
		
		stage.setTitle("Toggle");
		stage.setScene(new Scene(root, 560, 380));
		stage.show();
	}

	public static void main(String[] args) {
		ToggleMain.launch(args);
	}
}
