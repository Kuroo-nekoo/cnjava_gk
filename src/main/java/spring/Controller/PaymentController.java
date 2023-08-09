package spring.Controller;


import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import spring.Entity.*;
import spring.Entity.CartEntity;
import spring.Service.BillService;
import spring.Service.CartService;
import spring.Service.DetailBillService;
import spring.Service.OrderService;
import spring.Service.UserService;

@Controller
public class PaymentController {

	private final CartService cartService;

	private final UserService userService;

	private final OrderService orderService;
	
	private final BillService billService;
	
	private final DetailBillService detailBillSerivce;

	private List<OrderEntity> listCarts;
	Double totalPrice;
	Long totalItem;

	public PaymentController(CartService cartService, UserService userService, OrderService orderService, BillService billService, DetailBillService detailBillSerivce) {
		this.cartService = cartService;
		this.userService = userService;
		this.orderService = orderService;
		this.billService = billService;
		this.detailBillSerivce = detailBillSerivce;
	}

	private void getListItemsInCart(String username) {
		UserEntity userEntity = userService.findByUsername(username);
		CartEntity cartEntity = cartService.findByUserId(userEntity.getId());
		listCarts = orderService.findAllByCartId(cartEntity.getId());
		for (OrderEntity dc : listCarts) {
			totalItem += dc.getQuantity();
			totalPrice += (double) dc.getQuantity() * dc.getProduct().getPrice();
		}
	}

	@GetMapping("/payment")
	public String viewPayment(Model model, Authentication authentication) {

		listCarts = null;
		totalPrice = 0.0;
		totalItem = (long) 0;

		getListItemsInCart(authentication.getName());

		BillEntity billEntity = new BillEntity();
		model.addAttribute("bill", billEntity);
		model.addAttribute("listProducts", listCarts);
		model.addAttribute("totalPrice", "$" + totalPrice);
		model.addAttribute("totalItem", totalItem);
		model.addAttribute("discountPrice", "- $0");
		model.addAttribute("user", "Hi " + authentication.getName());
		return "payment";
	}

	@PostMapping("/payment/user/add")
	public String addPatment(@ModelAttribute("bill") BillEntity bill,
			Authentication authentication) {
		UserEntity userEntity = userService.findByUsername(authentication.getName());
		CartEntity cartEntity = cartService.findByUserId(userEntity.getId());
		
		Date date = new Date();

		bill.setCreatedDay(date);
		bill.setStatusPay("unpaid");
		bill.setStatusShip("shipping");
		bill.setUser(userEntity);
		
		billService.saveBill(bill);
		
		List<OrderEntity> detailcarts = orderService.findAllByCartId(cartEntity.getId());
		if (detailcarts != null) {
			for (OrderEntity dc : detailcarts) {
				DetailBillEntity detailBillEntity = new DetailBillEntity();
				detailBillEntity.setQuantity(dc.getQuantity());
				detailBillEntity.setProduct(dc.getProduct());
				detailBillEntity.setBill(bill);
				detailBillSerivce.saveDetailBill(detailBillEntity);
				orderService.deleteById(dc.getId());
			}			
		}
		
			
		System.out.println(date);
		System.out.println(bill.getId());
		return "paymentsucess";
	}
}
