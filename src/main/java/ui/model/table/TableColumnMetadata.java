package ui.model.table;

import bcheck.BCheck;
import bcheck.BCheck.Tags;

import java.util.function.Function;

public enum TableColumnMetadata {
    NAME("Name", String.class, BCheck::name),
    DESCRIPTION("Description", String.class, BCheck::description),
    TAGS("Tags", Tags.class, BCheck::tags);

    public final String columnName;
    public final Class<?> columnClass;
    public final Function<BCheck, Object> renderableTableData;

    TableColumnMetadata(String columnName, Class<?> columnClass, Function<BCheck, Object> renderableTableData) {
        this.columnName = columnName;
        this.columnClass = columnClass;
        this.renderableTableData = renderableTableData;
    }
}
