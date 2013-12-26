package com.versacomllc.training.dao;



import com.versacomllc.training.domain.Course;

/**
 * This class is the DAO interface for Course 
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface CourseDao extends GenericDao <Course, Long> {


	Course findCourseByCode(String code);
	
}
