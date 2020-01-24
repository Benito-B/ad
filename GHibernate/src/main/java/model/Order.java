package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "pedido")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "fecha")
	private LocalDateTime date = LocalDateTime.now();
	@Column(name = "importe")
	private BigDecimal total = BigDecimal.ZERO;
	@ManyToOne
	@JoinColumn(name = "cliente")
	private Client client;
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderLine> orderLines = new ArrayList<>();


	private Order() {}
	
	public Order(Client client) {
		this.client = client;
	}


	public Long getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	//Gracias Luis por estas anotaciones, super útiles y no las conocía :D
	@PrePersist
	@PreUpdate
	private void preGetImporte() {
		total = BigDecimal.ZERO;
		for (OrderLine orderLine : orderLines)
			total = total.add(orderLine.getImporte());
	}

	public BigDecimal getTotal() {
		preGetImporte();
		return total;
	}

	public Client getClient() {
		return client;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
}
