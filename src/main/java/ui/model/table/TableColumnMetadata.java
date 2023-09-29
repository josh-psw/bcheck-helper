package ui.model.table;

import bcheck.BCheck;

import java.util.function.Function;

import static java.lang.String.join;

public enum TableColumnMetadata {
    NAME("Name", String.class, BCheck::name),
    DESCRIPTION("Description", String.class, BCheck::description),
    TAGS("Tags", String.class, bCheck -> join(", ", bCheck.tags().stream().sorted().toList()));

    public final String columnName;
    public final Class<?> columnClass;
    public final Function<BCheck, String> renderableTableData;

    TableColumnMetadata(String columnName, Class<?> columnClass, Function<BCheck, String> renderableTableData) {
        this.columnName = columnName;
        this.columnClass = columnClass;
        this.renderableTableData = renderableTableData;
    }
}
