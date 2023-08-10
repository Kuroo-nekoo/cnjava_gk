package spring.Entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name="cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
	private UserEntity user;
	
	@OneToMany(mappedBy="cart", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<OrderEntity> order;
}