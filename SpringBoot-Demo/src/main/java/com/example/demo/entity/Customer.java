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
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@org.hibernate.validator.constraints.NotBlank(message = "請輸入客戶名稱")
	@Column
	private String name;  //客戶名稱
	
	@org.hibernate.validator.constraints.NotBlank(message = "請輸入電話號碼")
	@Column
	private String phone_number;  //電話號碼
	
	@org.hibernate.validator.constraints.NotBlank(message = "請輸入傳真號碼")
	@Column(length = 20)
	private String fax;  //傳真號碼
	
	@org.hibernate.validator.constraints.NotBlank(message = "請輸入統一編號")
	@Column
	private String tax_id;   //統一編號
	
	@org.hibernate.validator.constraints.NotBlank(message = "請輸入地址")
	@Column
	private String address;  //地址
	
	@OneToMany(mappedBy = "customer")
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

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTax_id() {
		return tax_id;
	}

	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
}
