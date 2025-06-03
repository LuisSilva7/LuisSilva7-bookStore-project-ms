package org.bookStore.composition.getOrderDetails;

import lombok.RequiredArgsConstructor;
import org.bookStore.composition.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/composition/get-order-details")
@RequiredArgsConstructor
public class GetOrderDetailsController {

    private final GetOrderDetailsService getOrderDetailsService;

    @GetMapping
    public ResponseEntity<ApiResponse<OrderDetailsResponse>> getOrderDetails(
            @RequestHeader("x-userid") Long userId,
            @RequestBody GetOrderDetailsRequest request) {
        OrderDetailsResponse response = getOrderDetailsService.getOrderDetails(userId, request.orderId());

        return ResponseEntity.ok(new ApiResponse<>(
                "Obtained OrderDetails successfully!", response));
    }
}
