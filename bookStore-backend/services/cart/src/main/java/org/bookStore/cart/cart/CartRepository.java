package org.bookStore.cart.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart getCartIDByUserID(Long userID);
}
