package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.entity.PurchaseItem;

@Component
public class PurchaseItemValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return PurchaseItem.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PurchaseItem purchaseItem = (PurchaseItem)target;
		
		if(purchaseItem.getAmount()==null) {
			errors.rejectValue("amount", null, "請輸入商品數量");
		}else if(purchaseItem.getAmount()<1) {
			errors.rejectValue("amount", null, "商品數量需大於1");
		}
		
		
	}
	
}
