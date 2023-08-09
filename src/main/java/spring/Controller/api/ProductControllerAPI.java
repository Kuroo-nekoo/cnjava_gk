package spring.Controller.api;

import org.springframework.web.bind.annotation.*;
import spring.Entity.ProductEntity;
import spring.Service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@ResponseBody
public class ProductControllerAPI {
    final
    ProductService productService;

    public ProductControllerAPI(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductEntity> findALl() {
        return productService.findAllProduct();
    }

    @GetMapping("/{id}")
    public ProductEntity findById(@PathVariable("id") Long id) {
        Optional<ProductEntity> product = productService.findProductById(id);
        if (product.isEmpty()) {
            throw new RuntimeException("Not found");
        } else {
            return product.get();
        }
    }

    @PostMapping
    public ProductEntity addProduct(@ModelAttribute ProductEntity productEntity) {
        return productService.addProduct(productEntity);
    }

    @PutMapping("/{id}")
    public ProductEntity updateProduct(@ModelAttribute ProductEntity productEntity, @PathVariable("id") Long id) {
        return productService.updateProduct(productEntity, id);
    }

    @DeleteMapping("{id}")
    public ProductEntity deleteProductById(@PathVariable("id") Long id) {
        return productService.deleteProductById(id);
    }
}
