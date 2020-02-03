package application.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import application.Model.*;

public class EmployeeAdd1Controller {
	@FXML
	private AnchorPane employeePane;

	@FXML
	private JFXTextField nameField;

	@FXML
	private JFXTextField emailField;

	@FXML
	private JFXTextField positionField;

	@FXML
	private JFXButton addButton;

	/**
	 * Action taken on clicking add button.
	 * 
	 * @param event
	 *            object of ActionEvent class.
	 */
	@FXML
	void addAction(ActionEvent event) {
		String Name = nameField.getText();
		String Email = emailField.getText();
		String Position = positionField.getText();
		// inserting the added employee data in database
		EmployeeModel.insertEmployee(Name, Email, Position);

		nameField.clear();
		emailField.clear();
		positionField.clear();
		// updating the employee table on screen
		int pageIndex = TableController.getInstance().pageCount;
		TableController.getInstance().update();
		TableController.getInstance().setPageIndex(pageIndex);
		TableController.getInstance().setTable();
	}

}
