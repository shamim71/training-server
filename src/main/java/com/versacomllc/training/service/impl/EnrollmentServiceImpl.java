package com.versacomllc.training.service.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.versacomllc.training.common.CommonConstants;
import com.versacomllc.training.dao.CourseDao;
import com.versacomllc.training.dao.CourseEnrollmentDao;
import com.versacomllc.training.dao.EmployeeDao;
import com.versacomllc.training.dao.UserCourseEnrollmentDao;
import com.versacomllc.training.domain.Course;
import com.versacomllc.training.domain.CourseEnrollment;
import com.versacomllc.training.domain.Employee;
import com.versacomllc.training.domain.UserCourseEnrollment;
import com.versacomllc.training.exception.ServiceException;
import com.versacomllc.training.message.CourseEnrollmentMessage;
import com.versacomllc.training.message.CourseEnrollmentMsg;
import com.versacomllc.training.message.CourseEnrollmentMsg.UserCourseEnrollmentMsg;
import com.versacomllc.training.service.AbstractPortalService;
import com.versacomllc.training.service.CourseService;
import com.versacomllc.training.service.EnrollmentService;

/**
 * Service implementation for Course related data
 * 
 * @author Shamim Ahmmed
 * 
 */
@Service
@Transactional
public class EnrollmentServiceImpl extends AbstractPortalService implements
		EnrollmentService {


	@Autowired
	CourseService courseService;

	@Autowired
	CourseDao courseDao;

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	CourseEnrollmentDao courseEnrollmentDao;
	
	@Autowired
	UserCourseEnrollmentDao userCourseEnrollmentDao;

	/** Validator declaration */
	@Autowired
	Validator validator;

	@Override
	public List<CourseEnrollmentMessage> getUserEnrollments(Long userId) {
		
	 List<UserCourseEnrollment> userEnrollments = 	userCourseEnrollmentDao.getPendingEnrollments(userId);
		
		List<CourseEnrollmentMessage> msgs = new ArrayList<CourseEnrollmentMessage>();
		for (UserCourseEnrollment en : userEnrollments) {
			msgs.add(new CourseEnrollmentMessage(en));
		}
		return msgs;
	}



	@Override
	public List<CourseEnrollmentMsg> getAllEnrollments() {

		List<CourseEnrollment> courseEnrollments = courseEnrollmentDao
				.loadAll();
		List<CourseEnrollmentMsg> msgs = new ArrayList<CourseEnrollmentMsg>();
		for (CourseEnrollment enr : courseEnrollments) {
			CourseEnrollmentMsg msg = new CourseEnrollmentMsg(enr);
			msgs.add(msg);
		}
		return msgs;
	}
	  private void validateEnrollment(CourseEnrollmentMsg enrollment) {
		    Set<ConstraintViolation<CourseEnrollmentMsg>> constraintViolations = this.validator
		        .validate(enrollment);
		    
		    for (ConstraintViolation<CourseEnrollmentMsg> con : constraintViolations) {
		      String message = con.getMessage();
		      throw new ServiceException(message, "400", HttpStatus.BAD_REQUEST.value());
		    }
		  }
	  
	@Override
	public String addEnrollment(CourseEnrollmentMsg msg) {

		Course course = this.courseDao.loadById(msg.getCourseId());
		if (course == null) {
			 throw new ServiceException("No course found", "404", HttpStatus.NOT_FOUND.value());
		}
		
		validateEnrollment(msg);
		
		CourseEnrollment enrollment = new CourseEnrollment();
		enrollment.setCourse(course);
		enrollment.setDescription(msg.getDescription());
		enrollment.setName(msg.getName());

		try {
			enrollment.setEnrollmentDate(CommonConstants.US_DATEFORMAT
					.parse(msg.getStart()));
			enrollment.setFinishDate(CommonConstants.US_DATEFORMAT.parse(msg
					.getEnd()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.courseEnrollmentDao.persist(enrollment);
		String enrollmentId = String.valueOf(enrollment.getId());

		return enrollmentId;
	}

	@Override
	public String updateEnrollment(CourseEnrollmentMsg msg) {

		CourseEnrollment enrollment = this.courseEnrollmentDao.loadById(msg
				.getId());
		if (enrollment == null) {
			 throw new ServiceException("No training course found", "404", HttpStatus.NOT_FOUND.value());
		}
		enrollment.setDescription(msg.getDescription());
		enrollment.setName(msg.getName());
		
		Course course = courseDao.loadById(msg.getCourseId());
		if(course != null){
			enrollment.setCourse(course);
		}

		try {
			enrollment.setEnrollmentDate(CommonConstants.US_DATEFORMAT
					.parse(msg.getStart()));
			enrollment.setFinishDate(CommonConstants.US_DATEFORMAT.parse(msg
					.getEnd()));
		} catch (ParseException e) {
			throw new ServiceException("Invalid enrollment date format", "400", 400);
		}
		
		
		this.courseEnrollmentDao.update(enrollment);
		
		Set<CourseEnrollmentMsg.UserCourseEnrollmentMsg> enrolledUsers = msg.getUsers();
		for(UserCourseEnrollmentMsg ucr: enrolledUsers){
			
			UserCourseEnrollment entity = new UserCourseEnrollment();
			Employee emp = employeeDao.loadById(ucr.getUserId());
			boolean alreadyEnrolled = isUserAlreadyEnrolled(enrollment, ucr.getUserId());
			if(emp != null && !alreadyEnrolled){
				entity.setEmployee(emp);
				entity.setStatus(CommonConstants.TraningStatus.PENDING);
				entity.setCourseEnrollment(enrollment);
				userCourseEnrollmentDao.persist(entity);
				//Sending email notification
				sendEnrollmentEmail(course.getName(), emp);
			}

		}
		
		String enrollmentId = String.valueOf(enrollment.getId());

		return enrollmentId;
	}
	private boolean isUserAlreadyEnrolled(CourseEnrollment enrollment, Long userId){
		
		Set<UserCourseEnrollment> list =  enrollment.getUserCourseEnrollments();
		for(UserCourseEnrollment enrl: list){
			if(enrl.getEmployee().getId().equals(userId)){
				return true;
			}
		}
		return false;
	}
	private void sendEnrollmentEmail(String course, Employee emp){
		logger.debug("Sending Email notification...");
		String emailSubject = "Training enrollment notification for course: "
				+ course;
		String name = emp.getFirstName() + " " + emp.getLastName();
		String body = "Please visit Versacom training portal web site and complete the course,"
				+ course;
		try {
			this.sendEmail(emp.getEmail(), emailSubject, body, name);
		} catch (Exception e) {
			logger.error("Error while sending email notification ::"
					+ e.getMessage());
		}
	}

	@Override
	public String deleteEnrollment(Long id) {

		CourseEnrollment enrollment = this.courseEnrollmentDao.loadById(id);

		if (enrollment == null) {
			 throw new ServiceException("No training course found", "404", HttpStatus.NOT_FOUND.value());
		}
		this.courseEnrollmentDao.delete(enrollment);

		return String.valueOf(id);
	}

	@Override
	public CourseEnrollmentMsg getEnrollment(Long id) {
		CourseEnrollment enrollment = this.courseEnrollmentDao.loadById(id);

		if (enrollment == null) {
			 throw new ServiceException("No training course found", "404", HttpStatus.NOT_FOUND.value());
		}
		
		CourseEnrollmentMsg msg = new CourseEnrollmentMsg(enrollment);
		return msg;
	}


}
