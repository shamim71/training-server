package com.versacomllc.training.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.versacomllc.training.message.EnrollmentTestMessage;
import com.versacomllc.training.message.UserEnrollmentTestMessage;
import com.versacomllc.training.message.request.EnrollmentTestFilter;
import com.versacomllc.training.message.response.GenericResponse;

/**
 * Define the service interface for ....
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface EnrollmentTestService {

	GenericResponse<String> saveEnrollmentTestDetails(Long testId, EnrollmentTestMessage.TestDetails details);
	
	GenericResponse<String> startNewTest(Long enrollmentId);
	
	EnrollmentTestMessage getTestResult(Long testId);
	
	List<UserEnrollmentTestMessage> getEnrollmentTestRecords(Long userId);
	
	/**
	 * Generate the list of test records by custom filter
	 * 
	 * @param filter
	 * @return
	 */
	List<UserEnrollmentTestMessage> getEnrollmentTestRecordsByFilter(EnrollmentTestFilter filter);
	
	ByteArrayOutputStream getCertificateByTestId(Long testId);
}
