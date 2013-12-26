package com.versacomllc.training.dao.impl;

import org.springframework.stereotype.Repository;

import com.versacomllc.training.dao.CourseTestDetailsDao;
import com.versacomllc.training.domain.CourseTestDetails;



/**
 * This class is responsible for managing course object
 * 
 * @author Shamim Ahmmed
 * 
 */
@Repository
public class CourseTestDetailsDaoImpl extends GenericEntityDaoImpl<CourseTestDetails, Long>
    implements CourseTestDetailsDao {

}
