package com.versacomllc.training.service;

import java.util.List;

import com.versacomllc.training.message.AuthenticationResponse;
import com.versacomllc.training.message.UserName;
import com.versacomllc.training.message.request.ChangePasswordRequest;
import com.versacomllc.training.message.request.RegistrationRequest;
import com.versacomllc.training.message.response.GenericResponse;

/**
 * Define the service interface for customer registration management.
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface EmployeeRegistrationService {

	/**
	 * Register a new user.
	 * 
	 * @return CustomerRegistrationResponse
	 */
	GenericResponse<String> registerUser(RegistrationRequest request);

	
	GenericResponse<String> lostPassword(RegistrationRequest request);
	
	/**
	 * Change user password
	 * 
	 * @param userId
	 * @return
	 */
	GenericResponse<String> changePassword(Long userId, ChangePasswordRequest request);

	/**
	 * @param request
	 * @return
	 */
	GenericResponse<AuthenticationResponse> authenticateUser(
			RegistrationRequest request);

	/**
	 * @return
	 */
	List<UserName> userList();

	List<UserName> nonAdminuserList();
	
	/**
	 * Add a new employee 
	 * 
	 * @param name
	 */
	GenericResponse<String> addEmployee(RegistrationRequest user);
	
	GenericResponse<String> updateEmployee(Long id, RegistrationRequest user);
	

	RegistrationRequest getEmployee(Long id);
	
	
	GenericResponse<String> deleteEmployee(Long id);
	
}
