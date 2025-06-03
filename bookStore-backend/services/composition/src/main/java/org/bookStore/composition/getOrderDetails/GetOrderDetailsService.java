package org.bookStore.composition.getOrderDetails;

import lombok.RequiredArgsConstructor;
import org.bookStore.composition.common.book.BookClient;
import org.bookStore.composition.common.book.BookResponse;
import org.bookStore.composition.getOrderDetails.order.OrderClient;
import org.bookStore.composition.getOrderDetails.order.OrderResponse;
import org.bookStore.composition.getOrderDetails.shippingOrder.GetShippingOrderRequest;
import org.bookStore.composition.getOrderDetails.shippingOrder.ShippingOrderClient;
import org.bookStore.composition.getOrderDetails.shippingOrder.ShippingOrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrderDetailsService {

    private final OrderClient orderClient;
    private final ShippingOrderClient shippingOrderClient;
    private final BookClient bookClient;


    public OrderDetailsResponse getOrderDetails(Long userId, String orderId) {
        OrderResponse order = orderClient.getOrderById(orderId).getData();

        ShippingOrderResponse shipping = shippingOrderClient
                .getShippingOrderByOrderId(new GetShippingOrderRequest(orderId))
                .getData();

        List<OrderDetailsItemResponse> items = order.orderDetails().stream()
                .map(detail -> {
                    BookResponse book = bookClient.getBookById(detail.bookId()).getData();
                    return new OrderDetailsItemResponse(
                            detail.id(),
                            detail.bookId(),
                            detail.unitPrice(),
                            detail.quantity()
                    );
                }).toList();

        return new OrderDetailsResponse(
                orderId,
                order.orderDate(),
                order.totalPrice(),
                userId,
                shipping.firstname(),
                shipping.address(),
                shipping.city(),
                shipping.postalCode(),
                items
        );
    }
}
