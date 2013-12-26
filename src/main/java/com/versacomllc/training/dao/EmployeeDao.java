package com.versacomllc.training.dao;


import java.util.List;

import com.versacomllc.training.domain.Employee;

/**
 * This class is the DAO interface for zaq zaq user registration domain object.
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface EmployeeDao extends GenericDao <Employee, Long> {


	Employee findEmployeeByEmail(String email);
	
	List<Employee> findAllNonAdminUser();
	
}
