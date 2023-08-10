package spring.Entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill")
public class BillEntity {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private Date createdDay;
	private String statusPay;
	private String statusShip;
	private String  paymentMethod;
	private String name;
	private String phone;
	private String address;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private UserEntity user;
	
	@OneToMany(mappedBy="bill", cascade = CascadeType.ALL)
	private Set<DetailBillEntity> detailBills;
	

}
