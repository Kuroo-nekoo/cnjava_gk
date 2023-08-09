package spring.Service;

import java.util.List;
import java.util.Optional;

import spring.Entity.ProductEntity;

public interface ProductService {

	ProductEntity addProduct(ProductEntity productEntity);

	List<ProductEntity> findAllProduct();

	ProductEntity updateProduct(ProductEntity productEntity, Long id);

	ProductEntity deleteProductById(Long id);

	List<ProductEntity> findAllProductWithCategory();

	Optional<ProductEntity> findProductById(Long id);

	List<ProductEntity> searchProducts(String searchTerm);

	List<ProductEntity> searchProduct (String keyword);
}
