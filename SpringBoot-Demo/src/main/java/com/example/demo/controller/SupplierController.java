package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Supplier;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.validator.SupplierValidator;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private SupplierValidator supplierValidator;
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("supplier", new Supplier());
		model.addAttribute("suppliers", supplierRepository.findAll());
		return "supplier";
	}
	
	@RequestMapping("/create")
	public String create(Supplier supplier,Model model,BindingResult result) {
		supplierValidator.validate(supplier, result);
		if(result.hasErrors()) {
			model.addAttribute("supplier", supplier);
			model.addAttribute("suppliers", supplierRepository.findAll());
			return "supplier";
		}
		supplierRepository.save(supplier);
		return "redirect:/supplier/";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Supplier supplier = supplierRepository.findById(id).get();
		model.addAttribute("supplier", supplier);
		return "supplier-update";
	}
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable("id") Long id,Supplier supplier,Model model,BindingResult result) {
		supplierValidator.validate(supplier, result);
		if(result.hasErrors()) {
			model.addAttribute("supplier", supplier);
			return "supplier-update";
		}
		supplierRepository.save(supplier);
		return "redirect:/supplier/";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		supplierRepository.deleteById(id);
		return "redirect:/supplier/";
	}
	
	
}
