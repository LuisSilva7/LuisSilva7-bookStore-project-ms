package org.bookStore.cart.cartItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.bookStore.cart.cart.Cart;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

    @Column
    private Double unitPrice;

    @Column
    private Double subTotal;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "book_id")
    private Long bookId;

    @Transient
    public Double getSubTotal() {
        return unitPrice * quantity;
    }
}
