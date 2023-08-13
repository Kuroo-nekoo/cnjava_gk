package spring.Controller;

import java.util.Date;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import spring.Entity.*;
import spring.Service.BillService;
import spring.Service.CartService;
import spring.Service.DetailCategoryService;
import spring.Service.ProductService;
import spring.Service.UserService;
import spring.Entity.BillEntity;
import spring.Service.CategoryService;
import spring.Service.DetailBillService;

@Controller
public class AdminController {

	private final UserService userService;
	
	private final ProductService productService;
	
	private final BillService billService;
	
	private final CategoryService categoryService;
	
	private final DetailCategoryService detailCategoryService;

	private final CartService cartService;
	
	private final DetailBillService detailBillService;
	
	private final PasswordEncoder passwordEncoder;

	public AdminController(UserService userService, ProductService productService, BillService billService, CategoryService categoryService, DetailCategoryService detailCategoryService, CartService cartService, DetailBillService detailBillService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.productService = productService;
		this.billService = billService;
		this.categoryService = categoryService;
		this.detailCategoryService = detailCategoryService;
		this.cartService = cartService;
		this.detailBillService = detailBillService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/admin")
	public String viewAccounts(Model model, Authentication authentication) {
		List<UserEntity> listUserEntities = userService.findAll();
		model.addAttribute("listUsers", listUserEntities);
		model.addAttribute("username",authentication.getName());
		return "admin";
	}
	
	@GetMapping("/admin/account/add")
	public String viewAccountForm(Model model) {
		UserEntity userEntity = new UserEntity();
		model.addAttribute("add","addAccount");
		model.addAttribute("user", userEntity);
		return "adminform";
	}
	@PostMapping("/admin/addaccount")
	public String register(@Valid @ModelAttribute("user") UserEntity userEntity,
			BindingResult result, Model model) {

		UserEntity existingUserEntity = userService.findByUsername(userEntity.getUsername());
		System.out.println(userEntity.getFullname()+" "+ userEntity.getSex());
		if (userEntity.getRoles() == "ROLE_ADMIN") {
			userEntity.setRoles("ROLE_ADMIN");
		}else {
		userEntity.setRoles("ROLE_USER");}
		if (existingUserEntity != null)
			result.rejectValue("email", null,
					"User already registered !!!");
		
		if (result.hasErrors()) {
			model.addAttribute("user", userEntity);
			return "/register";
		}

		userService.saveUser(userEntity);
		CartEntity cartEntity = new CartEntity();
		cartEntity.setUser(userEntity);
		cartService.addCart(cartEntity);
		return "redirect:/admin";
	}
	
	@PostMapping("/admin/update")
	public String updateAccount(@Valid @ModelAttribute("user") UserEntity userEntity,
			BindingResult result, Model model) {
		userService.updateInfo(userEntity);
		return "redirect:/admin";
	}
	
	@GetMapping("/admin/resetpass/{id}")
	public String resetPass(@PathVariable("id") long id) {
		UserEntity userEntity = userService.getById(id);
		userEntity.setPassword(passwordEncoder.encode("default123"));
		userService.saveUser(userEntity);
		return "redirect:/admin";
	}
	
	@GetMapping("/admin/update/{id}")
	public String updateAccount(@PathVariable("id") long id,Model model) {
		UserEntity userEntity = userService.getById(id);
		model.addAttribute("user", userEntity);
		return "updateaccount";
	}
	
	@GetMapping("/admin/delete/{id}")
	public String deleteAccount(@PathVariable("id") long id) {
		UserEntity userEntity = userService.getById(id);
		if (userEntity != null) {
			userService.deleteUser(userEntity);
		}
		return "redirect:/admin";
	}
	
	@GetMapping("/admin/product")
	public String viewProduct(Model model, Authentication authentication) {
		List<DetailCategoryEntity> listProducts = detailCategoryService.findAll();
		model.addAttribute("listProducts",listProducts);
		model.addAttribute("user",authentication.getName());
		return "adminproduct";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") long id) {
		ProductEntity p = productService.findProductById(id).get();


		if (p != null) {
			productService.deleteProductById(p.getId());
		}

		return "redirect:/admin/product";
	}
	
	@GetMapping("/admin/bill")
	public String viewBill(Model model, Authentication authentication) {
		List<BillEntity> listBillEntities = billService.findAll();
		double total = 0.0;
		for (BillEntity b : listBillEntities) {
			List<DetailBillEntity> db = detailBillService.findAllByBillId(b.getId());
			for (DetailBillEntity d : db) {
				System.out.println(d.getId());
				total += d.getQuantity() * d.getProduct().getPrice();
			}
			
		}
		model.addAttribute("total","Total: " + total);
		model.addAttribute("listBills", listBillEntities);
		model.addAttribute("user",authentication.getName());
		return "adminbill";
	}
	
	@GetMapping("/admin/bill/update/{id}")
	public String viewFormBill(@PathVariable("id") long id, Model model) {
		
		BillEntity billEntity = billService.getById(id);
		model.addAttribute("bill", billEntity);
	
		
		return "billform";
	}
	
	@GetMapping("/admin/bill/add")
	public String viewFormBillAdd( Model model) {
		BillEntity billEntity = new BillEntity();
		billEntity.setName("default");
		billEntity.setAddress("default");
		billEntity.setPaymentMethod("default");
		billEntity.setPhone("default");
		billEntity.setStatusShip("default");
		billEntity.setStatusPay("default");
		billEntity.setStatusShip("default");
		model.addAttribute("bill", billEntity);
		
		return "billform";
	}
	
	@PostMapping("/admin/bill/save")
	public String saveBill(@Valid BillEntity billEntity, Authentication authentication) {
		if (billEntity != null) {
			if (billEntity.getCreatedDay() == null) {
				billEntity.setCreatedDay(new Date());
			}
			if (billEntity.getUser()== null) {
				UserEntity userEntity = userService.findByUsername(authentication.getName());
				billEntity.setUser(userEntity);
			}
			billService.saveBill(billEntity);
		}
		return "redirect:/admin/bill";
	}
	
	@GetMapping("/admin/product/add")
	public String viewFormProdctAdd(Model model) {
		List<CategoryEntity> listCategoryEntity = categoryService.findAll();
		model.addAttribute("category", listCategoryEntity);
		model.addAttribute("dc",new categoryAndProduct());
		return "productform";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String viewFormProductUpdate(@PathVariable("id") long id, Model model) {
		model.addAttribute("product",productService.findProductById(id));
		return "productform";
	}
	
	@PostMapping("/admin/product/save")
	public String saveProduct(@ModelAttribute("dc")categoryAndProduct cap) {
		System.out.println(cap.toString());

		return "redirect:/admin/product";
	}
}
