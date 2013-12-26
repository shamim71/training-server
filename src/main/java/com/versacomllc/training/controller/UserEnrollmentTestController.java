package com.versacomllc.training.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.versacomllc.training.message.UserEnrollmentTestMessage;
import com.versacomllc.training.message.request.EnrollmentTestFilter;
import com.versacomllc.training.service.EnrollmentTestService;
import com.versacomllc.training.service.ReportingService;

@Controller
public class UserEnrollmentTestController extends
		AbstractApplicationWSController {

	@Autowired
	EnrollmentTestService enrollmentTestService;

	@Autowired
	ReportingService reportingService;

	@RequestMapping(value = "/enrollment/test/users/{id}", method = GET, produces = "application/json")
	public final @ResponseBody
	List<UserEnrollmentTestMessage> getUserEnrollmentTest(@PathVariable Long id) {

		logger.debug("Getting user finished enrollments : " + id);

		return enrollmentTestService.getEnrollmentTestRecords(id);
	}

	@RequestMapping(value = "/test/reports", method = POST, produces = "application/json", consumes = "application/json")
	public final @ResponseBody
	List<UserEnrollmentTestMessage> getAllUserEnrollmentTest(
			@RequestBody EnrollmentTestFilter filter) {

		logger.debug("Getting all user enrollment test details by custom filter : ");

		return enrollmentTestService.getEnrollmentTestRecordsByFilter(filter);
	}

	@RequestMapping(method = GET, value = "/enrollment/certificate/test/{id}")
	public void downloadCertificatetPdf(HttpServletResponse response,
			@PathVariable Long id) throws HttpMessageNotWritableException,
			IOException {

		logger.debug("Downloading certificate for test id,  " + id);

		// String message = appProperties.getProperty("pdf.text.en");
		ByteArrayOutputStream out = enrollmentTestService
				.getCertificateByTestId(id);

		/** Prepare response */
		response.setContentType("application/pdf");
		response.setContentLength(out.size());

		/** Send content to Browser */
		response.getOutputStream().write(out.toByteArray());
		response.getOutputStream().flush();

		/** Clean up */
		out.close();

	}

}
