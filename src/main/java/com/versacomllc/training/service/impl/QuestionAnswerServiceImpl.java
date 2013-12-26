package com.versacomllc.training.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.versacomllc.training.dao.CourseDao;
import com.versacomllc.training.dao.QuestionDao;
import com.versacomllc.training.domain.Course;
import com.versacomllc.training.domain.Question;
import com.versacomllc.training.message.UserQuestionAnswerMessage;
import com.versacomllc.training.service.QuestionService;

/**
 * Service implementation for Course related data
 * 
 * @author Shamim Ahmmed
 * 
 */
@Service
@Transactional
public class QuestionAnswerServiceImpl implements QuestionService {

	protected final Logger logger = Logger.getLogger(getClass());

	
	@Autowired
	CourseDao courseDao;

	@Autowired
	QuestionDao questionDao;
	
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	@Override
	public Set<UserQuestionAnswerMessage> getCourseQuestionAnswers(Long courseId) {
		Set<UserQuestionAnswerMessage> messages = new LinkedHashSet<>();
		Course course = courseDao.loadById(courseId);
		
		List<Question> questions = new ArrayList<>(course.getQuestions());
		
		if(questions.size() == 0){
			return messages;
		}
		int totalQuestions = course.getExamMaxQuestions();
		int min = 0;
		int max = questions.size() -1;
		
		Map<Integer, Integer> values = new HashMap<>();
		while(values.size() < totalQuestions){
			int val = randInt(min, max);
			if(!values.containsKey(val)){
				values.put(val, val);
			}
		}
		
		
		for(Entry<Integer, Integer> entry: values.entrySet()){
			Question question  = questions.get(entry.getKey());
			UserQuestionAnswerMessage msg = new UserQuestionAnswerMessage(question);
			messages.add(msg);
		}
		
		logger.debug("Total question selected "+ messages.size() + " out of "+ questions.size());
		
		return messages;
	}
	



}
