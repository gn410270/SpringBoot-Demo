package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.entity.Inventory;
import com.example.demo.entity.OrderItem;
import com.example.demo.repository.ProductRepository;

@Component
public class OrderItemValidator implements Validator{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return OrderItem.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		OrderItem orderItem = (OrderItem)target;
		
		if(orderItem.getAmount()==null) {
			errors.rejectValue("amount", null, "請輸入商品數量");
		}else if (orderItem.getAmount()<1) {
			errors.rejectValue("amount", null, "商品數量必須大於1");
		}else {
			// purchaseAmount --> 進貨/採購
			// orderAmount --> 銷售/訂單
			Inventory inventory = productRepository.findInventoryById(orderItem.getProduct().getId());
			int remain = 0;
			try {
				if(inventory.getPurchaseAmount()==null || inventory.getPurchaseAmount()==0) {
					errors.rejectValue("amount", null, "此商品無進貨資料");
					return;
				}
				if(inventory.getOrderAmount()==null || inventory.getOrderAmount()==0) {
					remain = inventory.getPurchaseAmount();
				}else {
					remain = inventory.getPurchaseAmount() - inventory.getOrderAmount();
				}
				if(orderItem.getAmount()>remain) {
					errors.rejectValue("amount", null, "此商品庫存不足(目前庫存量：" + remain + ")");
				}
			} catch (Exception e) {
				errors.rejectValue("amount", null, "其他錯誤：" + e);
			}
			
		}
		
		
	}
	
}
