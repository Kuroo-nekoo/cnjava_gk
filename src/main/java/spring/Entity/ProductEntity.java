package spring.Entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="product")
@Data
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private String description;
	private String img;
	private double price;
	private String color;
	private String brand;
	
	@OneToMany(mappedBy="product", cascade = CascadeType.ALL)
	private Set<OrderEntity> orders;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<DetailCategoryEntity> detailCategoryEntity;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<DetailBillEntity> detailBillEntity;
}
