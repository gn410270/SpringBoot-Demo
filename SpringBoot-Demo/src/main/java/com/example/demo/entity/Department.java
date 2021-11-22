package com.example.demo.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	
	@org.hibernate.validator.constraints.NotBlank(message = "請輸入部門名稱")
	@Column
	private String name;
	
	@org.hibernate.validator.constraints.NotBlank(message = "請輸入主管姓名")
	@Column
	private String manager; 
	
	@OneToMany(mappedBy = "department")
	@OrderBy("id ASC")
	private Set<Employee> employees = new LinkedHashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	
	
	
}
