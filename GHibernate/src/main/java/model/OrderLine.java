package model;

import java.math.BigDecimal;

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
	private BigDecimal ammount = BigDecimal.ONE;
	@Column(name = "importe")
	private BigDecimal total = BigDecimal.ZERO;
	
	private OrderLine() {}
	
	public OrderLine(Order order) {
		this.order = order;
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
		ammount = BigDecimal.ONE;
		this.article = articulo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}

	@PrePersist
	@PreUpdate
	private void preGetImporte() {
		total = price.multiply(ammount);
	}
	public BigDecimal getImporte() {
		preGetImporte();
		return total;
	}
}
