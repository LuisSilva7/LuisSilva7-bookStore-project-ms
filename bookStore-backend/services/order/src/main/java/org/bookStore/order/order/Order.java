package org.bookStore.order.order;

import jakarta.persistence.*;
import lombok.*;
import org.bookStore.order.orderDetails.OrderDetails;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime orderDate;

    @Column
    private double totalPrice;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "shipping_order_id")
    private Long shippingOrderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;
}
