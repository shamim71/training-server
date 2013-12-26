package com.versacomllc.training.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.versacomllc.training.dao.CourseDao;
import com.versacomllc.training.domain.Course;



/**
 * This class is responsible for managing course object
 * 
 * @author Shamim Ahmmed
 * 
 */
@Repository
public class CourseDaoImpl extends GenericEntityDaoImpl<Course, Long>
    implements CourseDao {

	@Override
	public Course findCourseByCode(String code) {
		String clause = " t.courseCode = ?1 ";
        List<Course> courses = loadByClause(clause, new Object[] { code });
        if(courses == null || courses.size() == 0){
        	return null;
        }
		return courses.get(0);
	}


}
