package ui.model.table;

import bcheck.BCheck;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static ui.model.table.TableColumnMetadata.*;

public class BCheckTableModel extends AbstractTableModel {
    private static final TableColumnMetadata[] TABLE_HEADERS = {
        NAME,
        DESCRIPTION,
        TAGS
    };

    private final List<BCheck> tableData = new ArrayList<>();

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
        Function<BCheck, Object> renderableTableDataFetcher = TABLE_HEADERS[columnIndex].renderableTableData;
        return renderableTableDataFetcher.apply(tableData.get(rowIndex));
    }

    public BCheck getBCheckAtRow(int row) {
        return tableData.get(row);
    }

    public void setBChecks(List<BCheck> bChecks) {
        tableData.clear();
        tableData.addAll(bChecks);

        fireTableDataChanged();
    }
}
