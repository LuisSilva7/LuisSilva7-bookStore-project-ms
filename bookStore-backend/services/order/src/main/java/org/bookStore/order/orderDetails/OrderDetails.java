package org.bookStore.order.orderDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.bookStore.order.order.Order;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id")
    private Long orderDetailsId;

    @Column
    private int quantity;

    @Column
    private double unitPrice;

    @Column
    private double subTotal;

    @Column
    private Long bookId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;
}
