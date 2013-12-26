package com.versacomllc.training.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.versacomllc.training.message.AuthenticationResponse;
import com.versacomllc.training.message.UserName;
import com.versacomllc.training.message.request.ChangePasswordRequest;
import com.versacomllc.training.message.request.RegistrationRequest;
import com.versacomllc.training.message.response.GenericResponse;
import com.versacomllc.training.service.EmployeeRegistrationService;

@Controller
public class EmployeeController extends AbstractApplicationWSController {

	@Autowired
	EmployeeRegistrationService service;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public final void authenticate(final HttpServletResponse response,
			@RequestBody RegistrationRequest request)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Authenticating user with user id: " + request.getEmail()
				+ " password: " + request.getPassword());

		GenericResponse<AuthenticationResponse> responseBody = service
				.authenticateUser(request);

		this.writeAsJson(response, responseBody);
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public final void registerCustomer(final HttpServletResponse response,
			@RequestBody RegistrationRequest request)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Registering new employee ");

		GenericResponse<String> res = service.registerUser(request);

		this.writeAsJson(response, res);
	}

	@RequestMapping(value = "/changepassword/{id}", method = RequestMethod.POST)
	public final void resendPassword(final HttpServletResponse response,
			@PathVariable Long id, @RequestBody ChangePasswordRequest request)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Changing user password ");

		GenericResponse<String> res = service.changePassword(id, request);

		this.writeAsJson(response, res);
	}

	@RequestMapping(value = "/lostpassword", method = RequestMethod.POST, consumes = "application/json")
	public final void lostPassword(final HttpServletResponse response,
			@RequestBody RegistrationRequest request)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Rquesting new password ");

		GenericResponse<String> res = service.lostPassword(request);

		this.writeAsJson(response, res);
	}

	@RequestMapping(value = "/employees")
	public @ResponseBody
	List<UserName> listUser(final HttpServletResponse response,
			@RequestParam(value = "type", required = false) String type)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Getting all employees");
		List<UserName> names = new ArrayList<UserName>();

		if (type == null || type.isEmpty()) {
			names = this.service.userList();
		} else if (type.equals("user")) {
			names = this.service.nonAdminuserList();
		}
		Collections.sort(names);
		return names;
	}

	@RequestMapping(value = "/employees", method = RequestMethod.POST, consumes = "application/json")
	public final void addEmployee(final HttpServletResponse response,
			@RequestBody RegistrationRequest request)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Adding new employee");

		GenericResponse<String> res = service.addEmployee(request);
		this.writeAsJson(response, res);
	}

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.POST, consumes = "application/json")
	public final void updateEmployee(final HttpServletResponse response,
			@PathVariable Long id, @RequestBody RegistrationRequest request)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Updating employee");

		GenericResponse<String> res = service.updateEmployee(id, request);

		this.writeAsJson(response, res);
	}

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	RegistrationRequest getEmployee(final HttpServletResponse response,
			@PathVariable Long id) throws HttpMessageNotWritableException,
			IOException {

		logger.debug("Getting employee");
		
		return service.getEmployee(id);
	}
	
	@RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public void  deleteEmployee(final HttpServletResponse response,
			@PathVariable Long id) throws HttpMessageNotWritableException,
			IOException {

		logger.debug("Deleting employee");
		
		GenericResponse<String> res = service.deleteEmployee(id);
		

		this.writeAsJson(response, res);
	}	
}
