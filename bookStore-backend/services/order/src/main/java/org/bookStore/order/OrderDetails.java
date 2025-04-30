package org.bookStore.order;

import jakarta.persistence.*;
import lombok.*;

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
    private Long orderDetailsID;

    @Column
    private int quantity;

    @Column
    private double subTotal;

    @Column
    private Long bookId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
