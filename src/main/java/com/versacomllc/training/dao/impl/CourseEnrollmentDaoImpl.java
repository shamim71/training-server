package com.versacomllc.training.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.versacomllc.training.dao.CourseEnrollmentDao;
import com.versacomllc.training.domain.CourseEnrollment;



/**
 * This class is responsible for managing course object
 * 
 * @author Shamim Ahmmed
 * 
 */
@Repository
public class CourseEnrollmentDaoImpl extends GenericEntityDaoImpl<CourseEnrollment, Long>
    implements CourseEnrollmentDao {


	@Override
	public List<CourseEnrollment> findUserPendingEnrollments(Long userId) {
		String clause = " t.employee.id = ?1 ";
       // List<Enrollment> enrollments = loadByClause(clause, new Object[] { employeeId });
		
		return null;
	}
}
