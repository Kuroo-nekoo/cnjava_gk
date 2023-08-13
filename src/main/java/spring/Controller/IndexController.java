package spring.Controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import spring.Entity.CartEntity;
import spring.Entity.OrderEntity;
import spring.Entity.ProductEntity;
import spring.Entity.UserEntity;
import spring.Service.*;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    private final OrderService orderService;

    private final CartService cartService;

    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping({"", "/"})
    public String index(
            Model model,
            Authentication authentication,
            @RequestParam(name = "filter" ,value = "filter", required = false) String filter
    ) {
        List<ProductEntity> listProductEntities = null;
        if (filter != null) {
            listProductEntities = productService.filter(filter);
        } else {
            listProductEntities = productService.findAllProduct();
        }
        System.out.println("listProductEntities: " + listProductEntities.size());
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
            model.addAttribute("role", "admin");
        }
        ;

        model.addAttribute("totalPrice", "$" + price);
        model.addAttribute("totalItem", totalQuantity);
        model.addAttribute("username", "Hi " + authentication.getName());
        model.addAttribute("listProducts", listProductEntities);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", productService.findProductBrand());
        model.addAttribute("colors", productService.findProductColor());
        return "index";
    }
}
