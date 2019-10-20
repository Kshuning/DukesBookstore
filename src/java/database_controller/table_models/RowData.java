package database_controller.table_models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Wrapper class for storing a row of data from the database as individual strings.
 * Provides an iterator for getting each value from the row.
 */
public class RowData implements Iterable<String> {
    private int numColumns;
    private List<String> values = new ArrayList<>();

    public RowData(List<String> rowData) {
        values.addAll(rowData);
        numColumns = values.size();
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }
}
