package database_controller.table_models;

import database_controller.tools.DatabaseStringFormatter;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * TableData takes a ResultSet from a SQL query and deconstructs it into the pieces
 * needed to construct a table from the from the result set.
 * It gets the names of tables, columns, and column types and stores them in
 * Lists of Strings for easier handling by the GUI.
 * Data from the table is stored as a nested List with each inner list containing
 * individual elements a row of data and each outer List representing a full row.
 *
 * This class is mostly used as a wrapper for converting ResultSet data into an
 * iterable format for printing formatted Strings. It should make handling data
 * to be printed to screen, console, file, or whatever a little simpler to manage.
 *
 * It may seem unnecessary as we can just use ResultSet from the Database, however
 * this made implementing the Tables for viewing data considerably simpler.
 */
public class TableData implements Iterable<RowData> {
    private int numColumns;
    private List<String> tableNames = new ArrayList<>();
    private boolean multipleTablesUsedForData;
    private List<String> columnNames = new ArrayList<>();
    private List<String> columnDataTypes = new ArrayList<>();
    private List<RowData> rows = new ArrayList<>();

    public TableData(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        numColumns = rsmd.getColumnCount();

        for (int i = 1; i <= numColumns; i++) {
            tableNames.add(rsmd.getTableName(i));
            columnNames.add(rsmd.getColumnName(i));
            columnDataTypes.add(rsmd.getColumnTypeName(i));
        }
        determineIfMultipleTablesWereUsed();

        while (resultSet.next()) {
            ArrayList<String> rowData = new ArrayList<>();
            for (int i = 1; i <= numColumns; i++) {
                rowData.add(DatabaseStringFormatter.formatDatabaseResult(resultSet, i));
            }
            rows.add(new RowData(rowData));
        }
    }

    private void determineIfMultipleTablesWereUsed() {
        multipleTablesUsedForData = new HashSet<>(tableNames).size() > 1;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public List<String> getUniqueTableNames() {
        return new ArrayList<>(new HashSet<>(tableNames));
    }

    public boolean isMultipleTablesUsedForData() {
        return multipleTablesUsedForData;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    /**
     * Gets a list of fully qualified column names from the results.
     * For example:
     * If the table name is "Orders" and the column name is "orderDate"
     * The method will return "Orders.orderDate".
     *
     * If the table name contains spaces, it'll return a SQL usable value using
     * brackets to enclose the table name.
     * For example:
     * If the table name is "Order Details" and the column name is "quantity"
     * The method will return "[Order Details].quantity"
     *
     * @return A List of Strings of fully qualified column name with
     *  TableName.ColumnName notation
     */
    public List<String> getFullyQualifiedColumnNames() {
        List<String> names = new ArrayList<>();

        for (int i = 0; i < numColumns; i++) {
            String name = DatabaseStringFormatter.formatTableNameForQuery(tableNames.get(i)) +
                              "." +
                              columnNames.get(i);
            names.add(name);
        }
        return names;
    }

    public List<String> getColumnDataTypes() {
        return columnDataTypes;
    }

    public List<RowData> getRows() {
        return rows;
    }

    @Override
    public Iterator<RowData> iterator() {
        return rows.iterator();
    }
}
