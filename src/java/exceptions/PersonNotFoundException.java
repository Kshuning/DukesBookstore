package exceptions;

import java.sql.SQLException;

public class PersonNotFoundException extends SQLException {
    public PersonNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
