package com.versacomllc.training.controller.customer;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.versacomllc.training.controller.SecurityAwareWSController;
import com.versacomllc.training.service.EmployeeRegistrationService;

/**
 * Controller for operations on customers.
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("/customer")
public class EmployeeRegistrationController extends SecurityAwareWSController {

	@Autowired
	private EmployeeRegistrationService userRegistrationService;

	/**
	 * Register a new customer without phone number.
	 * 
	 * @param response
	 *            response message
	 * @throws HttpMessageNotWritableException
	 *             if fail to write message response
	 * @throws IOException
	 *             if fail to write on response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public final void registerCustomer(final HttpServletResponse response)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Creating new customer");

		/*
		 * GenericResponse<CustomerRegistrationResponse> responseBody =
		 * this.userRegistrationService .registerUser();
		 * 
		 * logger.debug("Customer created with ID " +
		 * responseBody.getResult().getCustomerId());
		 * 
		 * this.writeObjectAsJsonString(response, responseBody);
		 */
	}

	/**
	 * Remove the customer identified by the provided ID.
	 * 
	 * @param response
	 *            response message
	 * @param customerId
	 *            customer to delete
	 * @throws HttpMessageNotWritableException
	 *             if fail to write message response
	 * @throws IOException
	 *             if fail to write on response
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{customerId}")
	public final void deleteCustomer(final HttpServletResponse response,
			@PathVariable final String customerId)
			throws HttpMessageNotWritableException, IOException {

		this.validateCustomer(customerId);

		logger.debug("Removing customer " + customerId);

		// this.writeObjectAsJsonString(response, responseBody);
	}

}
