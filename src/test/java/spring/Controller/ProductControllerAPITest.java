package spring.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.Controller.api.ProductControllerAPI;
import spring.Entity.ProductEntity;
import spring.Service.ProductService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class ProductControllerAPITest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductControllerAPI productControllerAPI;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productControllerAPI = new ProductControllerAPI(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productControllerAPI).build();
    }

    @Test
    public void findAllProduct_ShouldReturnAllProduct() throws Exception {
        List<ProductEntity> j = List.of(new ProductEntity());
        when(productService.findAllProduct()).thenReturn(j);

        MvcResult mvcResult = mockMvc.perform(get("/api/product")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("application/json", mvcResult.getResponse().getContentType());
        verify(productService, times(1)).findAllProduct();
    }

    @Test
    public void findProductById_ShouldReturnProductWithThatId() throws Exception {
        ProductEntity productEntity = new ProductEntity();
        when(productService.findProductById(1L)).thenReturn(Optional.of(productEntity));

        MvcResult mvcResult = mockMvc.perform(get("/api/product/1")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("application/json", mvcResult.getResponse().getContentType());
        verify(productService, times(1)).findProductById(1L);
    }

    @Test
    public void deleteProduct_ShouldReturnDeletedProduct() throws Exception {
        ProductEntity productEntity = new ProductEntity();
        when(productService.deleteProductById(1L)).thenReturn(productEntity);

        MvcResult mvcResult = mockMvc.perform(delete("/api/product/1")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("application/json", mvcResult.getResponse().getContentType());
        verify(productService, times(1)).deleteProductById(1L);
    }

    @Test
    public void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        ProductEntity productEntity = new ProductEntity();
        when(productService.updateProduct(productEntity, productEntity.getId())).thenReturn(productEntity);

        MvcResult mvcResult = mockMvc.perform(put("/api/product/1")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("application/json", mvcResult.getResponse().getContentType());
        verify(productService, times(1)).updateProduct(productEntity, productEntity.getId());
    }
}
