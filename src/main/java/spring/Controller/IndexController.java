package spring.Controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import spring.Entity.CartEntity;
import spring.Entity.OrderEntity;
import spring.Entity.ProductEntity;
import spring.Entity.UserEntity;
import spring.Service.CartService;
import spring.Service.OrderService;
import spring.Service.ProductService;
import spring.Service.UserService;

@Controller
public class IndexController {

	private final UserService userService;

	final
	OrderService orderService;

	private final CartService cartService;

	private final ProductService productService;

	public IndexController(UserService userService, OrderService orderService, CartService cartService, ProductService productService) {
		this.userService = userService;
		this.orderService = orderService;
		this.cartService = cartService;
		this.productService = productService;
	}

	@GetMapping("/")
	public String index(Model model, Authentication authentication) {
		List<ProductEntity> listProductEntities = productService.findAllProduct();
		UserEntity userEntity = userService.findByUsername(authentication.getName());
		CartEntity cartEntity = cartService.findByUserId(userEntity.getId());
		Double price = 0.0;
		Long totalQuantity = (long) 0;
		List<OrderEntity> totalItem = orderService.findAllByCartId(cartEntity.getId());

		  if (totalItem != null) {
			  for (OrderEntity dc : totalItem) {
				  price += dc.getQuantity() * dc.getProduct().getPrice();
				  totalQuantity += 1;
			  }			  
		  }
		  
		  String role = authentication.getAuthorities().toArray()[0].toString();
		if (role.equalsIgnoreCase("ROLE_ADMIN")) {
			model.addAttribute("role","admin");
		};

		model.addAttribute("totalPrice", "$" + price);
		model.addAttribute("totalItem", totalQuantity);
		model.addAttribute("username", "Hi " + authentication.getName());
		model.addAttribute("listProducts", listProductEntities);
		return "index";
	}
}
