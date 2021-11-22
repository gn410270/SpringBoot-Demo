package com.example.demo.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;  
	
	@Column
	private String job_title;
	
	@Column
	private String account;
	
	@Column
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	
	@OneToMany(mappedBy = "employee")
	@OrderBy("id ASC")
	private Set<Purchase> purchases = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
	@OrderBy("id ASC")
	private Set<Order> orders = new LinkedHashSet<>();

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

	public String getJob_title() {
		return job_title;
	}

	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/*
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", job_title=" + job_title + ", department=" + department
				+ ", purchases=" + purchases + ", orders=" + orders + "]";
	}
	*/
	
}
