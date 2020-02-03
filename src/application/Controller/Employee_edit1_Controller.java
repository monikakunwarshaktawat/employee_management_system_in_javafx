package application.Controller;

import java.io.IOException;
import java.util.Optional;
import application.Model.*;
import application.View.*;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

public class Employee_edit1_Controller {
	@FXML
	private AnchorPane editPane;
	@FXML
	private JFXTextField editName;

	@FXML
	private JFXTextField editEmail;

	@FXML
	private JFXTextField editPosition;
	/*
	 * Variable id store the id of employee whose data has to be edited or
	 * deleted.
	 */
	public int id;

	/**
	 * Action taken on clicking edit button.
	 * 
	 * @param event
	 *            object of ActionEvent class.
	 * @throws IOException
	 */
	@FXML
	void Edit(ActionEvent event) throws IOException {
		String Name = editName.getText();
		String Email = editEmail.getText();
		String Position = editPosition.getText();

		EmployeeModel.info[0]=Name;
		EmployeeModel.info[1]=Email;
		EmployeeModel.info[2]=Position;
				
				// Updating the edited data of employee in the database.

				EmployeeModel.updateEmployee();
		int pageIndex = TableController.getInstance().getPageIndex();

		TableController.getInstance().update();
		TableController.getInstance().setPageIndex(pageIndex);
		// loading the form pane with add form
		TableController.getInstance().createpage("/application/View/Employee_add1.fxml");
		TableController.getInstance().setTable();
	}

	/**
	 * Action taken on clicking cross(X) Button.
	 * 
	 * @param event
	 *            object of ActionEvent class.
	 * @throws IOException
	 */
	@FXML
	void cancel(ActionEvent event) throws IOException {

		TableController.getInstance().createpage("/application/View/Employee_add1.fxml");
		TableController.getInstance().setTable();
	}

	/**
	 * Function for setting the field of form with employee data selected for
	 * edit.
	 * 
	 * @param e
	 *            object of Employee class.
	 */
	public void setdata(Employee e) {
		// TODO Auto-generated method stub
		editName.setText(e.getName());
		editEmail.setText(e.getEmail());
		editPosition.setText(e.getPosition());
		id = e.getId();
	}

	/**
	 * Action taken on clicking Delete Button.
	 * 
	 * @param event
	 *            object of ActionEvent class.
	 * @throws IOException
	 */
	@FXML
	void delete(ActionEvent event) throws IOException {
		// Creating the alert box.
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("DELETE");
		alert.setContentText("Are you sure you want to delete the selected employee?");
		ButtonType deleteEmployee = new ButtonType("Delete Employee");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(deleteEmployee, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		// Action taken if DELETE button is pressed.
		if (result.get() == deleteEmployee) {
			EmployeeModel.deleteEmployee(id);
		
			int pageIndex = TableController.getInstance().getPageIndex();
			TableController.getInstance().update();
			if (pageIndex == TableController.getInstance().pageCount) {
				pageIndex = pageIndex - 1;
			}

			TableController.getInstance().setPageIndex(pageIndex);
			TableController.getInstance().createpage("/application/View/Employee_add1.fxml");
			TableController.getInstance().setTable();
		}
	}

}
