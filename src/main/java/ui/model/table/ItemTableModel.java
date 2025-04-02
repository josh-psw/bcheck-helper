package ui.model.table;

import data.Item;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static ui.model.table.TableColumnMetadata.*;

public class ItemTableModel<T extends Item> extends AbstractTableModel {
    private static final TableColumnMetadata[] TABLE_HEADERS = {
        NAME,
        DESCRIPTION,
        TAGS
    };

    private final List<T> tableData = new ArrayList<>();

    @Override
    public int getRowCount() {
        return tableData.size();
    }

    @Override
    public int getColumnCount() {
        return TABLE_HEADERS.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return TABLE_HEADERS[columnIndex].columnName;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return TABLE_HEADERS[columnIndex].columnClass;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Function<Item, Object> renderableTableDataFetcher = TABLE_HEADERS[columnIndex].renderableTableData;
        return renderableTableDataFetcher.apply(tableData.get(rowIndex));
    }

    public T getItemAtRow(int row) {
        return tableData.get(row);
    }

    public int getItemRow(T item) {
        return item == null ? -1 : tableData.indexOf(item);
    }

    public void setItems(List<T> items) {
        tableData.clear();
        tableData.addAll(items);

        fireTableDataChanged();
    }
}
