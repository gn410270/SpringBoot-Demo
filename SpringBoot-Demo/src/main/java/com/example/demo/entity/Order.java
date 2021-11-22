package com.example.demo.entity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;  //訂單日期
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ship_date;  //出貨日期
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	@OneToMany(mappedBy = "order")
	@OrderBy("id ASC")
	private Set<OrderItem> orderItems = new LinkedHashSet<>();
	
	public Integer getTotal() {
		if(orderItems.size() == 0) {return 0;}
		return orderItems.stream().mapToInt(oi->oi.getProduct().getPrice()*oi.getAmount()).sum();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getShip_date() {
		return ship_date;
	}

	public void setShip_date(Date ship_date) {
		this.ship_date = ship_date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	
}
