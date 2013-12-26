package com.versacomllc.training.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.versacomllc.training.dao.QuestionDao;
import com.versacomllc.training.domain.Question;



/**
 * This class is responsible for managing course object
 * 
 * @author Shamim Ahmmed
 * 
 */
@Repository
public class QuestionDaoImpl extends GenericEntityDaoImpl<Question, Long>
    implements QuestionDao {

	@Override
	public List<Question> findQuestionsByCourseId(Long courseId) {
	
		String clause = " t.course.id = ?1 ";
        
		List<Question> questions = loadByClause(clause, new Object[] { courseId });
        
		return questions;
	}

}
