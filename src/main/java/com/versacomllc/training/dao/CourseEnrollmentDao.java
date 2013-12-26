package com.versacomllc.training.dao;


import java.util.List;

import com.versacomllc.training.domain.CourseEnrollment;

/**
 * This class is the DAO interface for Enrollment 
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface CourseEnrollmentDao extends GenericDao <CourseEnrollment, Long> {


	List<CourseEnrollment> findUserPendingEnrollments(Long userId);
	
	
	
}
