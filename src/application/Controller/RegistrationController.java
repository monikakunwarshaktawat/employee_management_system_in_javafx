package application.Controller;

import java.io.IOException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import application.Model.*;
import application.View.*;

public class RegistrationController {
	@FXML
	private JFXTextField name;
	@FXML
	private JFXTextField email;
	@FXML
	private JFXPasswordField password;
	@FXML
	private JFXButton loginbutton;
	@FXML
	private JFXButton signUpButton;

	/**
	 * Function defining the action to be taken on clicking login button.
	 * 
	 * @param event
	 *            object of ActionEvent class.
	 * @throws IOException
	 */
	@FXML
	public void login(ActionEvent event) throws IOException {
		String Name = name.getText();

		String Email = email.getText();
		String Password = password.getText();
		// Variable checking the validation of email.
		Boolean emailValidate = new EmailValidator().validate(Email);
		// Variable checking the validation of password.
		Boolean passwordValidate = new PasswordValidator().validate(Password);
		if (!emailValidate) {
			// Creating notification.
			Notifications.create().darkStyle().hideAfter(Duration.seconds(2)).title("EMAIL").text("Enter valid email")
					.position(Pos.CENTER).showError();
		} else if (!passwordValidate) {
			Notifications.create().darkStyle().hideAfter(Duration.seconds(2)).title("PASSWORD")
					.text("Enter valid password").position(Pos.CENTER).showError();
		} else {
			RegistrationModel.insertDB(Name, Email, Password);
			((Node) (event.getSource())).getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("/application/View/dashboard.fxml"));
			Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(root, 867, 585));
			primaryStage.show();
		}
	}

	/**
	 * Function defining the action to be taken on clicking SignUp button .
	 * 
	 * @param event
	 *            object of ActionEvent class.
	 * @throws IOException
	 */
	@FXML
	public void signUp(ActionEvent event) throws IOException {
		String Name = name.getText();
		String Email = email.getText();
		String Password = password.getText();
		Boolean emailValidate = new EmailValidator().validate(Email);
		Boolean passwordValidate = new PasswordValidator().validate(Password);
		if (!emailValidate) {
			Notifications.create().darkStyle().hideAfter(Duration.seconds(2)).title("EMAIL").text("Enter valid email")
					.position(Pos.CENTER).showError();
		} else if (!passwordValidate) {
			Notifications.create().darkStyle().hideAfter(Duration.seconds(2)).title("PASSWORD")
					.text("Enter valid password").position(Pos.CENTER).showError();
		} else {
			RegistrationModel.insertDB(Name, Email, Password);
			((Node) (event.getSource())).getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("/application/View/Login.fxml"));
			Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(root, 1355, 898));
			primaryStage.show();
		}
	}
}
