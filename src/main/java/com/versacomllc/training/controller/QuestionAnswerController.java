package com.versacomllc.training.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.versacomllc.training.message.UserQuestionAnswerMessage;
import com.versacomllc.training.service.QuestionService;

@Controller
public class QuestionAnswerController extends AbstractApplicationWSController {

	@Autowired
	QuestionService questionService;
	


	
	@RequestMapping(value = "/course/{courseId}/questions", method = RequestMethod.GET)
	public  @ResponseBody
	Set<UserQuestionAnswerMessage> getCourseQuestionAnswers(@PathVariable Long courseId)
			{

		logger.debug("Course questions  , "+ courseId);
		
		Set<UserQuestionAnswerMessage>  results = questionService.getCourseQuestionAnswers(courseId);

		return results;
	}	
	
	/*@RequestMapping(value = "/course/{id}/enrollments", method = RequestMethod.GET)
	public  @ResponseBody
	List<UserCourse> courseEnrollments(@PathVariable String id){

		logger.debug("Finding list of users enrolled under the course id , "+ id);
		
		
		List<UserCourse>	users = enrollmentService.getCourseEnrollment(id);
		return users;
	}	
	
	@RequestMapping(value = "/course/{id}/enrollments", method = RequestMethod.POST ,consumes="application/json")
	public void saveEnrollments(@PathVariable String id, @RequestBody CourseUsersMessage msg,final HttpServletResponse response) throws HttpMessageNotWritableException, IOException{

		logger.debug("Finding list of users enrolled under the course id , "+ id);
		
		enrollmentService.saveCourseEnrollment(id, msg.getUsers());
		
		GenericResponse<String>  res = new GenericResponse<String>("Course successfully updated",
				ApplicationStatusCodes.SUCCESS_UPDATED);

		this.writeAsJson(response, res);
	}
*/	
	
}
