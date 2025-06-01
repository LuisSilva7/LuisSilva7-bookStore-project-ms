package org.bookStore.book.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookQuantityCommand {
    private String orderId;
    private String userId;
}
