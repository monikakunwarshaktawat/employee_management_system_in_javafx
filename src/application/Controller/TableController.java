package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import application.Model.*;
import application.View.*;

public class TableController implements Initializable {
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
	private Pagination pagination;
	@FXML
	private AnchorPane formPane;

	// Creating instance of given class.
	private static TableController instance;

	// Variable declaring the no of rows in table.
	private final static int rowsperpage = 2;

	// Variable pageCount is declared to store the no. of pages for pagination.
	public static int pageCount;

	/**
	 * Constructor for assigning the object of class to variable instance.
	 */
	public TableController() {
		instance = this;
	}

	/**
	 * Function returning the instance of the controller.
	 */
	public static TableController getInstance() {
		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		id.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
		name.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
		email.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
		position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
		try {
			createpage("/application/View/Employee_add1.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTable();
		update();

	}

	/**
	 * Function called on clicking the row in table.
	 */
	public void setTable() {
		/*
		 * Function setOnMousePressed is used to set certain action on clicking
		 * row in table.
		 */
		table.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Employee currentPerson = table.getSelectionModel().getSelectedItem();
				if (currentPerson != null) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/application/View/Employee_edit_1.fxml"));
					try {
						loader.load();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Assigning the controller to the loader.
					Employee_edit1_Controller Eedit = loader.getController();
					AnchorPane pane;
					pane = loader.getRoot();
					formPane.getChildren().setAll(pane);

					// Getting the data of row of pressed edit button.
					int ID = currentPerson.getId();

					EmployeeModel.eid=ID;

					// Passing the data of employee selected for edit to
					// Employee_edit Controller.
					Eedit.setdata(currentPerson);
				}
			}
		});
	}

	/**
	 * Function for setting the scene in formPane.
	 * 
	 * @param loc
	 *            string having the name of FXML file.
	 * @throws IOException
	 */
	public void createpage(String loc) throws IOException {
		AnchorPane pane;
		pane = FXMLLoader.load(getClass().getResource(loc));
		formPane.getChildren().setAll(pane);
	}

	/**
	 * Function returning the table to set in pagination.
	 * 
	 * @param pageIndex
	 *            the index of current page on pagination.
	 * @param Edata
	 *            observable list containing the data of employee.
	 * @return table with Employee data.
	 */
	private Node createPage(int pageIndex, ObservableList<Employee> Edata) {
		int fromIndex = pageIndex * rowsperpage;
		int toIndex = Math.min(fromIndex + rowsperpage, Edata.size());
		//toIndex should be greater than fromIndex for insertion of data in table.
		if (toIndex > fromIndex) {
			table.setItems(FXCollections.observableArrayList(Edata.subList(fromIndex, toIndex)));
		}
		return table;
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
			c = BaseModel.Connector();
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

}
