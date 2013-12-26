package com.versacomllc.training.dao.impl;

import static java.lang.Boolean.TRUE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.versacomllc.training.dao.CourseTestDao;
import com.versacomllc.training.domain.CourseTest;
import com.versacomllc.training.message.request.EnrollmentTestFilter;

/**
 * This class is responsible for managing course object
 * 
 * @author Shamim Ahmmed
 * 
 */
@Repository
public class CourseTestDaoImpl extends GenericEntityDaoImpl<CourseTest, Long>
		implements CourseTestDao {

	@Override
	public List<CourseTest> getUserPassedTest(Long userId) {
		String clause = " t.userCourseEnrollment.employee.id = ?1 and t.passed = ?2";
		List<CourseTest> tests = loadByClause(clause, new Object[] { userId,
				TRUE });
		return tests;
	}

	@Override
	public List<CourseTest> getAllUserPassedTestByFilter(
			EnrollmentTestFilter filter) {
		List<Object> params = new ArrayList<Object>();

		
		String clause = "t.passed = ?1 ";
		params.add(TRUE);
		
		if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
			
			clause = clause + appendAnd(params) +  " t.userCourseEnrollment.employee.firstName=?"
					+ (params.size()+1) + " ";
			params.add(filter.getFirstName());
		}
		
		if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
			
			clause = clause + appendAnd(params) +  " t.userCourseEnrollment.employee.lastName=?"
					+ (params.size()+1) + " ";
			params.add(filter.getLastName());
		}
		if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
			
			clause = clause + appendAnd(params) +  " t.userCourseEnrollment.employee.email=?"
					+ (params.size()+1) + " ";
			params.add(filter.getEmail());
		}
		/** Add course filters */
		if (filter.getCourseIds() != null && filter.getCourseIds().size() > 0) {
			String courseIds = "";
			boolean isFirst = true;
			for(Long id: filter.getCourseIds()){
				if(isFirst){
					courseIds = courseIds + String.valueOf(id);
				}
				else{
					courseIds =  courseIds + ","+String.valueOf(id);
				}
				isFirst = false;
			}
			clause = clause + appendAnd(params) +  " t.userCourseEnrollment.courseEnrollment.course.id in ("+ courseIds +") ";
		}
		
		logger.debug("Executing JPA quyery: "+ clause);
		
		List<CourseTest> tests = loadByClause(clause, params.toArray());
		return tests;
	}
	private String appendAnd(List<Object> params){
		if(params.size() > 0){
			return " and ";
		}
		return "";
	}

}
