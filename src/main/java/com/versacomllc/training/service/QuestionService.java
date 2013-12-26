package com.versacomllc.training.service;

import java.util.Set;

import com.versacomllc.training.message.UserQuestionAnswerMessage;

public interface QuestionService {

	
	 Set<UserQuestionAnswerMessage> getCourseQuestionAnswers(Long courseId);
		
}
