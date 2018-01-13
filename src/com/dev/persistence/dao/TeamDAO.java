package com.dev.persistence.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dev.bean.Schedule;
import com.dev.bean.Team;
import com.dev.persistence.entity.EmployeeEntity;
import com.dev.persistence.entity.ScheduleEntity;
import com.dev.persistence.entity.TeamEntity;
import com.dev.resources.Factory;
import com.dev.resources.HibernateUtility;

public class TeamDAO {
	public String scheduleShift(Schedule schedule) throws Exception{
		SessionFactory sessionFactory=null;
		Session session=null;
		try{
			sessionFactory=HibernateUtility.createSessionFactory();
			session=sessionFactory.openSession();
			
			Query query1=session.createQuery("Select e.employeeId from EmployeeEntity e where e.employeeName=?");
			query1.setParameter(0, schedule.getEmployeeName());
			String employeeId = query1.list().get(0)+"";
			
			Query query2=session.createQuery("Select t.teamId from TeamEntity t where t.teamName=?");
			query2.setParameter(0, schedule.getEmployeeTeam());
			String employeeTeam = query2.list().get(0)+"";
			
			
			
			
			ScheduleEntity scheduleEntity= Factory.createScheduleEntity();
			scheduleEntity.setEmployeeId(employeeId);
			scheduleEntity.setEmployeeTeam(employeeTeam);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(schedule.getAssignedDate());
			int month = calendar.get(Calendar.MONTH)+1;
			String date = calendar.get(Calendar.DATE)+"-"+month+"-"+calendar.get(Calendar.YEAR);
			scheduleEntity.setScheduledDate(date);
			
			Query query3=session.createQuery("Select e from ScheduleEntity e where e.employeeId=? and e.employeeTeam=? and e.scheduledDate=?");
			query3.setParameter(0, employeeId);
			query3.setParameter(1, employeeTeam);
			query3.setParameter(2, date);
			
			List<ScheduleEntity> list = query3.list();
			
			if(list.size()!=0){
				throw new Exception(schedule.getEmployeeName()+" from "+schedule.getEmployeeTeam()+" is already scheduled on "+date);
			}
			
			session.beginTransaction();
			Integer id = (Integer) session.save(scheduleEntity);
			session.getTransaction().commit();
			return "Shift for "+schedule.getEmployeeName()+" from "+schedule.getEmployeeTeam()+" has been scheduled with Id:"+id;
			
		}catch (HibernateException e) {
			DOMConfigurator.configure("src/com/dev/resources/log4j.xml");
			Logger logger=Logger.getLogger(this.getClass());
			logger.debug(e.getMessage(),e);
			throw new Exception("DAO.TECHINAL_ERROR");
		} catch (Exception exception) {
			throw exception;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
			
		}	
	}
	public List<Schedule> fetchScheduledShift(Schedule schedule) throws Exception{
		SessionFactory sessionFactory=null;
		Session session=null;
		try{
			sessionFactory=HibernateUtility.createSessionFactory();
			session=sessionFactory.openSession();
			Query query2=session.createQuery("Select t.teamId from TeamEntity t where t.teamName=?");
			query2.setParameter(0, schedule.getEmployeeTeam());
			String employeeTeam = query2.list().get(0)+"";
			
			
			Query query=session.createQuery("Select se,ee from ScheduleEntity se , EmployeeEntity ee where se.employeeId=ee.employeeId and se.scheduledDate=? and se.employeeTeam=?");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(schedule.getAssignedDate());
			int month = calendar.get(Calendar.MONTH)+1;
			String date = calendar.get(Calendar.DATE)+"-"+month+"-"+calendar.get(Calendar.YEAR);
			
			query.setParameter(0, date);
			query.setParameter(1, employeeTeam);
			
			List list = query.list();
			List<Schedule> sList = Factory.createScheduleArrayList();

			for (Object en : list) {
				Schedule schedule2 = Factory.createSchedule();
				
				Object[] objectArray  =  (Object[])en;
				ScheduleEntity sEntity = (ScheduleEntity)objectArray[0];
				EmployeeEntity eEntity =  (EmployeeEntity) objectArray[1];
				schedule2.setScheduleId(sEntity.getScheduleId());
				schedule2.setEmployeeName(eEntity.getEmployeeName());
				schedule2.setEmployeeTeam(schedule.getEmployeeTeam());
				String[] dateArray = sEntity.getScheduledDate().split("-");
				Calendar calendar2 = Calendar.getInstance();
				calendar2.set(Calendar.YEAR, Integer.parseInt(dateArray[2]));
				calendar2.set(Calendar.MONTH, Integer.parseInt(dateArray[1])-1);
				calendar2.set(Calendar.DATE, Integer.parseInt(dateArray[0]));
				Date assignedDate = calendar2.getTime();
				schedule2.setAssignedDate(assignedDate);
				sList.add(schedule2);
			}
			return sList;
			
		}catch (HibernateException e) {
			DOMConfigurator.configure("src/com/dev/resources/log4j.xml");
			Logger logger=Logger.getLogger(this.getClass());
			logger.debug(e.getMessage(),e);
			throw new Exception("DAO.TECHINAL_ERROR");
		} catch (Exception exception) {
			throw exception;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
			
		}
	}
	public List<Team> fetchAllTeams() throws Exception {
		SessionFactory sessionFactory=null;
		Session session=null;
		try{
			sessionFactory=HibernateUtility.createSessionFactory();
			session=sessionFactory.openSession();
			Query query=session.createQuery("Select je from TeamEntity je");
			List<TeamEntity> jobList=query.list();
			List<Team> tList = Factory.createTeamArrayList();
			for (TeamEntity job : jobList) {
				Team team2 = Factory.createTeam();
				team2.setTeamId(job.getTeamId());
				team2.setTeamName(job.getTeamName());
				tList.add(team2);
			}
			return tList;
			
		}catch (HibernateException e) {
			DOMConfigurator.configure("src/com/dev/resources/log4j.xml");
			Logger logger=Logger.getLogger(this.getClass());
			logger.debug(e.getMessage(),e);
			throw new Exception("DAO.TECHINAL_ERROR");
		} catch (Exception exception) {
			throw exception;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
			
		}
	}
	
