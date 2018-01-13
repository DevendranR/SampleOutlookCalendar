package com.dev.service;

import java.util.List;

import com.dev.bean.Schedule;
import com.dev.bean.Team;
import com.dev.resources.Factory;

public class TeamService {
	public List<Team> fetchAllTeams() throws Exception{
		
			return Factory.createTeamDAO().fetchAllTeams();
	
	}
	public String scheduleShift(Schedule schedule) throws Exception{
		
			return Factory.createTeamDAO().scheduleShift(schedule);
				
	}
	public List<Schedule> fetchScheduledShift(Schedule schedule) throws Exception{
		
			return Factory.createTeamDAO().fetchScheduledShift(schedule);	
	}
	public List<String> fetchEmployeeBasedOnTeam(String team) throws Exception{
		
		return Factory.createTeamDAO().fetchEmployeeBasedOnTeam(team);	
	}
	public String deleteScheduledData(int scheduleId) throws Exception {

		return Factory.createTeamDAO().deleteScheduledData(scheduleId);
		
	}
	public List<Schedule> fetchScheduledShift(int scheduleId) throws Exception {
		return Factory.createTeamDAO().fetchScheduledShift(scheduleId);	
	}
}
