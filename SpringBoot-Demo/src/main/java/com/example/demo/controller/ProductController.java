package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.validator.ProductValidator;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductValidator productValidator;
	
	@RequestMapping(value = "/")
	public String index(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		model.addAttribute("products", productRepository.findAll());
		return "product";
	}
	
	@RequestMapping(value = "/create")
	public String create(Product product,Model model,BindingResult result) {
		productValidator.validate(product, result);
		if (result.hasErrors()) {
			model.addAttribute("product", product);
			model.addAttribute("products", productRepository.findAll());
			return "product";
		}
		productRepository.save(product);
		return "redirect:/product/";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Product product = productRepository.findById(id).get();
		model.addAttribute("product", product);
		return "product-update";
	}
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable("id") Long id, Product product,Model model,BindingResult result) {
		productValidator.validate(product, result);
		if(result.hasErrors()) {
			model.addAttribute("product", product);
			return "product-update";
		}
		product.setId(id);
		productRepository.save(product);
		return "redirect:/product/";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		productRepository.deleteById(id);
		return "redirect:/product/";
	}
	
	
}
