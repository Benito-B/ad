package model;

import javax.persistence.*;
import java.util.List;
//Uso un entityGraph para indicar que quiero cargar los pedidos al sacar el cliente para evitar el lazy loading
@NamedEntityGraph(name="fullClient", attributeNodes={
        @NamedAttributeNode("orders")
})
@Entity
@Table(name = "users")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;
    @Column(name="name")
    private String username;
    @OneToMany(targetEntity = Order.class)
    @JoinColumn(name="client_id")
    private List<Order> orders;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
