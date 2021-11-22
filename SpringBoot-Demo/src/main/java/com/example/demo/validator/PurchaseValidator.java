package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.entity.Purchase;

@Component
public class PurchaseValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Purchase.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Purchase purchase = (Purchase)target;
		
		if(purchase.getDate()==null) {
			errors.rejectValue("date", null, "請輸入進貨日期");
		}
		
		
	}
	
}
