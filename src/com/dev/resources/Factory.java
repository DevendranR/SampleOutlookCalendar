package com.dev.resources;

import java.util.ArrayList;
import java.util.List;

import com.dev.bean.Schedule;
import com.dev.bean.Team;
import com.dev.persistence.dao.TeamDAO;
import com.dev.persistence.entity.ScheduleEntity;
import com.dev.service.TeamService;


public class Factory
{
	public static TeamDAO createTeamDAO(){
		return new TeamDAO();
	}
	public static TeamService createTeamService(){
		return new TeamService();
	}
	public static Team createTeam(){
		return new Team();
	}
	public static Schedule createSchedule(){
		return new Schedule();
	}
	public static ScheduleEntity createScheduleEntity(){
		return new ScheduleEntity();
	}
	public static ArrayList<Team> createTeamArrayList(){
		
		return new ArrayList<Team>();
	}
	public static List<Schedule> createScheduleArrayList() {
		return new ArrayList<Schedule>();
	}
}