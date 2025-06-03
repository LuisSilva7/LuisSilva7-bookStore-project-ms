package org.bookStore.order.orderDetails;

import org.bookStore.common.utils.CreateOrderDetailsRequest;
import org.bookStore.order.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsMapper {

    public static OrderDetails toOrderDetails(CreateOrderDetailsRequest request, Order order) {
        return OrderDetails.builder()
                .bookId(request.bookId())
                .quantity(request.quantity())
                .unitPrice(request.unitPrice())
                .subTotal(request.quantity() * request.unitPrice())
                .order(order)
                .build();
    }

    public static OrderDetailsResponse toOrderDetailsResponse(OrderDetails details) {
        return new OrderDetailsResponse(
                details.getId(),
                details.getQuantity(),
                details.getUnitPrice(),
                details.getSubTotal(),
                details.getBookId()
        );
    }
}
