package com.dev.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.dev.bean.Schedule;
import com.dev.bean.Team;
import com.dev.resources.Factory;
import com.dev.service.TeamService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("ScheduleAPI")
public class ScheduleAPI {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response scheduleShift(String dataRecieved)
	{
		Response returnValue = null;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd-MM-yyyy");
		Gson gson = gsonBuilder.create();
		
		Schedule schedule2 = gson.fromJson(dataRecieved,Schedule.class);
		try {
			TeamService teamService = Factory.createTeamService();
			String message = teamService.scheduleShift(schedule2);
			List<Schedule> schedule = teamService.fetchScheduledShift(schedule2);
			String value2 = gson.toJson(schedule);
			
			returnValue = Response.ok(value2).build();
		} catch (Exception e) {
			String value = gson.toJson(e.getMessage());
			if(e.getMessage().contains("DAO")){
			returnValue=Response.status(Status.SERVICE_UNAVAILABLE).entity(value).build();
			}
			else{
				returnValue=Response.status(Status.BAD_REQUEST).entity(value).build();
			}
			
		}
		
		return returnValue;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchTeams()
	{
		Response returnValue = null;
		Gson gson = new GsonBuilder().create();
		try {
			TeamService teamService=Factory.createTeamService();
			
			List<Team> teamList = teamService.fetchAllTeams();
			List<String> teamNameList =new ArrayList<String>();
			for (Team team : teamList) {
				teamNameList.add(team.getTeamName());
			}
			String value = gson.toJson(teamNameList);
			returnValue = Response.ok(value).build();
		} catch (Exception e) {
			
			String value = gson.toJson(e.getMessage());
			if(e.getMessage().contains("DAO")){
				returnValue=Response.status(Status.SERVICE_UNAVAILABLE).entity(value).build();
			}else{
				returnValue=Response.status(Status.BAD_REQUEST).entity(value).build();
			
			}
		}
		
		return returnValue;
	}
	
	@PUT
	@Path("/teamMembers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response populateTeamMembers(String dataRecieved)
	{
		
		Response returnValue = null;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd-MM-yyyy");
		Gson gson = gsonBuilder.create();
		
		Schedule schedule = gson.fromJson(dataRecieved,Schedule.class);
		
		try {
			TeamService teamService = Factory.createTeamService();
			List<Schedule> scheduleDetails = teamService.fetchScheduledShift(schedule);
			
			String value = gson.toJson(scheduleDetails);
			
			returnValue = Response.ok(value).build();
		} catch (Exception e) {
			String value = gson.toJson(e.getMessage());
			if(e.getMessage().contains("DAO")){
			returnValue=Response.status(Status.SERVICE_UNAVAILABLE).entity(value).build();
			}
			else{
				returnValue=Response.status(Status.BAD_REQUEST).entity(value).build();
			}
			
		}
		
		return returnValue;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response fetchScheduledDetails(String dataRecieved)
	{
		Response returnValue = null;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd-MM-yyyy");
		Gson gson = gsonBuilder.create();
		
		String team = gson.fromJson(dataRecieved,String.class);
		try {
			TeamService teamService = Factory.createTeamService();
			List<String> memberList = teamService.fetchEmployeeBasedOnTeam(team);
			
			String value = gson.toJson(memberList);
			
			returnValue = Response.ok(value).build();
		} catch (Exception e) {
			String value = gson.toJson(e.getMessage());
			if(e.getMessage().contains("DAO")){
			returnValue=Response.status(Status.SERVICE_UNAVAILABLE).entity(value).build();
			}
			else{
				returnValue=Response.status(Status.BAD_REQUEST).entity(value).build();
			}
			
		}
		
		return returnValue;
	}
	
	@DELETE
	@Path("/{scheduleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteScheduledEmployeeShift(@PathParam("scheduleId") int scheduleId)
	{
		Response returnValue = null;
		Gson gson = new GsonBuilder().create();
		
		try {
			TeamService teamService = Factory.createTeamService();
			List<Schedule> schedule = teamService.fetchScheduledShift(scheduleId);
			teamService.deleteScheduledData(scheduleId);
			
			String value = gson.toJson(schedule);
			
			returnValue = Response.ok(value).build();
		} catch (Exception e) {
			String value = gson.toJson(e.getMessage());
			if(e.getMessage().contains("DAO")){
				returnValue=Response.status(Status.SERVICE_UNAVAILABLE).entity(value).build();
			}else{
				returnValue=Response.status(Status.BAD_REQUEST).entity(value).build();
			
			}
		}
		
		return returnValue;
	}
}
