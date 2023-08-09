package spring.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="detail_bill")
public class DetailBillEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="bill_id",nullable=false)
	private BillEntity bill;
	
	@ManyToOne
	@JoinColumn(name="product_id",nullable=false)
	private ProductEntity product;
	
	private Long quantity;
}
