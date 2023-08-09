package spring.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import spring.Entity.CartEntity;
import spring.Entity.OrderEntity;
import spring.Entity.ProductEntity;
import spring.Entity.UserEntity;
import spring.Service.CartService;
import spring.Service.OrderService;
import spring.Service.ProductService;
import spring.Service.UserService;

@Controller
public class CartController {

	private final CartService cartService;
	
	private final UserService userService;
	
	private final OrderService orderService;
	
	private final ProductService productService;
	
	List<OrderEntity> listCarts = new ArrayList<OrderEntity>();
	Double totalPrice = 0.0;
	Long totalItem = (long) 0;

	public CartController(CartService cartService, UserService userService, OrderService orderService, ProductService productService) {
		this.cartService = cartService;
		this.userService = userService;
		this.orderService = orderService;
		this.productService = productService;
	}

	private void getAllProductInCart(Authentication authentication) {
		UserEntity userEntity = userService.findByUsername(authentication.getName());
		CartEntity cartEntity = cartService.findByUserId(userEntity.getId());
		listCarts = orderService.findAllByCartId(cartEntity.getId());
		totalPrice = 0.0;
		totalItem = (long) 0;
		if (listCarts != null) {
			for (OrderEntity dc : listCarts) {
				totalItem += dc.getQuantity();
				totalPrice += (double) dc.getQuantity() * dc.getProduct().getPrice();
			}			
		}
	}
	
	@GetMapping("/cart")
	public String showCart(Model model, Authentication authentication) {
		listCarts = null;
	    getAllProductInCart(authentication);
//		check cart is empty
		if (listCarts.size() < 1) {
			listCarts = null;
		}
		model.addAttribute("listProducts", listCarts);
		model.addAttribute("totalPrice", "$" + totalPrice);
		model.addAttribute("totalItem", totalItem);
		model.addAttribute("username", "Hi " + authentication.getName());
		return "/cart";
	}
	
	@GetMapping("/home/add/{id}")
	public String addItemCart(@PathVariable("id") long id, Model mode, Authentication authentication) {
		if (listCarts.size() < 1) 
		{getAllProductInCart(authentication);}
		if (listCarts != null) {
			for (OrderEntity dc : listCarts) {
				if (dc.getProduct().getId() == id) {
					dc.setQuantity(dc.getQuantity() + 1);
					orderService.addOrder(dc);
					return "redirect:/";
				}			
		}}
		UserEntity userEntity = userService.findByUsername(authentication.getName());
		CartEntity cartEntity = cartService.findByUserId(userEntity.getId());
		OrderEntity dc = new OrderEntity();
		ProductEntity p = productService.findProductById(id).get();
		dc.setCart(cartEntity);
		dc.setProduct(p);
		dc.setQuantity((long)1);
		orderService.addOrder(dc);
		return "redirect:/";
	}
	
	@GetMapping("/cart/add/{id}")
	public String addItemCartCartpage(@PathVariable("id") long id, Model mode, Authentication authentication) {
		if (listCarts.size() < 1) 
		{getAllProductInCart(authentication);}
		if (listCarts != null) {
			for (OrderEntity dc : listCarts) {
				if (dc.getProduct().getId() == id) {
					dc.setQuantity(dc.getQuantity() + 1);
					orderService.addOrder(dc);
					return "redirect:/cart";
				}			
		}}
		UserEntity userEntity = userService.findByUsername(authentication.getName());
		CartEntity cartEntity = cartService.findByUserId(userEntity.getId());
		OrderEntity dc = new OrderEntity();
		ProductEntity p = productService.findProductById(id).get();
		dc.setCart(cartEntity);
		dc.setProduct(p);
		dc.setQuantity((long)1);
		orderService.addOrder(dc);

		return "redirect:/cart";
	}
	
	@GetMapping("/cart/minus/{id}")
	public String minusItemCart(@PathVariable("id") long id, Model mode, Authentication authentication) {
		System.out.println("cart size" + listCarts.size());
		if (listCarts.size() < 1) 
		{getAllProductInCart(authentication);}
		for (OrderEntity dc : listCarts) {
			System.out.println(dc.getProduct().getId()+" "+id);
			if (dc.getProduct().getId() == id) {
				System.out.println(dc.getQuantity());
				System.out.println(dc.getProduct().getName());
				if (dc.getQuantity() >= 1) {
					dc.setQuantity(dc.getQuantity() - 1);
					orderService.addOrder(dc);
				} else if (dc.getQuantity() == 0) {
					orderService.deleteById(dc.getId());
				}
				totalPrice -= dc.getProduct().getPrice();
				totalItem -= 1 ;
				return "redirect:/cart";
			}
		}
		return "redirect:/cart";
	}
	
	private Long total = (long) 0;
	private Double price = (double) 0;
	
	public Double getPriceProduct(Long id, List<ProductEntity> p) {
		for (ProductEntity productEntity : p) {
			if (productEntity.getId() == id) {
				return productEntity.getPrice();
			}
		}
		return (double) 0;
	}
	
	public void calPrice_Total(List<OrderEntity> items, List<ProductEntity> productEntity) {
		
		for (OrderEntity dc: items) {
			total += dc.getQuantity();
			ProductEntity p = new ProductEntity();
			p = dc.getProduct();
			price += dc.getQuantity() * getPriceProduct(p.getId(), productEntity);
		}
	}
	
	public boolean checkProductInCart(Long id, List<ProductEntity> p) {
		for (ProductEntity productEntity : p) {
			if (productEntity.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public List<ProductEntity> getProductsCart(List<OrderEntity> items, List<ProductEntity> productEntities)
	{
		List<ProductEntity> p = new ArrayList<>();
		ProductEntity productEntity = new ProductEntity();
		for (OrderEntity dc: items) {
			productEntity = dc.getProduct();
			System.out.println(productEntity.getId());
			if (checkProductInCart(productEntity.getId(), productEntities)) {
				p.add(productEntity);
			}
		}
		return p;
	}
	
	
	

}
