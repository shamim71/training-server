package com.versacomllc.training.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.versacomllc.training.common.ApplicationStatusCodes;
import com.versacomllc.training.message.CourseEnrollmentMessage;
import com.versacomllc.training.message.CourseEnrollmentMsg;

import com.versacomllc.training.message.response.GenericResponse;
import com.versacomllc.training.service.EnrollmentService;

@Controller
public class EnrollmentController extends AbstractApplicationWSController {

	@Autowired
	EnrollmentService enrollmentService;

	@RequestMapping(value = "/enrollments/user/{userId}", method = RequestMethod.GET)
	public @ResponseBody
	List<CourseEnrollmentMessage> userEnrollments(@PathVariable Long userId) {

		logger.debug("Finding list of user enrollments , " + userId);

		List<CourseEnrollmentMessage> results = enrollmentService
				.getUserEnrollments(userId);

		return results;
	}




	@RequestMapping(value = "/course/enrollments", method = RequestMethod.GET)
	public @ResponseBody
	List<CourseEnrollmentMsg> getCourseEnrollments() {

		logger.debug("Finding all course enrollments , ");

		List<CourseEnrollmentMsg> courseEnrollments = enrollmentService
				.getAllEnrollments();
		return courseEnrollments;
	}

	@RequestMapping(value = "/course/enrollments", method = RequestMethod.POST, consumes = "application/json")
	public void saveCourseEnrollment(@RequestBody CourseEnrollmentMsg msg,
			final HttpServletResponse response)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Saving course enrollments , ");

		String id = this.enrollmentService.addEnrollment(msg);
		GenericResponse<String> res = new GenericResponse<String>(id,
				ApplicationStatusCodes.SUCCESS_UPDATED);

		this.writeAsJson(response, res);
	}

	@RequestMapping(value = "/course/enrollments/{id}", method = RequestMethod.POST, consumes = "application/json")
	public void updateEnrollment(@PathVariable Long id,
			@RequestBody CourseEnrollmentMsg msg,
			final HttpServletResponse response)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Updating course enrollment with id  , " + id);

		enrollmentService.updateEnrollment(msg);

		GenericResponse<String> res = new GenericResponse<String>(
				"Enrollment successfully updated",
				ApplicationStatusCodes.SUCCESS_UPDATED);

		this.writeAsJson(response, res);
	}

	@RequestMapping(value = "/course/enrollments/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	CourseEnrollmentMsg getEnrollment(@PathVariable Long id)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Getting course enrollment with id  , " + id);

		CourseEnrollmentMsg msg = enrollmentService.getEnrollment(id);

		return msg;
	}
	
	@RequestMapping(value = "/course/enrollments/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public void deleteEnrollment(@PathVariable Long id, final HttpServletResponse response)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Deleting enrollment with id  , " + id);

		enrollmentService.deleteEnrollment(id);
		GenericResponse<String> res = new GenericResponse<String>(
				"Enrollment successfully deleted",
				ApplicationStatusCodes.SUCCESS_REMOVED);

		this.writeAsJson(response, res);
	}	
}
