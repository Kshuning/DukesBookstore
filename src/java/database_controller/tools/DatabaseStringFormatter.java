package database_controller.tools;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for formatting Database results as Strings.
 * Provides methods to format dates and currency and Table names.
 *
 * TODO: This is just a shell ported from another project.
 * The viability and usefulness of the methods needs to be determined.
 * Additional methods may be implemented as needed, or some may be removed altogether.
 */
public class DatabaseStringFormatter {

    private DatabaseStringFormatter() {} // utility class, do not instantiate

    /**
     * Formats a result from a specific element of the result set given a column index.
     * Uses the default Locale provided by the machine that runs this method.
     * @param resultSet The results from the database.
     * @param columnIndex The column to work on.
     * @return A formatted String of the data.
     * @throws SQLException if the result set is bad. Should never happen.
     */
    public static String formatDatabaseResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return formatDatabaseResults(resultSet, columnIndex, Locale.getDefault());
    }

    /**
     * Formats a result from a specific element of the result set given a column index.
     * @param resultSet The results from the database.
     * @param columnIndex The column to work on.
     * @param locale The Locale of the requesting information.
     * @return A formatted String of the data.
     * @throws SQLException if the result set is bad. Should never happen.
     */
    public static String formatDatabaseResults(ResultSet resultSet, int columnIndex, Locale locale) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        String typeName = rsmd.getColumnTypeName(columnIndex);
        if (typeName.equals("TIMESTAMP")) {
            Date date = resultSet.getDate(columnIndex);
            return (!(date == null)) ? formatDate(date, locale) : "NULL";
        }
        if (typeName.equals("DECIMAL")) {
            Double currency = resultSet.getDouble(columnIndex);
            return formatCurrency(currency, locale);
        }
        String varChar = resultSet.getString(columnIndex);
        return (!(varChar == null)) ? varChar : "NULL";
    }

    /**
     * Formats a date from the Database as a short form Date in the default locale.
     * @param date Date object to format.
     * @return Formatted String containing the date.
     */
    private static String formatDate(Date date, Locale locale) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        return dateFormat.format(date);
    }

    /**
     * Formats Currency from the database in the default locale.
     * @param currency Double amount returned from the Database.
     * @return Formatted String containing the currency amount.
     */
    private static String formatCurrency(Double currency, Locale locale) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(currency);
    }

    /**
     * Formats a Table name, enclosing it in brackets for use in SQL queries if
     * it contains a whitespace character.
     * @param tableName The table name to check for formatting
     * @return A proper table name that can be used for queries
     */
    public static String formatTableNameForQuery(String tableName) {
        if (containsWhitespace(tableName)) {
            return "[" + tableName + "]";
        }
        return tableName;
    }

    /**
     * Helper function to determine if a string has white space inside.
     * @param str String to check
     * @return True if there is any whitespace character found in the string.
     */
    private static boolean containsWhitespace(String str) {
        if (str != null) {
            for (Character c : str.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    return true;
                }
            }
        }
        return false;
    }
}

