package spring.Controller;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.Entity.CartEntity;
import spring.Entity.UserEntity;
import spring.Service.CartService;
import spring.Service.UserService;

@Controller
public class UserController {

	private UserService userService;

	private final CartService cartService;

	public UserController(CartService cartService) {
		this.cartService = cartService;
	}

	@Autowired
	public UserController(UserService userService, CartService cartService) {
		this.userService = userService;
		this.cartService = cartService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		UserEntity userEntity = new UserEntity();
		model.addAttribute("userEntity", userEntity);
		return "register";
	}

	@PostMapping("/register")
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
		return "redirect:/login";
	}

}