package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.validator.EmployeeValidator;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeValidator employeeValidator;
	
	@RequestMapping(value = "/")
	public String index(Model model,@RequestParam(name = "department_id",required = false)Long department_id) {
		Employee employee = new Employee();
		Department department = null;
		if(department_id!=null) {
			department = departmentRepository.findById(department_id).get();
			employee.setDepartment(department);
		}
		model.addAttribute("employee", employee);
		if(department ==null || department_id == 0L) {
			model.addAttribute("employees", employeeRepository.findAll());
		}else {
			model.addAttribute("employees", employeeRepository.findByDepartment(department));
		}
		model.addAttribute("departments", departmentRepository.findAll());
		return "employee";
	}
	
	@RequestMapping(value = "/create")
	public String create(Employee employee,Model model,BindingResult result,@RequestParam(name = "department_id",required = false)Long department_id) {
		employeeValidator.validate(employee, result);
		if(result.hasErrors()) {
			Department department = null;
			if(department_id!=null) {
				department = departmentRepository.findById(department_id).get();
				employee.setDepartment(department);
			}
			model.addAttribute("employee", employee);
			if(department ==null || department_id == 0L) {
				model.addAttribute("employees", employeeRepository.findAll());
			}else {
				model.addAttribute("employees", employeeRepository.findByDepartment(department));
			}
			model.addAttribute("departments", departmentRepository.findAll());
			return "employee";
		}
		Department department = departmentRepository.findById(employee.getDepartment().getId()).get();
		employee.setDepartment(department);
		employeeRepository.save(employee);
		return "redirect:/employee/?department_id="+department.getId();
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(Model model,@PathVariable(value = "id")Long id) {
		Employee employee = employeeRepository.findById(id).get();
		model.addAttribute("employee", employee);
		model.addAttribute("departments", departmentRepository.findAll());
		return "employee-update";
	}
	
	@RequestMapping(value = "/update/{id}")
	public String update(@PathVariable(value = "id")Long id,Employee employee,Model model,BindingResult result) {
		employeeValidator.validate(employee, result);
		if(result.hasErrors()) {
			model.addAttribute("employee", employee);
			model.addAttribute("departments", departmentRepository.findAll());
			return "employee-update";
		}
		Department department = departmentRepository.findById(employee.getDepartment().getId()).get();
		employee.setDepartment(department);
		employeeRepository.save(employee);
		return "redirect:/employee/?department_id="+department.getId();
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id")Long id) {
		Employee employee = employeeRepository.findById(id).get();
		Department department = employee.getDepartment();
		employeeRepository.deleteById(id);
		return "redirect:/employee/?department_id="+department.getId();
	}
}
