package com.dev.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Employee")
public class EmployeeEntity {
	@Id
	private String employeeId;
	private String employeeName;
	private String employeeTeam;
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeTeam() {
		return employeeTeam;
	}
	public void setEmployeeTeam(String employeeTeam) {
		this.employeeTeam = employeeTeam;
	}
	
	
}
