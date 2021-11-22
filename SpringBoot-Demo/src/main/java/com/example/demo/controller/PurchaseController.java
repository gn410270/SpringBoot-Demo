package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.PurchaseItem;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseItemRepository;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.validator.PurchaseItemValidator;
import com.example.demo.validator.PurchaseValidator;

@Controller
@RequestMapping(value = "/purchase")
public class PurchaseController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private PurchaseItemRepository purchaseItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PurchaseValidator purchaseValidator;
	
	@Autowired
	private PurchaseItemValidator purchaseItemValidator;
	
	@RequestMapping(value = "/")
	public String index(Model model) {
		Purchase purchase = new Purchase();
		model.addAttribute("purchase", purchase);
		model.addAttribute("employees", employeeRepository.findAll().stream().filter(e->e.getDepartment().getName().equals("採購部")).toList());
		model.addAttribute("suppliers", supplierRepository.findAll());
		model.addAttribute("purchases", purchaseRepository.findAll());
		return "purchase";
	}
	
	@RequestMapping(value = "/create")
	public String create(Purchase purchase,Model model,BindingResult result) {
		purchaseValidator.validate(purchase, result);
		if(result.hasErrors()) {
			model.addAttribute("purchase", purchase);
			model.addAttribute("employees", employeeRepository.findAll().stream().filter(e->e.getDepartment().getName().equals("採購部")).toList());
			model.addAttribute("suppliers", supplierRepository.findAll());
			model.addAttribute("purchases", purchaseRepository.findAll());
			return "purchase";
		}
		purchaseRepository.save(purchase);
		return "redirect:/purchase/";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id")Long id,Model model) {
		Purchase purchase = purchaseRepository.findById(id).get();
		model.addAttribute("purchase", purchase);
		model.addAttribute("employees", employeeRepository.findAll().stream().filter(e->e.getDepartment().getName().equals("採購部")).toList());
		model.addAttribute("suppliers", supplierRepository.findAll());
		model.addAttribute("purchases", purchaseRepository.findAll());
		return "purchase-update";
	}
	
	@RequestMapping(value = "/update/{id}")
	public String update(@PathVariable(value = "id")Long id,Purchase purchase,Model model,BindingResult result) {
		purchaseValidator.validate(purchase, result);
		if(result.hasErrors()) {
			model.addAttribute("purchase", purchase);
			model.addAttribute("employees", employeeRepository.findAll().stream().filter(e->e.getDepartment().getName().equals("採購部")).toList());
			model.addAttribute("suppliers", supplierRepository.findAll());
			model.addAttribute("purchases", purchaseRepository.findAll());
			return "purchase-update";
		}
		purchase.setId(id);
		purchaseRepository.save(purchase);
		return "redirect:/purchase/";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id")Long id) {
		purchaseRepository.deleteById(id);
		return "redirect:/purchase/";
	}
	
	//--------------------------------------------------------------------------
	@RequestMapping(value = "{pid}/item")
	public String viewItem(@PathVariable(value = "pid")Long pid,Model model) {
		Purchase purchase = purchaseRepository.findById(pid).get();
		PurchaseItem purchaseItem = new PurchaseItem();
		model.addAttribute("purchase", purchase);
		model.addAttribute("purchaseItem", purchaseItem);
		model.addAttribute("products", productRepository.findAll());
		return "purchaseItem";
	}
	
	@RequestMapping(value = "/{pid}/item/create")
	public String createItem(@PathVariable(value = "pid")Long pid,PurchaseItem purchaseItem,Model model,BindingResult result) {
		purchaseItemValidator.validate(purchaseItem, result);
		if(result.hasErrors()) {
			Purchase purchase = purchaseRepository.findById(pid).get();
			model.addAttribute("purchase", purchase);
			model.addAttribute("purchaseItem", purchaseItem);
			model.addAttribute("products", productRepository.findAll());
			return "purchaseItem";
		}
		Purchase purchase = purchaseRepository.findById(pid).get();
		purchaseItem.setPurchase(purchase);
		purchaseItemRepository.save(purchaseItem);
		return "redirect:/purchase/" + pid + "/item";
	}
	
	@RequestMapping(value = "/{pid}/item/edit/{iid}")
	public String editItem(@PathVariable(value = "pid")Long pid,@PathVariable(value = "iid")Long iid,Model model) {
		Purchase purchase = purchaseRepository.findById(pid).get();
		PurchaseItem purchaseItem = purchaseItemRepository.findById(iid).get();
		model.addAttribute("purchase", purchase);
		model.addAttribute("purchaseItem", purchaseItem);
		model.addAttribute("products", productRepository.findAll());
		return "purchaseItem";
	}
	
	@RequestMapping(value = "/{pid}/item/delete/{iid}")
	public String delete(@PathVariable(value = "pid")Long pid,@PathVariable(value = "iid")Long iid) {
		purchaseItemRepository.deleteById(iid);
		return "redirect:/purchase/" + pid + "/item";
	}
	
}
