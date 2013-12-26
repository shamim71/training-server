package com.versacomllc.training.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.versacomllc.training.common.ApplicationStatusCodes;
import com.versacomllc.training.dao.CourseDao;
import com.versacomllc.training.dao.EmployeeDao;
import com.versacomllc.training.domain.Course;
import com.versacomllc.training.domain.Employee;
import com.versacomllc.training.domain.Slide;
import com.versacomllc.training.message.CourseMessage;
import com.versacomllc.training.message.CourseMessage.SlideMessage;
import com.versacomllc.training.message.UserCourse;
import com.versacomllc.training.message.response.GenericResponse;
import com.versacomllc.training.service.CourseService;

/**
 * Service implementation for Course related data
 * 
 * @author Shamim Ahmmed
 * 
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	EmployeeDao empDao;
	
	@Autowired
	private Validator validator;

	@Autowired
	private Properties appProperties;
	
	@Override
	public List<CourseMessage> getAllCourses() {
		List<CourseMessage> list = new ArrayList<>();
		List<Course> courses = courseDao.loadAll();
		for (Course course : courses) {
			CourseMessage msg = new CourseMessage();
			msg.setId(course.getId());
			msg.setCode(course.getCourseCode());
			msg.setName(course.getName());
			msg.setDescription(course.getDescription());
			msg.setPassMark(course.getPassMark());
			msg.setMaxQuestions(course.getExamMaxQuestions());
			list.add(msg);
		}

		return list;
	}

	@Override
	public GenericResponse<String> addCourse(CourseMessage message) {

		Course course = new Course();
		course.setCourseCode(message.getCode());
		course.setName(message.getName());
		course.setDescription(message.getDescription());
		course.setExamMaxQuestions(message.getMaxQuestions());
		course.setPassMark(message.getPassMark());
		courseDao.persist(course);

		GenericResponse<String> response =new GenericResponse<String>("Course successfully created",
				ApplicationStatusCodes.SUCCESS_CREATED);

		return response;
	}

	@Override
	public GenericResponse<String> updateCourse(String id,CourseMessage message) {
		Course course = courseDao.loadById(Long.parseLong(id));
		if (course == null) {
			return new GenericResponse<String>("Course code not found",
					ApplicationStatusCodes.ERROR_NOTFOUND);
		}

		course.setCourseCode(message.getCode());
		course.setName(message.getName());
		course.setDescription(message.getDescription());
		course.setExamMaxQuestions(message.getMaxQuestions());
		course.setPassMark(message.getPassMark());
		
		courseDao.update(course);

		return new GenericResponse<String>("Course successfully updated",
				ApplicationStatusCodes.SUCCESS_UPDATED);
	}

	@Override
	public GenericResponse<String> removeCourse(String id) {

		Course course = courseDao.loadById(Long.parseLong(id));
		if (course == null) {
			return new GenericResponse<String>("Course code not found",
					ApplicationStatusCodes.ERROR_NOTFOUND);
		}

		courseDao.delete(course);

		return new GenericResponse<String>("Course successfully deleted",
				ApplicationStatusCodes.SUCCESS_REMOVED);
	}

	@Override
	public CourseMessage getCourse(String id) {
		Course course = courseDao.loadById(Long.parseLong(id));
		CourseMessage msg = new CourseMessage();
		if (course == null) {
			return msg;
		}
		msg.setId(course.getId());
		msg.setCode(course.getCourseCode());
		msg.setName(course.getName());
		msg.setDescription(course.getDescription());
		msg.setPassMark(course.getPassMark());
		msg.setMaxQuestions(course.getExamMaxQuestions());
		final String slideServerPath = appProperties.getProperty("content.server.root.path");
		//Load the slides
		Set<Slide> slides =  course.getSlides();
		msg.setSlides(new TreeSet<SlideMessage>( new Comparator<SlideMessage>() {

			@Override
			public int compare(SlideMessage o1, SlideMessage o2) {
				return o1.compareTo(o2);
			}
		}));
		for(Slide slide: slides){
			SlideMessage e = new SlideMessage();
			e.setContent(slide.getContent());
			e.setId(slide.getId());
			e.setSerial(slide.getSerial());
			e.setTitle(slide.getTitle());
			e.setUrl(slideServerPath + slide.getUrl());
			msg.getSlides().add(e);
		}
		
		return msg;
	}

	@Override
	public List<UserCourse> getCourseUsers(String id) {

		List<Employee> employees = empDao.loadAll();
		
		List<UserCourse> names = new ArrayList<UserCourse>();
		for(Employee emp: employees){
			UserCourse name = new UserCourse(emp);
			names.add(name);
		}
		return names;
	}

}
