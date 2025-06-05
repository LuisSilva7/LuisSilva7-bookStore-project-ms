package org.bookStore.order_query.order;

import jakarta.persistence.*;
import lombok.*;
import org.bookStore.common.utils.OrderStatus;
import org.bookStore.order_query.book.BookItem;

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
    private String orderId;

    @Column
    private LocalDateTime orderDate;

    @Column
    private Double totalPrice;

    @Column
    private OrderStatus status;

    @Column
    private Long userId;

    @Column
    private String firstname;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String postalCode;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookItem> bookItems;
}
