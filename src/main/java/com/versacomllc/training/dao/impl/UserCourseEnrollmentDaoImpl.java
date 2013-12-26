package com.versacomllc.training.dao.impl;

import static com.versacomllc.training.common.CommonConstants.TraningStatus.COMPLETED;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.versacomllc.training.dao.UserCourseEnrollmentDao;
import com.versacomllc.training.domain.UserCourseEnrollment;



/**
 * This class is responsible for managing course object
 * 
 * @author Shamim Ahmmed
 * 
 */
@Repository
public class UserCourseEnrollmentDaoImpl extends GenericEntityDaoImpl<UserCourseEnrollment, Long>
    implements UserCourseEnrollmentDao {

	@Override
	public List<UserCourseEnrollment> getPendingEnrollments(Long userId) {
		String clause = " t.employee.id = ?1 and t.status != ?2";
	    List<UserCourseEnrollment> enrollments = loadByClause(clause, new Object[] { userId, COMPLETED });
		return enrollments;
	}

	@Override
	public List<UserCourseEnrollment> getFinishedEnrollments(Long userId) {
		String clause = " t.employee.id = ?1 and t.status = ?2";
	    List<UserCourseEnrollment> enrollments = loadByClause(clause, new Object[] { userId, COMPLETED });
		return enrollments;
	}

	@Override
	public List<UserCourseEnrollment> getAllUnfinishedEnrollment() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		String clause = "  ?1  <= t.courseEnrollment.finishDate and t.status != ?2";
		
	    List<UserCourseEnrollment> enrollments = loadByClause(clause, new Object[] { cal.getTime(), COMPLETED });
	    
		return enrollments;
	}

}
