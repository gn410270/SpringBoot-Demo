package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.entity.Employee;

@Component
public class EmployeeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Employee employee = (Employee)target;
		
		if(employee.getName()==null || employee.getName().trim().length()==0) {
			errors.rejectValue("name", null, "請輸入員工姓名");
		}
		if(employee.getJob_title()==null || employee.getJob_title().trim().length()==0) {
			errors.rejectValue("name", null, "請輸入職稱");
		}
	}
	
}
