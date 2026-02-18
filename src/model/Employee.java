/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author elyas
 */
import main.Logable; 

public class Employee extends Person implements Logable {

	private int employeeId;
	private String password;
	
	private static final int EMPLOYEE_ID = 123;
	private static final String PASSWORD = "test";

	public Employee(String name) {
		super(name);
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean login(int user, String password) {
		if (user == EMPLOYEE_ID && password.equals(PASSWORD)) {
			return true;
		} else {
			return false;
		}
	}
}