package application.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class Employee {

	/*
	 * Variables id ,name email,position stores the id,name,email,position of
	 * employee.
	 */
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty email;
	private SimpleStringProperty position;

	// Constructor of Employee class
	public Employee(Integer id, String name, String email, String position) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.email = new SimpleStringProperty(email);
		this.position = new SimpleStringProperty(position);

	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public String getPosition() {
		return position.get();
	}

	public void setPosition(String position) {
		this.position.set(position);
	}

	
}
