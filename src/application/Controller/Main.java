package application.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import application.Model.*;
import application.View.*;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Initially loading the Login page to window.
			RegistrationModel.tableCreate();
			Parent root = FXMLLoader.load(getClass().getResource("/application/View/Login.fxml"));
			Scene scene = new Scene(root, 772, 512);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
