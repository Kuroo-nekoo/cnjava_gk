package spring.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.Controller.api.OrderControllerAPI;
import spring.Entity.OrderEntity;
import spring.Service.OrderService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class OrderControllerAPITest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderControllerAPI orderControllerAPI;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderControllerAPI = new OrderControllerAPI(orderService);
        mockMvc = MockMvcBuilders.standaloneSetup(orderControllerAPI).build();
    }

    @Test
    public void findAllOrder_ShouldReturnAllOrder() throws Exception {
        List<OrderEntity> j = List.of(new OrderEntity());
        when(orderService.findAll()).thenReturn(j);

        MvcResult mvcResult = mockMvc.perform(get("/api/order")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("application/json", mvcResult.getResponse().getContentType());
        verify(orderService, times(1)).findAll();
    }

    @Test
    public void findOrderById_ShouldReturnOrderWithThatId() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        when(orderService.findById(1L)).thenReturn(orderEntity);

        MvcResult mvcResult = mockMvc.perform(get("/api/order/1")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("application/json", mvcResult.getResponse().getContentType());
        verify(orderService, times(1)).findById(1L);
    }

    @Test
    public void addOrder_ShouldReturnSavedOrder() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        when(orderService.addOrder(orderEntity)).thenReturn(orderEntity);

        MvcResult mvcResult = mockMvc.perform(post("/api/order/")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("application/json", mvcResult.getResponse().getContentType());
        verify(orderService, times(1)).addOrder(orderEntity);
    }

    @Test
    public void deleteOrder_ShouldReturnDeletedOrder() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        when(orderService.deleteById(1L)).thenReturn(orderEntity);

        MvcResult mvcResult = mockMvc.perform(delete("/api/order/1")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("application/json", mvcResult.getResponse().getContentType());
        verify(orderService, times(1)).deleteById(1L);
    }

    @Test
    public void updateOrder_ShouldReturnUpdatedOrder() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        when(orderService.updateOrder(orderEntity, 1L)).thenReturn(orderEntity);

        MvcResult mvcResult = mockMvc.perform(put("/api/order/1")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("application/json", mvcResult.getResponse().getContentType());
        verify(orderService, times(1)).updateOrder(orderEntity, 1L);
    }
}
