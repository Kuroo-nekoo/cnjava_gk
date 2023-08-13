package spring.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.micrometer.core.instrument.search.Search;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import spring.Dto.ProductSpecification;
import spring.Dto.SearchCriteria;
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

	@Override
	public List<String> findProductBrand() {
		return productRepo.findDistinctBrand();
	}

	@Override
	public List<String> findProductColor() {
		return productRepo.findDistinctColor();
	}

	@Override
	public List<ProductEntity> filter(String filter) {
		var tabs = List.of(filter.split(";"));

		System.out.println("tabs: " + tabs);
		List<SearchCriteria> searchCriteria = new ArrayList<>();
		tabs.forEach(tab -> {
			var key = tab.split(":")[0];
			var value = tab.split(":")[1];

			switch (key) {
				case "brands" -> {
					var brands = value.replace("%20", " ").split(",");
					for (var brand : brands) {
						searchCriteria.add(SearchCriteria.builder().key("brand").operation(":").value(brand).build());
					}
				}
				case "colors" -> {
					var colors = value.split(",");
					for (var color : colors) {
						searchCriteria.add(SearchCriteria.builder().key("color").operation(":").value(color).build());
					}
				}
				case "price" -> {
					if (value.contains("-")) {
//						two case -153 or 0-300
						var prices = value.split("-");
						if (value.startsWith("-")) {
							System.out.println("co 1 gia tri maxPrice" + "; value: " + value);
							searchCriteria.add(SearchCriteria.builder().key("price").operation("<").value(Double.parseDouble(prices[1])).build());
						} else if (value.endsWith("-")) {
							System.out.println("co 1 gia tri minPrice" + "; value: " + value);
							searchCriteria.add(SearchCriteria.builder().key("price").operation(">").value(Double.parseDouble(prices[0])).build());
						} else {
							System.out.println("co 2 gia tri minPrice va maxPrice" + "; value: " + value);
							searchCriteria.add(SearchCriteria.builder().key("price").operation("><").value(Double.parseDouble(prices[0])).secondValue(Double.parseDouble(prices[1])).build());
						}
					} else {
						System.out.println("co 1 gia tri minPrice" + "; value: " + value);
						searchCriteria.add(SearchCriteria.builder().key("price").operation(">").value(value).build());
					}
				}
				default -> {}
			}
		});

		Specification<ProductEntity> spec = Specification.where(null);

		for (var criteria : searchCriteria) {
			if (criteria.getOperation().equalsIgnoreCase(":")) {
				spec = spec.or(new ProductSpecification(criteria));
			} else if (criteria.getOperation().equalsIgnoreCase("><")) {
				spec = spec.and(new ProductSpecification(criteria));
			} else if (criteria.getOperation().equalsIgnoreCase(">")) {
				spec = spec.and(new ProductSpecification(criteria));
			} else if (criteria.getOperation().equalsIgnoreCase("<")) {
				spec = spec.and(new ProductSpecification(criteria));
			}
		}

		return productRepo.findAll(spec);
	}
}