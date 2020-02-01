package model;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.*;

@Entity
@Table(name = "pedidolinea")
public class OrderLine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "pedido")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "articulo")
	private Article article;
	@Column(name = "precio")
	private BigDecimal price = BigDecimal.ZERO;
	@Column(name = "unidades")
	private BigDecimal amount = BigDecimal.ONE;
	@Column(name = "importe")
	private BigDecimal total = BigDecimal.ZERO;
	
	private OrderLine() {}
	
	public OrderLine(Order order) {
		this.order = order;
		if(order.getOrderLines() == null)
			order.setOrderLines(new ArrayList<>());
		order.getOrderLines().add(this);
	}
	
	public Long getId() {
		return id;
	}
	public Order getOrder() {
		return order;
	}
	public Article getArticulo() {
		return article;
	}

	public void setArticulo(Article articulo) {
		price = articulo.getPrice();
		this.article = articulo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@PrePersist
	@PreUpdate
	private void preGetImporte() {
		System.out.println("PRICE" + price.toString());
		System.out.println("AMOUNT: " + amount.toString());
		total = price.multiply(amount);
	}
	public BigDecimal getImporte() {
		preGetImporte();
		return total;
	}
}
