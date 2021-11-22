package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.entity.Supplier;

@Component
public class SupplierValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Supplier.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Supplier supplier = (Supplier)target;
		
		if(supplier.getName()==null || supplier.getName().trim().length()==0) {
			errors.rejectValue("name", null, "請輸入供應商名稱");
		}
		if(supplier.getPhone_number()==null || supplier.getPhone_number().trim().length()<10) {
			errors.rejectValue("phone_number", null, "請輸入供應商電話號碼");
		}
		if(supplier.getAddress()==null || supplier.getAddress().trim().length()<7) {
			errors.rejectValue("phone_number", null, "請輸入供應商地址");
		}
		
	}
	
}
