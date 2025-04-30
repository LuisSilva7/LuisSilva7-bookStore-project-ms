package org.bookStore.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    //Cart getCartIdByUserId(Long userId);
}
