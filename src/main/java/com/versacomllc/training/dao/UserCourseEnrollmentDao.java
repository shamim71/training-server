package com.versacomllc.training.dao;


import java.util.List;

import com.versacomllc.training.domain.UserCourseEnrollment;

/**
 * This class is the DAO interface for Enrollment 
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface UserCourseEnrollmentDao extends GenericDao <UserCourseEnrollment, Long> {

	
	List<UserCourseEnrollment> getPendingEnrollments(Long userId);
	
	List<UserCourseEnrollment> getFinishedEnrollments(Long userId);
	
	List<UserCourseEnrollment> getAllUnfinishedEnrollment();
}
