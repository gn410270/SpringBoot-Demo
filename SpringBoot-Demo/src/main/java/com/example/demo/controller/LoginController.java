package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@RequestMapping(value = "/")
	public String index(Model model,HttpServletRequest req) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "login";
	}
	
	@RequestMapping(value = "/accpass")
	public String login(HttpServletRequest req) {
		Boolean isPass = null;
		HttpSession session = req.getSession();
		
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		//System.out.println(account + "  " + password);
		
		if(session==null || (Boolean)session.getAttribute("isPass")==null) {
			Employee employee = employeeRepository.findByAccountAndPassword(account, password);
			if(employee!=null && employee.getAccount().equals(account) && employee.getPassword().equals(password)) {
				isPass = true;
				session.setAttribute("username", employee.getName());
				session.setAttribute("isPass", isPass);
				return "home";
			}else {
				return "redirect:/login/";
			}
		}else {
			return "home";
		}
		
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		return "redirect:/login/";
	}
}
