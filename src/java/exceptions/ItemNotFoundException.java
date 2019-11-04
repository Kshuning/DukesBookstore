package exceptions;

import java.sql.SQLException;

public class ItemNotFoundException extends SQLException {
    public ItemNotFoundException(String exceptionMsg) {
        super(exceptionMsg);
    }
}
