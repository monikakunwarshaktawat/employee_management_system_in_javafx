package application.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Model.EmployeeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class EmployeeAddController {

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
	 *            object of class ActionEvent.
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
		int pageIndex = EmployeeController.getInstance().pageCount;
		EmployeeController.getInstance().update();
		EmployeeController.getInstance().setPageIndex(pageIndex);

	}

}
