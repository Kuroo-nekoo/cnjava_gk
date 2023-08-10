package spring.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
    private Long cart_id;
    private Long product_id;
    private Long quantity;

}
