package com.versacomllc.training.service;

import java.util.List;

import com.versacomllc.training.message.CourseEnrollmentMessage;
import com.versacomllc.training.message.CourseEnrollmentMsg;

/**
 * Define the service interface for ....
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface EnrollmentService {

  
	/**
	 * Get the list of all course 
	 * 
	 * @return
	 */
	List<CourseEnrollmentMessage> getUserEnrollments(Long userId);
	
	List<CourseEnrollmentMsg> getAllEnrollments();
	
	String addEnrollment(CourseEnrollmentMsg msg);
	
	String updateEnrollment(CourseEnrollmentMsg msg);
	
	String deleteEnrollment(Long id);
	
	CourseEnrollmentMsg getEnrollment(Long id);
}
