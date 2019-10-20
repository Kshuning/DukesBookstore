package exceptions;

import java.sql.SQLException;

public class CustomerNotFoundException extends SQLException {
    public CustomerNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
