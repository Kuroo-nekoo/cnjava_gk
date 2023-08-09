package spring.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spring.Entity.ProductEntity;
import spring.Repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImp(productRepository);
    }

    @Test
    public void addProduct_ShouldReturnSavedProduct() {
        // Arrange
        ProductEntity productEntity = new ProductEntity();
        when(productRepository.save(productEntity)).thenReturn(productEntity);

        // Act
        ProductEntity savedProduct = productService.addProduct(productEntity);

        // Assert
        assertEquals(productEntity, savedProduct);
        verify(productRepository, Mockito.times(1)).save(productEntity);
    }

    @Test
    public void findAllProduct_ShouldReturnAllProduct() {
        // Arrange
        List<ProductEntity> j = List.of(new ProductEntity());
        when(productRepository.findAll()).thenReturn(j);

        // Act
        productService.findAllProduct();

        // Assert
        assertEquals(j, productRepository.findAll());
        verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
public void updateProduct_ShouldReturnUpdatedProduct() {
        // Arrange
        ProductEntity productEntity = new ProductEntity();
        when(productRepository.getById(1L)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);

        // Act
        ProductEntity updatedProduct = productService.updateProduct(productEntity, 1L);

        // Assert
        assertEquals(productEntity, updatedProduct);
        verify(productRepository, Mockito.times(1)).getById(1L);
        verify(productRepository, Mockito.times(1)).save(productEntity);
    }

    @Test
    public void deleteProductById_ShouldReturnDeletedProduct() {
        // Arrange
        ProductEntity productEntity = new ProductEntity();
        when(productRepository.getById(1L)).thenReturn(productEntity);

        // Act
        ProductEntity deletedProduct = productService.deleteProductById(1L);

        // Assert
        assertEquals(productEntity, deletedProduct);
        verify(productRepository, Mockito.times(1)).getById(1L);
        verify(productRepository, Mockito.times(1)).deleteById(1L);
    }
}
