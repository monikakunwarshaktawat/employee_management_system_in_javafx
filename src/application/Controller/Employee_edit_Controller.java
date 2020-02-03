package application.Controller;

import java.io.IOException;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import application.Model.*;
import application.View.*;

public class Employee_edit_Controller {
	@FXML
	private AnchorPane editPane;
	@FXML
	private JFXTextField editName;

	@FXML
	private JFXTextField editEmail;

	@FXML
	private JFXTextField editPosition;

	/**
	 * Action taken on clicking edit Button.
	 * 
	 * @param event
	 *            object of class ActionEvent.
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
		// Variable storing the page index of current page on pagination.
		int pageIndex = EmployeeController.getInstance().getPageIndex();

		EmployeeController.getInstance().update();
		EmployeeController.getInstance().setPageIndex(pageIndex);
		// Loading the employee pane with add form.
		EmployeeController.getInstance().createpage("/application/View/Employee_add.fxml");
	}

	// Action taken on clicking cross(X) Button.
	@FXML
	void cancel(ActionEvent event) throws IOException {

		EmployeeController.getInstance().createpage("/application/View/Employee_add.fxml");
	}

	/**
	 * Function for setting the field of form with employee data selected for
	 * edit.
	 * 
	 * @param e
	 *            Object of class Employee
	 */
	public void setdata(Employee e) {
		// TODO Auto-generated method stub
		editName.setText(e.getName());
		editEmail.setText(e.getEmail());
		editPosition.setText(e.getPosition());
	}
}
