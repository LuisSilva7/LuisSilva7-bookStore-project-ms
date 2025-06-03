package org.bookStore.order_query.book;

import jakarta.persistence.*;
import lombok.*;
import org.bookStore.order_query.order.Order;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class BookItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;
    private Double unitPrice;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id_fk")
    private Order order;
}
