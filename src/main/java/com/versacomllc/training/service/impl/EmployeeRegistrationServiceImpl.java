package com.versacomllc.training.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.versacomllc.training.common.ApplicationStatusCodes;
import com.versacomllc.training.dao.EmployeeDao;
import com.versacomllc.training.domain.Employee;
import com.versacomllc.training.exception.ServiceException;
import com.versacomllc.training.message.AuthenticationResponse;
import com.versacomllc.training.message.UserName;
import com.versacomllc.training.message.request.ChangePasswordRequest;
import com.versacomllc.training.message.request.RegistrationRequest;
import com.versacomllc.training.message.response.GenericResponse;
import com.versacomllc.training.service.AbstractPortalService;
import com.versacomllc.training.service.EmployeeRegistrationService;
import com.versacomllc.training.util.CryptographicUtility;
import com.versacomllc.training.util.Identifier;

/**
 * Service implementation for customer registration related operation.
 * 
 * @author Shamim Ahmmed
 * 
 */
@Service
@Transactional
public class EmployeeRegistrationServiceImpl extends AbstractPortalService
		implements EmployeeRegistrationService {

	@Autowired
	private EmployeeDao customerDao;

	@Autowired
	private Validator validator;


	/**
	 * Validate partial passcode message (only customerid/pin).
	 * 
	 * @param message
	 *            PasscodeRequestMessage object
	 */
	private String validateRegistrationMessage(final RegistrationRequest message) {
		Set<ConstraintViolation<RegistrationRequest>> constraintViolations = this.validator
				.validate(message);
		for (ConstraintViolation<RegistrationRequest> con : constraintViolations) {
			String msg = con.getMessage();

			return msg;
		}
		return null;
	}

	@Override
	public GenericResponse<String> registerUser(RegistrationRequest request) {

		String msg = validateRegistrationMessage(request);
		if (msg != null && !msg.isEmpty()) {
			return new GenericResponse<String>(msg,
					ApplicationStatusCodes.INVALID_MESSAGE);
		}

		// Verify if the email address exist
		Employee emp = this.customerDao.findEmployeeByEmail(request.getEmail());
		if (emp != null) {
			return new GenericResponse<String>(
					"User already registered with this email, "
							+ request.getEmail(),
					ApplicationStatusCodes.INVALID_MESSAGE);
		}
		Employee employee = new Employee();
		employee.setEmail(request.getEmail());
		employee.setFirstName(request.getFirstName());
		employee.setLastName(request.getLastName());
		employee.setPhone(request.getPhone());
		employee.setAddress(request.getAddress());

		employee.setRole("user");

		/** 32 bin random number */
		byte[] sharedSecret = CryptographicUtility.generateSecureRandom();

		byte[] hashedSharedSecret = CryptographicUtility.encode(request
				.getPassword().toCharArray(), sharedSecret);

		if (hashedSharedSecret == null) {
			return new GenericResponse<String>(
					"Error while encrypting password",
					ApplicationStatusCodes.INVALID_MESSAGE);
		}
		String encodedSharedSecreet = Base64
				.encodeBase64String(hashedSharedSecret);
		String encodedSalt = Base64.encodeBase64String(sharedSecret);

		employee.setSalt(encodedSalt);
		employee.setHashedPassword(encodedSharedSecreet);

		this.customerDao.persist(employee);

		// Sending confirmation email

		final String subject = this.getText("portal.registration.sub");
		final String body = this.getText("portal.registration.body", request.getPassword());
		final String name = employee.getFirstName() + " "
				+ employee.getLastName();

		try {
			this.sendEmail(employee.getEmail(), subject, body, name);
		} catch (Exception e) {
			logger.error("Error while sending registration confirmation");
		}

		return new GenericResponse<String>("Record created",
				ApplicationStatusCodes.SUCCESS_CREATED);

	}

	@Override
	public GenericResponse<AuthenticationResponse> authenticateUser(
			RegistrationRequest request) {

		Employee employee = this.customerDao.findEmployeeByEmail(request
				.getEmail());

		if (employee == null) {
			return new GenericResponse<AuthenticationResponse>(
					new AuthenticationResponse(),
					ApplicationStatusCodes.NOT_AUTHORIZED);
		}

		byte[] sharedSecret = Base64.decodeBase64(employee.getSalt());

		byte[] hashedSharedSecret = CryptographicUtility.encode(request
				.getPassword().toCharArray(), sharedSecret);

		if (hashedSharedSecret == null) {
			return new GenericResponse<AuthenticationResponse>(
					new AuthenticationResponse(),
					ApplicationStatusCodes.NOT_AUTHORIZED);
		}
		String encodedSharedSecreet = Base64
				.encodeBase64String(hashedSharedSecret);
		logger.debug("provided: " + encodedSharedSecreet + " database "
				+ employee.getHashedPassword());
		if (!employee.getHashedPassword().equals(encodedSharedSecreet)) {
			return new GenericResponse<AuthenticationResponse>(
					new AuthenticationResponse(),
					ApplicationStatusCodes.NOT_AUTHORIZED);
		}

		AuthenticationResponse resp = new AuthenticationResponse();
		resp.setId(String.valueOf(employee.getId()));
		resp.setFirstName(employee.getFirstName());
		resp.setLastName(employee.getLastName());
		resp.setEmail(employee.getEmail());
		resp.setRole(employee.getRole());

		return new GenericResponse<AuthenticationResponse>(resp,
				ApplicationStatusCodes.SUCCESS_UPDATED);
	}

	@Override
	public List<UserName> userList() {

		List<Employee> employees = this.customerDao.loadAll();

		List<UserName> names = new ArrayList<>();
		for (Employee emp : employees) {
			UserName name = new UserName(emp);
			names.add(name);
		}
		return names;
	}

	@Override
	public List<UserName> nonAdminuserList() {
		List<Employee> employees = this.customerDao.findAllNonAdminUser();

		List<UserName> names = new ArrayList<>();
		for (Employee emp : employees) {
			UserName name = new UserName(emp);
			names.add(name);
		}
		return names;
	}
	private String validateChangePasswordMessage(final ChangePasswordRequest message) {
		Set<ConstraintViolation<ChangePasswordRequest>> constraintViolations = this.validator
				.validate(message);
		for (ConstraintViolation<ChangePasswordRequest> con : constraintViolations) {
			String msg = con.getMessage();

			return msg;
		}
		return null;
	}
	
	@Override
	public GenericResponse<String> changePassword(Long userId,
			ChangePasswordRequest request) {

		String msg = validateChangePasswordMessage(request);
		if (msg != null && !msg.isEmpty()) {
			return new GenericResponse<String>(msg,
					ApplicationStatusCodes.INVALID_MESSAGE);
		}

		
		if (!request.getId().equals(userId)) {
			return new GenericResponse<String>("Access denied",
					ApplicationStatusCodes.INVALID_REQUEST);
		}
		Employee employee = this.customerDao.loadById(userId);

		if (employee == null) {
			return new GenericResponse<String>("Access denied. No user exist",
					ApplicationStatusCodes.INVALID_REQUEST);
		}

		byte[] sharedSecret = Base64.decodeBase64(employee.getSalt());

		byte[] hashedSharedSecret = CryptographicUtility.encode(request
				.getOldPassword().toCharArray(), sharedSecret);
		String encodedSharedSecreet = Base64
				.encodeBase64String(hashedSharedSecret);

		logger.debug("provided: " + encodedSharedSecreet + " database "
				+ employee.getHashedPassword());
		if (!employee.getHashedPassword().equals(encodedSharedSecreet)) {
			return new GenericResponse<String>("Old password invalid",
					ApplicationStatusCodes.INVALID_REQUEST);
		}
		byte[] hashedSharedSecretNew = CryptographicUtility.encode(request
				.getNewPassword().toCharArray(), sharedSecret);
		String encodedSharedSecreetNew = Base64
				.encodeBase64String(hashedSharedSecretNew);
		
		employee.setHashedPassword(encodedSharedSecreetNew);
		
		this.customerDao.update(employee);
		
		//Send confirmation email.
		try {
			this.sendEmail(employee.getEmail(), "Password change notification", "Your passowrd has been changed", employee.getFirstName() + " "+ employee.getLastName());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new GenericResponse<String>("Password has been changed",
				ApplicationStatusCodes.SUCCESS_UPDATED);
	}

	@Override
	public GenericResponse<String> lostPassword(RegistrationRequest request) {
		
		String randomPass = Identifier.next(6);
		Employee employee = this.customerDao.findEmployeeByEmail(request
				.getEmail());

		if (employee == null) {
			return new GenericResponse<String>(
					"Invalid email address",
					ApplicationStatusCodes.INVALID_REQUEST);
		}
		
		
		byte[] sharedSecret = Base64.decodeBase64(employee.getSalt());

		byte[] hashedSharedSecret = CryptographicUtility.encode(randomPass.toCharArray(), sharedSecret);
		String encodedSharedSecreet = Base64
				.encodeBase64String(hashedSharedSecret);
		
		employee.setHashedPassword(encodedSharedSecreet);
		
		this.customerDao.update(employee);
		
		//Send confirmation email.
		String sub = getText("portal.lostpass.sub");
		String body = getText("portal.lostpass.body", randomPass);
		
		try {
			this.sendEmail(employee.getEmail(), sub, body, employee.getFirstName() + " "+ employee.getLastName());
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new GenericResponse<String>("A temporary password has been sent to your email address",
				ApplicationStatusCodes.SUCCESS_UPDATED);

	}

	@Override
	public GenericResponse<String> addEmployee(RegistrationRequest user) {
		String randomPass = Identifier.next(6);
		user.setPassword(randomPass);
		return this.registerUser(user);
		
	}

	@Override
	public GenericResponse<String> updateEmployee(Long id, RegistrationRequest user) {
		Employee employee = this.customerDao.findEmployeeByEmail(user
				.getEmail());

		if (employee == null) {
			return new GenericResponse<String>(
					"Employee does not exist",
					ApplicationStatusCodes.INVALID_REQUEST);
		}
		
		employee.setFirstName(user.getFirstName());
		employee.setLastName(user.getLastName());
		employee.setPhone(user.getPhone());
		employee.setAddress(user.getAddress());
		
		this.customerDao.update(employee);
		
		return new GenericResponse<String>("Record updated successfully",
				ApplicationStatusCodes.SUCCESS_UPDATED);
	}

	@Override
	public RegistrationRequest getEmployee(Long id) {
		Employee employee = this.customerDao.loadById(id);
		if (employee == null) {
			throw new ServiceException("Employee not found", "404", 404);
		}
		RegistrationRequest emp = new RegistrationRequest();
		emp.setAddress(employee.getAddress());
		emp.setEmail(employee.getEmail());
		emp.setFirstName(employee.getFirstName());
		emp.setLastName(employee.getLastName());
		emp.setPhone(employee.getPhone());
		
		return emp;
	}

	@Override
	public GenericResponse<String> deleteEmployee(Long id) {
		Employee employee = this.customerDao.loadById(id);
		if (employee == null) {
			throw new ServiceException("Employee not found", "404", 404);
		}
		this.customerDao.delete(employee);
		
		return new GenericResponse<String>("Record deleted successfully",
				ApplicationStatusCodes.SUCCESS_REMOVED);
	}
}
