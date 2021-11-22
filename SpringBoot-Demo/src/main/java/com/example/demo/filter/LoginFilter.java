package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/home/*","/department/*","/customer/","/order/","/product/","/purchase/","/inventory/","/supplier/"})
public class LoginFilter extends BaseFilter{

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("---------------------------------------------------LoginFilter");
		HttpSession session = request.getSession();
		if(session!=null) {
			System.out.println("---------------------------------------------------LoginFilter Session");
			Boolean isPass = (Boolean)session.getAttribute("isPass");
			if(isPass!=null && isPass==true) {
				System.out.println("---------------------------------------------------LoginFilter Session Pass");
				chain.doFilter(request, response);
				return;
			}
		}
		response.sendRedirect("/psi/login/");
	}

}
