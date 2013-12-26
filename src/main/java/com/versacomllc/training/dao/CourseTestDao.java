package com.versacomllc.training.dao;


import java.util.List;

import com.versacomllc.training.domain.CourseTest;
import com.versacomllc.training.message.request.EnrollmentTestFilter;

/**
 * This class is the DAO interface for CourseTest 
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface CourseTestDao extends GenericDao <CourseTest, Long> {

	
	List<CourseTest> getUserPassedTest(Long userId);
	
	List<CourseTest> getAllUserPassedTestByFilter(EnrollmentTestFilter filter);
	
}
