package spring.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
		
	@ManyToOne
	@JoinColumn(name="cart_id", nullable=false)
	@JsonBackReference
	private CartEntity cart;

	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private ProductEntity product;

	private Long quantity;
}
