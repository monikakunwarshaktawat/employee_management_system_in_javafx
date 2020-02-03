package application.Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.Model.*;
import application.View.*;
public class LoginController {
	@FXML
	private TextField emailField;
	@FXML
	private TextField passwordField;
	@FXML
	private Label message;

	/**
	 * function defining the action to be taken on pressing login button
	 * 
	 * @param event
	 *            object of ActionEvent class.
	 * @throws Exception
	 */
	@FXML
	public void Loginaction(ActionEvent event) throws Exception {
		String Email = emailField.getText();
		String Password = passwordField.getText();
		// Variable isLogin tells that whether user is valid or not.
		Boolean isLogin = RegistrationModel.isLogin(Email, Password);
		// Action taken if the login user is valid user.
		if (isLogin) {

			((Node) (event.getSource())).getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("/application/View/dashboard.fxml"));
			Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(root, 1441, 886));
			primaryStage.show();
		}
		// action taken if the login user is not valid user
		else

		{
			message.setText("Login failed.Please register yourself");
		}
	}

	/**
	 * Function defining the action to be taken on pressing Register button.
	 * 
	 * @param event
	 *            object of ActionEvent class.
	 * @throws Exception
	 */
	@FXML
	public void Registeraction(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/application/View/registration.fxml"));
		Stage primaryStage = new Stage();
		primaryStage.setScene(new Scene(root, 867, 585));
		primaryStage.show();
	}

}
