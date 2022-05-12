package com.techelevator.projects.dao;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Employee;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcEmployeeDao implements EmployeeDao {

	private final JdbcTemplate jdbcTemplate;

	public JdbcEmployeeDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employeeList = new ArrayList<>();
		String sqlGetAllEmployees = "SELECT employee_id, department_id, first_name, last_name, birth_date, hire_date FROM employee";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllEmployees);
		while(results.next()) {
			employeeList.add(mapRowToEmployee(results));
		}
		return employeeList;
	}


	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		List<Employee> searchEmployees = new ArrayList<>();
		String sqlSearchEmployees = "SELECT employee_id, department_id, first_name, last_name, birth_date, hire_date FROM employee WHERE first_name ILIKE ? AND last_name ILIKE ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchEmployees, "%" + firstNameSearch + "%", "%" + lastNameSearch + "%");
		while(results.next()) {
			searchEmployees.add(mapRowToEmployee(results));
		}
		return searchEmployees;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		List<Employee> employeesByProjectId = new ArrayList<>();
		String sqlGetEmployeesByProjectId = "SELECT e.employee_id, e.department_id, e.first_name, " +
							"e.last_name, e.birth_date, e.hire_date FROM employee e " +
							"JOIN project_employee pe USING (employee_id) WHERE pe.project_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeesByProjectId, projectId);
		while(results.next()) {
			employeesByProjectId.add(mapRowToEmployee(results));
		}
		return  employeesByProjectId;
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		String sqlAddEmployeeToProject = "INSERT INTO project_employee (project_id, employee_id) VALUES (?,?)";
		jdbcTemplate.update(sqlAddEmployeeToProject, projectId,employeeId);
	}

	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		String sqlRemoveEmployeeFromProject = "DELETE FROM project_employee WHERE project_id = ? AND employee_id = ?";
		jdbcTemplate.update(sqlRemoveEmployeeFromProject, projectId, employeeId);
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		List<Employee> employeesWithoutProjects = new ArrayList<>();
		String sqlGetEmployeesWithoutProjects = "SELECT employee_id, department_id, first_name, " +
				"last_name, birth_date, hire_date FROM employee e WHERE employee_id " +
				"NOT IN (SELECT DISTINCT employee_id FROM project_employee)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeesWithoutProjects);
		while(results.next()) {
			employeesWithoutProjects.add(mapRowToEmployee(results));
		}
		return employeesWithoutProjects;
	}

	private Employee mapRowToEmployee(SqlRowSet results) {
		Employee employee = new Employee();
		employee.setId(results.getLong("employee_id"));
		employee.setDepartmentId(results.getLong("department_id"));
		employee.setFirstName(results.getString("first_name"));
		employee.setLastName(results.getString("last_name"));
		employee.setBirthDate(results.getDate("birth_date").toLocalDate());
		employee.setHireDate(results.getDate("hire_date").toLocalDate());

		return employee;
	}


}
