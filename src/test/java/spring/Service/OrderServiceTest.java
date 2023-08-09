package spring.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spring.Entity.OrderEntity;
import spring.Repository.OrderRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImp(orderRepository);
    }

    @Test
    public void addOrder_ShouldReturnSavedOrder() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        // Act
        OrderEntity savedOrder = orderService.addOrder(orderEntity);

        // Assert
        assertEquals(orderEntity, savedOrder);
        verify(orderRepository, Mockito.times(1)).save(orderEntity);
    }

    @Test
    public void findAllById_ShouldReturnAllOrder() {
        // Arrange
        List<OrderEntity> j = List.of(new OrderEntity());
        when(orderRepository.findAll()).thenReturn(j);

        // Act
        List<OrderEntity> allOrder = orderRepository.findAll();

        // Assert
        assertEquals(j, allOrder);
        verify(orderRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void deleteOrder_ShouldReturnDeletedOrder() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));

       // Act
        OrderEntity deletedOrder = orderService.deleteById(1L);

        // Assert
        assertEquals(orderEntity, deletedOrder);
        verify(orderRepository, Mockito.times(1)).findById(1L);
        verify(orderRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void updateOrder_ShouldReturnUpdatedOrder() {
        // Arrange
        OrderEntity orderEntity = new OrderEntity();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        // Act
        OrderEntity updatedOrder = orderService.updateOrder(orderEntity, 1L);

        // Assert
        assertEquals(orderEntity, updatedOrder);
        verify(orderRepository, Mockito.times(1)).findById(1L);
        verify(orderRepository, Mockito.times(1)).save(orderEntity);
    }
}
