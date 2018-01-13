package com.dev.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Schedule")
@SequenceGenerator(name="schedulePKey",sequenceName="hibernate_sequence",allocationSize=1)
public class ScheduleEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="schedulePKey")
	private Integer scheduleId;
	private String employeeId;
	private String scheduledDate;
	private String employeeTeam;
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeTeam() {
		return employeeTeam;
	}
	public void setEmployeeTeam(String employeeTeam) {
		this.employeeTeam = employeeTeam;
	}
	public String getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	
	
}
