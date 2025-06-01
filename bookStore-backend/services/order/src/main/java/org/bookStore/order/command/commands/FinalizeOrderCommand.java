package org.bookStore.order.command.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class FinalizeOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String userId;
}

