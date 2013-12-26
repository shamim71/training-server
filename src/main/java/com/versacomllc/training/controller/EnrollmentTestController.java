package com.versacomllc.training.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.versacomllc.training.message.EnrollmentTestMessage;
import com.versacomllc.training.message.response.GenericResponse;
import com.versacomllc.training.service.EnrollmentTestService;

@Controller
public class EnrollmentTestController extends AbstractApplicationWSController {

	@Autowired
	EnrollmentTestService enrollmentTestService;

	@RequestMapping(value = "/tests", method = RequestMethod.POST)
	public final void  startNewTest(final HttpServletResponse response,
			@RequestBody EnrollmentTestMessage request)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Starting a new enrollment test"+ request.getEnrollmentId());
		
		GenericResponse<String> message  = enrollmentTestService.startNewTest(request.getEnrollmentId());
		this.writeAsJson(response, message);
	}
	
	@RequestMapping(value = "/tests/{id}", method = RequestMethod.POST)
	public final void updateCourse(final HttpServletResponse response, @PathVariable Long id,
			@RequestBody EnrollmentTestMessage.TestDetails details)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Updating course with id, "+ id);
		

		GenericResponse<String> message = enrollmentTestService.saveEnrollmentTestDetails(id, details);
		
		this.writeAsJson(response, message);
	}	

	
	@RequestMapping(value = "/tests/{id}", method = RequestMethod.GET, produces="application/json")
	public final @ResponseBody EnrollmentTestMessage getCourseDetails(@PathVariable Long id ){

		logger.debug("Getting course with id: "+ id);
		
		return enrollmentTestService.getTestResult(id);
	}		

	
	
}
