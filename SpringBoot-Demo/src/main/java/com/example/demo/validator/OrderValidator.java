package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.entity.Order;

@Component
public class OrderValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Order.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Order order = (Order)target;
		
		if(order.getDate()==null) {
			errors.rejectValue("date", null, "請輸入訂單日期");
		}
		
		if(order.getShip_date()==null) {
			errors.rejectValue("ship_date", null, "請輸入出貨日期");
		}
		
		
	}
	
}
