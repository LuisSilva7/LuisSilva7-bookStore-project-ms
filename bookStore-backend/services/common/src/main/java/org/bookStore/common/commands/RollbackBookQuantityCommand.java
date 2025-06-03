package org.bookStore.common.commands;

import org.bookStore.common.dto.CreateOrderDetailsRequest;

import java.util.List;

public record RollbackBookQuantityCommand(
        String orderId,
        Long userId,
        List<CreateOrderDetailsRequest> books
) {
}
