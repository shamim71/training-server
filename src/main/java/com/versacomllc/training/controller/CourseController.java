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

import com.versacomllc.training.message.CourseMessage;
import com.versacomllc.training.message.UserCourse;
import com.versacomllc.training.message.response.GenericResponse;
import com.versacomllc.training.service.CourseService;

@Controller
public class CourseController extends AbstractApplicationWSController {

	@Autowired
	CourseService courseService;

	@RequestMapping(method = RequestMethod.GET, value = "/courses", produces="application/json")
	public @ResponseBody
	List<CourseMessage> getAllCourse() {

		logger.debug("Getting list of courses...");
		
		return courseService.getAllCourses();
	}

	
	@RequestMapping(value = "/courses", method = RequestMethod.POST)
	public final void addNewCourse(final HttpServletResponse response,
			@RequestBody CourseMessage request)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Creating new course ");
		
		GenericResponse<String>  res = courseService.addCourse(request);

		this.writeAsJson(response, res);
	}
	
	@RequestMapping(value = "/courses/{id}", method = RequestMethod.POST)
	public final void updateCourse(final HttpServletResponse response, @PathVariable String id,
			@RequestBody CourseMessage request)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Updating course with id, "+ id);
		
		GenericResponse<String>  res = courseService.updateCourse(id,request);

		this.writeAsJson(response, res);
	}	
	
	@RequestMapping(value = "/courses/{id}", method = RequestMethod.DELETE)
	public final void deleteCourse(final HttpServletResponse response,
			@PathVariable String id)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Deleting course with id: "+ id);
		
		GenericResponse<String>  res = courseService.removeCourse(id);

		this.writeAsJson(response, res);
	}	
	
	@RequestMapping(value = "/courses/{id}", method = RequestMethod.GET, produces="application/json")
	public final @ResponseBody CourseMessage getCourseDetails(@PathVariable String id )
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Getting course with id: "+ id);
		
		return courseService.getCourse(id);
	}		


	@RequestMapping(value = "/courses/{id}/users", method = RequestMethod.GET, produces="application/json")
	public final @ResponseBody List<UserCourse> getAssignedUserCourse(@PathVariable String id )
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Getting course with id: "+ id);
		
		return null;
	}	
	
}
