package com.versacomllc.training.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.versacomllc.training.dao.EmployeeDao;
import com.versacomllc.training.domain.Employee;



/**
 * This class is responsible for managing Registration entity into the database.
 * 
 * @author Shamim Ahmmed
 * 
 */
@Repository
public class EmployeeDaoImpl extends GenericEntityDaoImpl<Employee, Long>
    implements EmployeeDao {

	@Override
	public Employee findEmployeeByEmail(String email) {
		String clause = " t.email = ?1 ";
        List<Employee> employees = loadByClause(clause, new Object[] { email });
        if(employees == null || employees.size() == 0){
        	return null;
        }
		return employees.get(0);
	}

	@Override
	public List<Employee> findAllNonAdminUser() {
		String clause = " t.role = ?1 ";
        List<Employee> employees = loadByClause(clause, new Object[] { "user" });
		return employees;
	}


}
