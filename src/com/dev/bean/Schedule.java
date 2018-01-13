package com.dev.bean;

import java.util.Date;

public class Schedule {
	private Integer scheduleId;
	private String employeeName;
	private Date assignedDate;
	private String employeeTeam;
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Date getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getEmployeeTeam() {
		return employeeTeam;
	}
	public void setEmployeeTeam(String employeeTeam) {
		this.employeeTeam = employeeTeam;
	}
	
	
}
