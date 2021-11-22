package com.example.demo.entity;

public interface Inventory {
	Long getId();
	String getName();
	Integer getCost();
	Integer getPrice();
	Integer getPurchaseAmount(); // 採購數量
	Integer getOrderAmount(); // 銷貨(訂購)數量
}
