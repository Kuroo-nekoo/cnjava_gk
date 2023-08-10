package spring.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import spring.Entity.CartEntity;
import spring.Entity.OrderEntity;
import spring.Entity.ProductEntity;
import spring.Entity.UserEntity;

import spring.Service.CartService;
import spring.Service.OrderService;
import spring.Service.ProductService;
import spring.Service.UserService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;
	
	private final CartService cartService;
	
	private final UserService userService;
	
	private final OrderService orderService;

	public ProductController(ProductService productService, CartService cartService, UserService userService, OrderService orderService) {
		this.productService = productService;
		this.cartService = cartService;
		this.userService = userService;
		this.orderService = orderService;
	}

	@PostMapping("/add")
	public ProductEntity addProduct(@RequestBody ProductEntity product) {
		return productService.addProduct(product);
	}

	@GetMapping("/")
	public List<ProductEntity> findAllProduct() {
		return productService.findAllProduct();
	}

	@PutMapping("/update/{id}")
	public void updateProduct(@RequestBody ProductEntity productEntity, @PathVariable("id") Long id) {
		productService.updateProduct(productEntity, id);
	}

	@DeleteMapping("/{id}")
	public void deleteProductById(@PathVariable("id") Long id) {
		productService.deleteProductById(id);
	}

	@GetMapping("/{id}")
	public String showProductById(@PathVariable("id") long id, Model model, Authentication authentication) {
		Optional<ProductEntity> productEntity = productService.findProductById(id);
		System.out.println(productEntity.get());
		if (productEntity.isEmpty()) {
			throw new RuntimeException("Not found");
		} else {
			ProductEntity p = productEntity.get();
			UserEntity userEntity = userService.findByUsername(authentication.getName());
			CartEntity cartEntity = cartService.findByUserId(userEntity.getId());
			List<OrderEntity> listCarts = orderService.findAllByCartId(cartEntity.getId());

			int totalItem = 0;
			double totalPrice = 0.0;
			for (OrderEntity dc : listCarts) {
				totalItem += dc.getQuantity();
				totalPrice += (double) dc.getQuantity() * dc.getProduct().getPrice();
			}

			model.addAttribute("totalPrice", "$" + totalPrice);
			model.addAttribute("totalItem", totalItem);
			model.addAttribute("product", p);
			model.addAttribute("username", "Hi " + authentication.getName());
			return "productdetail";
		}
	}
	
	@PostMapping("/search")
	public String searchProduct(@ModelAttribute("keyword") String keyword, Model model) {
		List<ProductEntity> p = productService.searchProduct(keyword);
		model.addAttribute("listProducts",p);
		return "search";
	}
}