	public List<String> fetchEmployeeBasedOnTeam(String team) throws Exception {
		SessionFactory sessionFactory=null;
		Session session=null;
		try{
			sessionFactory=HibernateUtility.createSessionFactory();
			session=sessionFactory.openSession();
			

			Query query1=session.createQuery("Select je.teamId from TeamEntity je where je.teamName=?");
			query1.setParameter(0, team);
			String teamId = query1.list().get(0)+"";
			
			Query query=session.createQuery("Select je.employeeName from EmployeeEntity je where je.employeeTeam=?");
			query.setParameter(0, teamId);
			List<String> employeeList=query.list();
			
			return employeeList;
			
		}catch (HibernateException e) {
			DOMConfigurator.configure("src/com/dev/resources/log4j.xml");
			Logger logger=Logger.getLogger(this.getClass());
			logger.debug(e.getMessage(),e);
			throw new Exception("DAO.TECHINAL_ERROR");
		} catch (Exception exception) {
			throw exception;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
			
		}
	}
	public String deleteScheduledData(int scheduleId) throws Exception {
		SessionFactory sessionFactory=null;
		Session session=null;
		try{
			sessionFactory=HibernateUtility.createSessionFactory();
			session=sessionFactory.openSession();
			
			ScheduleEntity scheduleEntity= (ScheduleEntity)session.get(ScheduleEntity.class, scheduleId);
			if(scheduleEntity!=null)
			{
				session.beginTransaction();
				session.delete(scheduleEntity);
				session.getTransaction().commit();
			}
			
			return "schedule has been removed Successfully";
			
			
		}catch (HibernateException e) {
			DOMConfigurator.configure("src/com/dev/resources/log4j.xml");
			Logger logger=Logger.getLogger(this.getClass());
			logger.debug(e.getMessage(),e);
			throw new Exception("DAO.TECHINAL_ERROR");
		} catch (Exception exception) {
			throw exception;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
			
		}
		
	}
	public List<Schedule> fetchScheduledShift(int scheduleId) throws Exception {
		SessionFactory sessionFactory=null;
		Session session=null;
		try{
			sessionFactory=HibernateUtility.createSessionFactory();
			session=sessionFactory.openSession();
			
			ScheduleEntity scheduleEntity= (ScheduleEntity)session.get(ScheduleEntity.class, scheduleId);

			if(scheduleEntity!=null)
			{
				Query query2=session.createQuery("Select t.teamName from TeamEntity t where t.teamId=?");
				query2.setParameter(0, scheduleEntity.getEmployeeTeam());
				String employeeTeam = query2.list().get(0)+"";
				
				Query query=session.createQuery("Select se,ee from ScheduleEntity se , EmployeeEntity ee where se.employeeId=ee.employeeId and se.scheduledDate=? and se.employeeTeam=?");
				
				
				query.setParameter(0, scheduleEntity.getScheduledDate());
				query.setParameter(1, scheduleEntity.getEmployeeTeam());
				
				List list = query.list();
				List<Schedule> sList = Factory.createScheduleArrayList();

				for (Object en : list) {
					Object[] objectArray  =  (Object[])en;
					ScheduleEntity sEntity = (ScheduleEntity)objectArray[0];
					EmployeeEntity eEntity =  (EmployeeEntity) objectArray[1];
					if(!sEntity.getScheduleId().equals(scheduleId)){
						Schedule schedule2 = Factory.createSchedule();
						schedule2.setScheduleId(sEntity.getScheduleId());
						schedule2.setEmployeeName(eEntity.getEmployeeName());
						schedule2.setEmployeeTeam(employeeTeam);
						String[] dateArray = sEntity.getScheduledDate().split("-");
						Calendar calendar2 = Calendar.getInstance();
						calendar2.set(Calendar.YEAR, Integer.parseInt(dateArray[2]));
						calendar2.set(Calendar.MONTH, Integer.parseInt(dateArray[1])-1);
						calendar2.set(Calendar.DATE, Integer.parseInt(dateArray[0]));
						Date assignedDate = calendar2.getTime();
						schedule2.setAssignedDate(assignedDate);
						sList.add(schedule2);
					}
					
					
				}
				return sList;
			}
			
			return null;
			
			
		}catch (HibernateException e) {
			DOMConfigurator.configure("src/com/dev/resources/log4j.xml");
			Logger logger=Logger.getLogger(this.getClass());
			logger.debug(e.getMessage(),e);
			throw new Exception("DAO.TECHINAL_ERROR");
		} catch (Exception exception) {
			throw exception;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
			
		}
	}
	


}
