package com.versacomllc.training.service.impl;

import static com.versacomllc.training.common.CommonConstants.TraningStatus.COMPLETED;
import static com.versacomllc.training.common.CommonConstants.TraningStatus.FAILED;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.versacomllc.training.common.ApplicationStatusCodes;
import com.versacomllc.training.common.CommonConstants;
import com.versacomllc.training.dao.CourseTestDao;
import com.versacomllc.training.dao.CourseTestDetailsDao;
import com.versacomllc.training.dao.QuestionDao;
import com.versacomllc.training.dao.UserCourseEnrollmentDao;
import com.versacomllc.training.domain.Course;
import com.versacomllc.training.domain.CourseTest;
import com.versacomllc.training.domain.CourseTestDetails;
import com.versacomllc.training.domain.Employee;
import com.versacomllc.training.domain.Question;
import com.versacomllc.training.domain.QuestionAnswer;
import com.versacomllc.training.domain.UserCourseEnrollment;
import com.versacomllc.training.message.EnrollmentTestMessage;
import com.versacomllc.training.message.EnrollmentTestMessage.TestDetails;
import com.versacomllc.training.message.UserEnrollmentTestMessage;
import com.versacomllc.training.message.request.EnrollmentTestFilter;
import com.versacomllc.training.message.response.GenericResponse;
import com.versacomllc.training.service.EnrollmentTestService;
import com.versacomllc.training.service.ReportingService;
import com.versacomllc.training.util.Identifier;

/**
 * Service implementation for
 * 
 * @author Shamim Ahmmed
 * 
 */
@Service
@Transactional
public class EnrollmentTestServiceImpl implements EnrollmentTestService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	UserCourseEnrollmentDao userCourseEnrollmentDao;

	@Autowired
	CourseTestDao courseTestDao;

	@Autowired
	QuestionDao questionDao;

	@Autowired
	CourseTestDetailsDao courseTestDetailsDao;

	@Autowired
	ReportingService reportingService;
	
	@Override
	public GenericResponse<String> saveEnrollmentTestDetails(Long testId,
			TestDetails details) {

		CourseTest test = courseTestDao.loadById(testId);
		if (test == null) {
			return new GenericResponse<String>("Test is not started yet.",
					ApplicationStatusCodes.ERROR_NOTFOUND);
		}

		Question question = questionDao.loadById(details.getQuestionId());
		Set<QuestionAnswer> answers = question.getQuestionAnswers();
		QuestionAnswer userAnswer = null;
		Boolean correct = false;
		for (QuestionAnswer ans : answers) {
			if (ans.getId().equals(details.getUserAnswerId())) {
				userAnswer = ans;
			}
			if (ans.getIsAnswer()
					&& ans.getId().equals(details.getUserAnswerId())) {
				correct = true;
			}
		}

		CourseTestDetails userDetailsAnswer = new CourseTestDetails();
		userDetailsAnswer.setCourseTest(test);
		userDetailsAnswer.setUserAnswer(userAnswer);
		userDetailsAnswer.setQuestion(question);
		userDetailsAnswer.setCorrect(correct);

		courseTestDetailsDao.persist(userDetailsAnswer);

		if (details.getEndOfTest() != null && details.getEndOfTest()) {

			Set<CourseTestDetails> allanswers = test.getCourseTestDetails();

			int totalCorrect = 0;
			for (CourseTestDetails dtl : allanswers) {
				if (dtl.getCorrect()) {
					totalCorrect = totalCorrect + 1;
				}
				logger.debug("Pass mark: " + dtl.getCorrect() + "  ----> ");
			}

			test.setEndTime(new Date());
			test.setCompleted(true);
			test.setTotalAnswers(allanswers.size());
			test.setCorrectAnswers(totalCorrect);
			int markInPercent = (100 * totalCorrect) / allanswers.size();

			UserCourseEnrollment enrollment = test.getUserCourseEnrollment();

			Course course = enrollment.getCourseEnrollment().getCourse();

			logger.debug("Pass mark: " + course.getPassMark() + " Got ----> "
					+ markInPercent);

			if (markInPercent >= course.getPassMark()) {
				test.setPassed(true);
				enrollment.setStatus(COMPLETED);
			} else {
				enrollment.setStatus(FAILED);
			}

		}

		courseTestDao.update(test);

		GenericResponse<String> response = new GenericResponse<String>(
				String.valueOf(userDetailsAnswer.getId()),
				ApplicationStatusCodes.SUCCESS_CREATED);

		return response;
	}

	@Override
	public GenericResponse<String> startNewTest(Long enrollmentId) {

		UserCourseEnrollment enrollment = userCourseEnrollmentDao
				.loadById(enrollmentId);
		if (enrollment == null) {
			return new GenericResponse<String>("Course enrollment not found",
					ApplicationStatusCodes.ERROR_NOTFOUND);
		}

		Course course = enrollment.getCourseEnrollment().getCourse();
		int maxQuestions = course.getExamMaxQuestions();
		CourseTest test = new CourseTest();
		test.setStartTime(new Date());
		test.setPassed(false);
		test.setCompleted(false);
		test.setCode("VC-"+Identifier.next(10));
		test.setTotalQuestions(maxQuestions);

		test.setUserCourseEnrollment(enrollment);

		courseTestDao.persist(test);
		String testId = String.valueOf(test.getId());

		GenericResponse<String> response = new GenericResponse<String>(testId,
				ApplicationStatusCodes.SUCCESS_CREATED);

		return response;
	}

	@Override
	public EnrollmentTestMessage getTestResult(Long enrollmentId) {

		return null;
	}

	@Override
	public List<UserEnrollmentTestMessage> getEnrollmentTestRecords(Long userId) {

		List<CourseTest> userPassedTests = this.courseTestDao
				.getUserPassedTest(userId);

		List<UserEnrollmentTestMessage> records = new ArrayList<UserEnrollmentTestMessage>();
		for(CourseTest test: userPassedTests){
			UserEnrollmentTestMessage msg = new UserEnrollmentTestMessage(test);
			records.add(msg);
		}
		return records;
	}

	@Override
	public ByteArrayOutputStream getCertificateByTestId(Long testId) {

		CourseTest test =  courseTestDao.loadById(testId);
		if(test == null){
			return null;
		}
		Employee emp = test.getUserCourseEnrollment().getEmployee();
		Course course = test.getUserCourseEnrollment().getCourseEnrollment().getCourse();
		String name = emp.getFirstName() + " "+ emp.getLastName();
		String courseName = course.getName();
		String date = CommonConstants.US_DATEFORMAT.format(test.getEndTime());
		String serial = test.getCode();
		ByteArrayOutputStream out = this.reportingService.getCertificatePDFAsStream(name,courseName, date, serial);
		return out;
	}

	@Override
	public List<UserEnrollmentTestMessage> getEnrollmentTestRecordsByFilter(
			EnrollmentTestFilter filter) {
		List<CourseTest> userPassedTests = this.courseTestDao
				.getAllUserPassedTestByFilter(filter);

		List<UserEnrollmentTestMessage> records = new ArrayList<UserEnrollmentTestMessage>();
		for(CourseTest test: userPassedTests){
			UserEnrollmentTestMessage msg = new UserEnrollmentTestMessage(test);
			records.add(msg);
		}
		return records;
	}

}
