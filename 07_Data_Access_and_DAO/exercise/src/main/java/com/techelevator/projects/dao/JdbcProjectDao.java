package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Project;

public class JdbcProjectDao implements ProjectDao {

	private final JdbcTemplate jdbcTemplate;

	public JdbcProjectDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Project getProject(Long projectId) {
		Project project = null;
		String sqlGetProject = "SELECT project_id, name, from_date, to_date FROM project WHERE project_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetProject, projectId);
		if (results.next()) {
			project = mapRowToProject(results);
		}
		return project;
	}

	@Override
	public List<Project> getAllProjects() {
		List<Project> projectList = new ArrayList<>();
		String sqlGetAllProjects = "SELECT project_id, name, from_date, to_date FROM project";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllProjects);
		while(results.next()) {
			projectList.add(mapRowToProject(results));
		}
		return projectList;
	}

	@Override
	public Project createProject(Project newProject) {
		String sqlCreateProject = "INSERT INTO project(name, from_date, to_date) VALUES (?,?,?) RETURNING project_id";
		Long newId = jdbcTemplate.queryForObject(sqlCreateProject, Long.class, newProject.getName(), newProject.getFromDate(), newProject.getToDate());
		return getProject(newId);
	}

	@Override
	public void deleteProject(Long projectId) {
		String sqlDeleteProjectEmployee = "DELETE FROM project_employee WHERE project_id = ?";
		jdbcTemplate.update(sqlDeleteProjectEmployee, projectId);
		String sqlDeleteProject = "DELETE FROM project WHERE project_id = ?";
		jdbcTemplate.update(sqlDeleteProject, projectId);

	}

	private Project mapRowToProject(SqlRowSet results) {
		Project project = new Project();
		project.setId(results.getLong("project_id"));
		project.setName(results.getString("name"));
		if (results.getDate("from_date") != null) {
			project.setFromDate(results.getDate("from_date").toLocalDate());
		}
		if (results.getDate("to_date") != null) {
			project.setToDate(results.getDate("to_date").toLocalDate());
		}
		return project;
	}

	

}
