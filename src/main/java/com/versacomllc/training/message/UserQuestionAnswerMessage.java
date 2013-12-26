package com.versacomllc.training.message;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.versacomllc.training.domain.Question;
import com.versacomllc.training.domain.QuestionAnswer;

public class UserQuestionAnswerMessage {

	private Long id;
	
	private String name;
	
	private Long courseId;
	
	private Long userAnswer;
	
	private Set<Answer> answers;
	
	public UserQuestionAnswerMessage() {
	}
	
	public UserQuestionAnswerMessage(Question question){
		
		this.id = question.getId();
		this.name = question.getName();
		this.courseId = question.getCourse().getId();
		this.answers = new TreeSet<Answer>(new Comparator<Answer>() {

			@Override
			public int compare(Answer o1, Answer o2) {
				return o1.answer.compareTo(o2.answer);
			}
		});
		
		for(QuestionAnswer answer: question.getQuestionAnswers()){
			Answer ans = new Answer();
			ans.correct = answer.getIsAnswer();
			ans.id = answer.getId();
			ans.answer = answer.getAnswer();
			this.answers.add(ans);
		}
	}
	
	public static class Answer implements Comparable<Answer>{
		
		private Long id;
		
		private String answer;
		
		private Boolean correct;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Boolean getCorrect() {
			return correct;
		}

		public void setCorrect(Boolean correct) {
			this.correct = correct;
		}

		public String getAnswer() {
			return answer;
		}

		public void setAnswer(String answer) {
			this.answer = answer;
		}

		@Override
		public int compareTo(Answer o) {
			return this.answer.compareTo(o.answer);
		}
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Long getCourseId() {
		return courseId;
	}


	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}


	public Long getUserAnswer() {
		return userAnswer;
	}


	public void setUserAnswer(Long userAnswer) {
		this.userAnswer = userAnswer;
	}


	public Set<Answer> getAnswers() {
		return answers;
	}


	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}



	
}
