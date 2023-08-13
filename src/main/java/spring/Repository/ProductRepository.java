package spring.Repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.Entity.ProductEntity;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByNameContaining(String searchTerm);

    @Query(value = "SELECT p.* FROM product p, detail_category dc, category c WHERE p.id = dc.id AND dc.id = c.id AND CONCAT(p.description,' ',p.name,' ',p.price,' ',c.name,' ') like '%?1%'", nativeQuery = true)
    List<ProductEntity> searchProduct(String keyword);

    @Query(value = "SELECT DISTINCT p.brand FROM ProductEntity p")
    List<String> findDistinctBrand();

    @Query(value = "SELECT DISTINCT p.color FROM ProductEntity p")
    List<String> findDistinctColor();

    List<ProductEntity> findAll(Specification<ProductEntity> specification);
}