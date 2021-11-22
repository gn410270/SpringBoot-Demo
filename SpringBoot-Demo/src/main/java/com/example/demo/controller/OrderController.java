package com.example.demo.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.validator.OrderItemValidator;
import com.example.demo.validator.OrderValidator;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderValidator orderValidator;
	
	@Autowired
	private OrderItemValidator orderItemValidator;
	
	@RequestMapping(value = "/")
	public String index(Model model,@RequestParam(name = "customer_id",required = false)Long customer_id) {
		Order order = new Order();
		
		Customer customer = null;
		if(customer_id != null) {
			customer = customerRepository.findById(customer_id).get();
			order.setCustomer(customer);
		}
		model.addAttribute("order", order);
		if(customer == null || customer_id == 0L) {
			model.addAttribute("orders", orderRepository.findAll());
		}else {
			model.addAttribute("orders", orderRepository.findByCustomer(customer));
		}
		
		//model.addAttribute("order", order);
		//model.addAttribute("orders", orderRepository.findAll());
		
		model.addAttribute("customers", customerRepository.findAll());
		model.addAttribute("employees", employeeRepository.findAll());
		return "order";
	}
	
	@RequestMapping(value = "/create")
	public String create(Model model,Order order,BindingResult result,@RequestParam(name = "customer_id",required = false)Long customer_id) {
		orderValidator.validate(order, result);
		if(result.hasErrors()) {
			
			Customer customer = null;
			if(customer_id != null) {
				customer = customerRepository.findById(customer_id).get();
				order.setCustomer(customer);
			}
			model.addAttribute("order", order);
			if(customer == null || customer_id == 0L) {
				model.addAttribute("orders", orderRepository.findAll());
			}else {
				model.addAttribute("orders", orderRepository.findByCustomer(customer));
			}
			
			//model.addAttribute("order", order);
			//model.addAttribute("orders", orderRepository.findAll());
			
			model.addAttribute("customers", customerRepository.findAll());
			model.addAttribute("employees", employeeRepository.findAll());
			return "order";
		}
		orderRepository.save(order);
		Customer customer = customerRepository.findById(order.getCustomer().getId()).get();
		return "redirect:/order/?customer_id=" + customer.getId();
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(Model model,@PathVariable(value = "id")Long id) {
		Order order = orderRepository.findById(id).get();
		model.addAttribute("order", order);
		model.addAttribute("customers", customerRepository.findAll());
		model.addAttribute("employees", employeeRepository.findAll());
		return "order-update";
	}
	
	@RequestMapping(value = "/update/{id}")
	public String update(Order order,Model model,BindingResult result,@PathVariable(value = "id")Long id) {
		orderValidator.validate(order, result);
		if(result.hasErrors()) {
			model.addAttribute("order", order);
			model.addAttribute("customers", customerRepository.findAll());
			model.addAttribute("employees", employeeRepository.findAll());
			return "order-update";
		}
		order.setId(id);
		orderRepository.save(order);
		return "redirect:/order/?customer_id=" + order.getCustomer().getId();
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id")Long id) {
		Customer customer = orderRepository.findById(id).get().getCustomer();
		orderRepository.deleteById(id);
		return "redirect:/order/?customer_id=" + customer.getId();
	}
	
	//---------------------------------------------------------------------------
	
	@RequestMapping(value = "/{oid}/item")
	public String viewItem(Model model, @PathVariable("oid") Long oid) {
		Order order = orderRepository.findById(oid).get();
		OrderItem orderItem = new OrderItem();
		model.addAttribute("order", order);
		model.addAttribute("orderItem", orderItem);
		model.addAttribute("products", productRepository.findAll());
		return "orderItem";
	}
	
	@RequestMapping(value = "/{oid}/item/create")
	public String createItem(@PathVariable(value = "oid")Long oid,OrderItem orderItem,Model model,BindingResult result) {
		orderItemValidator.validate(orderItem, result);
		if(result.hasErrors()) {
			Order order = orderRepository.findById(oid).get();
			model.addAttribute("order", order);
			model.addAttribute("orderItem", orderItem);
			model.addAttribute("products", productRepository.findAll());
			return "orderItem";
		}
		Order order = orderRepository.findById(oid).get();
		orderItem.setOrder(order);
		orderItemRepository.save(orderItem);
		return "redirect:/order/" + oid + "/item";
	}
	
	@RequestMapping(value = "/{oid}/item/edit/{iid}")
	public String editItem(@PathVariable(value = "oid")Long oid,@PathVariable(value = "iid")Long iid,Model model) {
		Order order = orderRepository.findById(oid).get();
		OrderItem orderItem = orderItemRepository.findById(iid).get();
		model.addAttribute("order", order);
		model.addAttribute("orderItem", orderItem);
		model.addAttribute("products", productRepository.findAll());
		return "orderItem";
	}
	
	@RequestMapping(value = "/{oid}/item/delete/{iid}")
	public String deleteItem(@PathVariable(value = "oid")Long oid,@PathVariable(value = "iid")Long iid) {
		orderItemRepository.deleteById(iid);
		return "redirect:/order/" + oid + "/item";
	}
	
}
