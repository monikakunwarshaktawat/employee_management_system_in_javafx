package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.prism.impl.Disposer.Record;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import application.Model.*;
import application.View.*;

/**
 * EmployeeController handle the employeeList.fxml.
 * 
 * @author Monika.
 */
public class EmployeeController implements Initializable {
	@FXML
	private AnchorPane employeePane;

	private static EmployeeController instance;// instance of EmployeeController

	/**
	 * Constructor assigning the instance of class to instance variable.
	 */
	public EmployeeController() {
		instance = this;
	}

	/**
	 * Function returning the instance of the controller.
	 */
	public static EmployeeController getInstance() {
		return instance;
	}

	@FXML
	private Pagination pagination;

	private final static int rowsperpage = 2;

	@FXML
	private TableView<Employee> table;

	@FXML
	private TableColumn<Employee, Integer> id;

	@FXML
	private TableColumn<Employee, String> name;

	@FXML
	private TableColumn<Employee, String> email;

	@FXML
	private TableColumn<Employee, String> position;

	@FXML
	private TableColumn<Employee, String> action;

	@FXML
	private TableColumn<Record, Boolean> edit;

	@FXML
	private TableColumn<Record, Boolean> delete;
	// Variable pageCount is declared to store the no. of pages for pagination.
	public static int pageCount;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		EmployeeModel.employeeTable();
		id.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
		name.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
		email.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
		position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
		// Initially load the employee pane with add form.
		try {
			AnchorPane pane;
			pane = FXMLLoader.load(getClass().getResource("/application/View/Employee_add.fxml"));
			employeePane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Initially loading the table with current data of employee table.
		update();
		editAction();
		deleteAction();

	}

	/**
	 * Function for returning the table with employee database values for
	 * current page.
	 */
	private Node createPage(int pageIndex, ObservableList<Employee> Edata) {
		int fromIndex = pageIndex * rowsperpage;
		int toIndex = Math.min(fromIndex + rowsperpage, Edata.size());
		//toIndex should be greater than fromIndex for insertion of data in table.
		if (toIndex > fromIndex) {
			table.setItems(FXCollections.observableArrayList(Edata.subList(fromIndex, toIndex)));
			editAction();
		}
		return table;
	}

	/**
	 * Function foe setting the scene in employee pane.
	 * 
	 * @param loc
	 *            the name of the fxml document.
	 * @throws IOException
	 */
	public void createpage(String loc) throws IOException {
		AnchorPane pane;
		pane = FXMLLoader.load(getClass().getResource(loc));
		employeePane.getChildren().setAll(pane);
	}

	/**
	 * Function returning the index of current page on pagination.
	 * 
	 * @return page index.
	 */
	public int getPageIndex() {
		return (pagination.getCurrentPageIndex());
	}

	/**
	 * Function setting the pagination's current page index.
	 * 
	 * @param pageIndex
	 *            integer value .
	 */
	public void setPageIndex(int pageIndex) {
		pagination.setCurrentPageIndex(pageIndex);
	}

	/**
	 * Function assigning the value of PageCount to variable pageCount.
	 * 
	 * @param PageCount
	 *            integer value shows the no of pages for pagination.
	 */
	public void countPages(int PageCount) {
		pageCount = PageCount;
	}

	/**
	 * Function loading the pagination with table having employee data.
	 */
	public void update() {
		int datasize = 0;
		ObservableList<Employee> data = FXCollections.observableArrayList();
		
		Connection c = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			c =BaseModel.Connector();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			String sql = "SELECT * FROM employee ;";
			pstmt = c.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// Adding the data to ObservableList<Employee>data.
				data.add(new Employee(rs.getInt("eid"), rs.getString("name"), rs.getString("email"),
						rs.getString("position")));
				datasize++;
			}
			// Defining the pagecount for pagination.
			int pagecount = (int) Math.ceil((double) datasize / rowsperpage);
			countPages(pagecount);
			// Setting the page count for pagination.
			pagination.setPageCount(pagecount);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				@Override
				public Node call(Integer pageIndex) {
					return createPage(pageIndex, data);
				}
			});

			rs.close();
			pstmt.close();
			c.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function deleteAction controlling the delete button task.
	 */
	public void deleteAction() {
		// Insert button.
		delete.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});
		// Adding the Button to the cell.
		delete.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

			@Override
			public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
				return new ButtonCell();
			}

		});
	}

	/**
	 * ButtonCell is the class defining the button cell.
	 * 
	 * @author Monika
	 */
	private class ButtonCell extends TableCell<Record, Boolean> {
		// Creating the delete button.
		final Button deleteButton = new Button("Delete");

		ButtonCell() {

			// Action when the button is pressed.
			deleteButton.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent t) {
					// Get Selected Item.
					Employee currentPerson = (Employee) ButtonCell.this.getTableView().getItems()
							.get(ButtonCell.this.getIndex());
					int ID = currentPerson.getId();
					EmployeeModel.deleteEmployee(ID);// Deleting the selected employee.
					setGraphic(null);

					int pageIndex = getPageIndex();

					if (pageIndex == pageCount) {
						pageIndex = pageIndex - 1;
					}
					update();
					setPageIndex(pageIndex);
				}
			});
		}

		/**
		 * Display button if the row is not empty
		 */
		@Override
		protected void updateItem(Boolean t, boolean empty) {

			if (!empty) {
				setGraphic(deleteButton);

			}
		}
	}

	/**
	 * Function editAction controlling the edit button task.
	 */
	public void editAction() {
		// Insert button.
		edit.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});
		// Adding the Button to the cell.
		edit.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

			@Override
			public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
				return new ButtonCell1();
			}

		});
	}

	/**
	 * ButtonCell1 is the class defining the button cell.
	 * 
	 * @author Monika
	 */
	private class ButtonCell1 extends TableCell<Record, Boolean> {
		final Button editButton = new Button("Edit");// Creating the edit
														// button.

		ButtonCell1() {

			// Action when the button is pressed.
			editButton.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent t) {

					try {

						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/application/View/Emloyee_edit.fxml"));
						loader.load();
						// Assigning the controller to the loader.
						Employee_edit_Controller Eedit = loader.getController();
						AnchorPane pane;
						pane = loader.getRoot();
						employeePane.getChildren().setAll(pane);
						// Getting the data of row of pressed edit button.
						Employee currentPerson = (Employee) ButtonCell1.this.getTableView().getItems()
								.get(ButtonCell1.this.getIndex());
						int ID = currentPerson.getId();

						EmployeeModel.eid=ID;
						/*
						 * Passing the data of employee selected for edit to
						 * Employee_edit Controller.
						 */
						Eedit.setdata(currentPerson);

					} catch (IOException e) {

						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			});
		}

		// Display button if the row is not empty.

		@Override
		protected void updateItem(Boolean t, boolean empty) {

			if (!empty) {
				setGraphic(editButton);

			}
		}
	}
}
