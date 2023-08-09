package spring.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import spring.Entity.ProductEntity;
import spring.Repository.ProductRepository;

@Service
public class ProductServiceImp implements ProductService {

	private final ProductRepository productRepo;

	public ProductServiceImp(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	@Override
	public ProductEntity addProduct(ProductEntity productEntity) {
		return productRepo.save(productEntity);
	}

	@Override
	public List<ProductEntity> findAllProduct() {
		return productRepo.findAll();
	}

	@Override
	public ProductEntity updateProduct(ProductEntity productEntity, Long id) {
		Optional<ProductEntity> product = productRepo.findById(id);
		if (product.isEmpty()) {
			throw new RuntimeException("Not found");
		} else {
			ProductEntity p = product.get();
			p.setName(productEntity.getName());
			p.setPrice(productEntity.getPrice());
			p.setDescription(productEntity.getDescription());
			p.setImg(productEntity.getImg());
			return productRepo.save(p);
		}
	}

	@Override
	public ProductEntity deleteProductById(Long id) {
		Optional<ProductEntity> product = productRepo.findById(id);
		if (product.isEmpty()) {
			return null;
		} else {
			ProductEntity p = product.get();
			productRepo.deleteById(id);
			return p;
		}
	}

	@Override
	public List<ProductEntity> findAllProductWithCategory() {
		return null;
	}


	@Override
	public Optional<ProductEntity> findProductById(Long id) {
		return productRepo.findById(id);
	}

	@Override
	public List<ProductEntity> searchProducts(String searchTerm) {
		return productRepo.findByNameContaining(searchTerm);
	}

	@Override
	public List<ProductEntity> searchProduct(String keyword) {
		return productRepo.searchProduct(keyword);
	}
}