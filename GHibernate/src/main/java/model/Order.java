package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "pedido")
public class Order implements EditableItem{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "fecha")
	private LocalDateTime date;
	@Column(name = "importe")
	private BigDecimal total = BigDecimal.ZERO;
	@ManyToOne
	@JoinColumn(name = "cliente")
	private Client client;
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderLine> orderLines = new ArrayList<>();


	public Order() {}

	public Long getId() {
		return id;
	}

	public void setDate(LocalDateTime date){
		this.date = date;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

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

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines){
		this.orderLines = orderLines;
	}
}
