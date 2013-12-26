package com.versacomllc.training.dao;


import java.util.List;

import com.versacomllc.training.domain.Question;

/**
 * This class is the DAO interface for Question 
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface QuestionDao extends GenericDao <Question, Long> {


	List<Question> findQuestionsByCourseId(Long courseId);
	
	
}
