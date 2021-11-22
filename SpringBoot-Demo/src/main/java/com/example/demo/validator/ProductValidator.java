package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.entity.Product;

@Component
public class ProductValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product)target;
		
		if(product.getName()==null || product.getName().trim().length()==0) {
			errors.rejectValue("name", null, "請輸入商品名稱");
		}else if (product.getName().trim().length()>20) {
			errors.rejectValue("name", null, "商品名稱太長，請重新輸入");
		}
		if(product.getCost()==null) {
			errors.rejectValue("cost", null, "請輸入成本價");
		}else if (product.getCost()==0) {
			errors.rejectValue("cost", null, "成本價必須大於0");
		}
		if(product.getPrice()==null) {
			errors.rejectValue("price", null, "請輸入售價");
		}else if (product.getCost()==0) {
			errors.rejectValue("price", null, "售價必須大於0");
		}
	}
	
}
