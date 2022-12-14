package com.masters.waterways.services;

import java.util.List;
import com.masters.waterways.daos.*;
import com.masters.waterways.models.Employee;
import com.masters.waterways.models.EmployeeStatusProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	JdbcTemplate jdbctemplate;

//	@Override
//	public void delete (Employee employee) throws RuntimeException {
//		if (jdbctemplate.query(
//				"SELECT EmployeeId FROM Crew, Voyage WHERE Crew.EmployeeId = ? AND Crew.VoyageId = Voyage.VoyageId AND Voyage.DepartureTime < NOW()",
//				new BeanPropertyRowMapper<Integer>(Integer.class), employee.getEmployeeId()
//		).isEmpty())
//			jdbctemplate.update(
//					"DELETE FROM Employee WHERE EmployeeId = ?",
//					employee.getEmployeeId()
//			);
//		else
//			throw new RuntimeException("Cannot dismiss crew of past voyages");
//	}

	@Override
	public List<Employee> getAll () {
		return jdbctemplate.query(
				"SELECT * FROM Employee",
				new BeanPropertyRowMapper<Employee>(Employee.class)
		);
	}

	@Override
	public Employee getById (int id) {
		return jdbctemplate.queryForObject(
				"SELECT * FROM Employee WHERE EmployeeId = ?",
				new BeanPropertyRowMapper<Employee>(Employee.class), id
		);
	}

	@Override
	public Employee getByUserId (int id) {
		return jdbctemplate.queryForObject(
				"SELECT * FROM Employee WHERE UserId = ?",
				new BeanPropertyRowMapper<Employee>(Employee.class), id
		);
	}

    @Override
	@Transactional
    public void makeEmployee(Employee employee) {
        if (!jdbctemplate.query(
				"SELECT * FROM Employee WHERE UserId = ?",
				new BeanPropertyRowMapper<>(Employee.class), employee.getUserId()
		).isEmpty())
			throw new RuntimeException("User is already an employee");
		jdbctemplate.update(
				"INSERT INTO Employee (UserId, JoinDate, EmployeeStatusCode) VALUE (?, NOW(), ?)",
				employee.getUserId(), employee.getEmployeeStatusCode()
		);
    }

	@Override
	public void setSuspended (int employeeId) {
		jdbctemplate.update(
				"DELETE FROM Crew WHERE Crew.EmployeeId = ? AND EXISTS(SELECT * FROM Voyage WHERE VoyageId = Crew.VoyageId AND DepartureTime > NOW())",
				employeeId
		);

		jdbctemplate.update(
				"UPDATE Employee SET EmployeeStatusCode = ? WHERE EmployeeId = ?",
				EmployeeStatusProvider.getEmployeeStatusCode.get("SUSPENDED"), employeeId
		);
	}

	@Override
	public void setActive (int employeeId) {
		jdbctemplate.update(
				"UPDATE Employee SET EmployeeStatusCode = ? WHERE EmployeeId = ?",
				EmployeeStatusProvider.getEmployeeStatusCode.get("ACTIVE"), employeeId
		);
	}

	@Override
	public Boolean isEmployee(int id) {
		try{
			Employee employee = jdbctemplate.queryForObject(
					"SELECT * FROM Employee WHERE UserId = ?",
					new BeanPropertyRowMapper<>(Employee.class), id
			);
			return employee!=null;
		}
		catch (Exception e){
			return false;
		}

	}
}
