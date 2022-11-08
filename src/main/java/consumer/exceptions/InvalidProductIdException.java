package consumer.exceptions;

import java.util.UUID;

public class InvalidProductIdException extends Exception {
    public InvalidProductIdException(UUID id) {
        super("Invalid product id: " + id);
    }
}
