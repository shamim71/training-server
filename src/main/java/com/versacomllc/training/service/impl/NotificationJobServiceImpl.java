package com.versacomllc.training.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.versacomllc.training.dao.UserCourseEnrollmentDao;
import com.versacomllc.training.domain.Course;
import com.versacomllc.training.domain.UserCourseEnrollment;
import com.versacomllc.training.service.AbstractPortalService;
import com.versacomllc.training.service.NotificationJobService;

@Service
public class NotificationJobServiceImpl extends AbstractPortalService implements NotificationJobService {
	 
	 @Autowired
	 UserCourseEnrollmentDao userCourseEnrollmentDao;
	 
	  /**
	   * This job will run on each day at 12 am. Could be configured later according to
	   * sync interval
	   */
	  @Scheduled(cron = "0 26 11 * * ?")
	  @Override
	  public void notifyReceivers() {
	    String threadName = Thread.currentThread().getName();
	    logger.debug("Scheduler started at: "
	        + Calendar.getInstance().getTime().toString() + " with thread: "
	        + threadName);


	    List<UserCourseEnrollment> courseEnrollments = userCourseEnrollmentDao.getAllUnfinishedEnrollment();
	    
	    for(UserCourseEnrollment enr: courseEnrollments){
	    	
	    	final String userName = enr.getEmployee().getFirstName() + " "+ enr.getEmployee().getLastName();
	    	final String email = enr.getEmployee().getEmail();
	    	final Course course = enr.getCourseEnrollment().getCourse();
	    	
	    	sendEnrollmentEmail(course.getName(), userName, email);
	    }
	  }
	  
		private void sendEnrollmentEmail(String course, String name, String email){
			
			logger.debug("Sending reminder Email notification...");
			
			
			
			String emailSubject = getText("course.completion.sub", course);
		
			String body = getText("course.completion.body", course);
			try {
				this.sendEmail(email, emailSubject, body, name);
			} catch (Exception e) {
				logger.error("Error while sending email notification ::"
						+ e.getMessage());
			}
		}
}

