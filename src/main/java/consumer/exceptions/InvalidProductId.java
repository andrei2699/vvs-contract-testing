package consumer.exceptions;

import java.util.UUID;

public class InvalidProductId extends Exception {
    public InvalidProductId(UUID id) {
        super("Invalid product id: " + id);
    }
}
