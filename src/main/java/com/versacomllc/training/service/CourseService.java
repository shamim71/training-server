package com.versacomllc.training.service;

import java.util.List;

import com.versacomllc.training.message.CourseMessage;
import com.versacomllc.training.message.UserCourse;
import com.versacomllc.training.message.response.GenericResponse;

/**
 * Define the service interface for customer registration management.
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface CourseService {

  
	/**
	 * Get the list of all course 
	 * 
	 * @return
	 */
	List<CourseMessage> getAllCourses();

	
	/**
	 * Add a new course 
	 * 
	 * @param message the course information
	 * 
	 * @return the generated response
	 */
	GenericResponse<String> addCourse(CourseMessage message);
	
	/**
	 * Update course 
	 * 
	 * @param message the course information
	 * 
	 * @return the generated response
	 */
	GenericResponse<String> updateCourse(String id,CourseMessage message);	
	
	
	/**
	 * Remove existing course
	 * 
	 * @param id
	 * @return
	 */
	GenericResponse<String> removeCourse(String id);
	
	
	CourseMessage getCourse(String id);
	
	List<UserCourse> getCourseUsers(String id);
	
}
