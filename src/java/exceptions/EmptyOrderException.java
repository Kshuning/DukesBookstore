package exceptions;

public class EmptyOrderException extends IllegalArgumentException {
    public EmptyOrderException() {
        super("Cannot create order with no parts.");
    }
}
