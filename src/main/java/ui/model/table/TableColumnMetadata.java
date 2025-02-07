package ui.model.table;

import bcheck.Item;
import bcheck.Tags;

import java.util.function.Function;

public enum TableColumnMetadata {
    NAME("Name", String.class, Item::name),
    DESCRIPTION("Description", String.class, Item::description),
    TAGS("Tags", Tags.class, Item::tags);

    public final String columnName;
    public final Class<?> columnClass;
    public final Function<Item, Object> renderableTableData;

    TableColumnMetadata(String columnName, Class<?> columnClass, Function<Item, Object> renderableTableData) {
        this.columnName = columnName;
        this.columnClass = columnClass;
        this.renderableTableData = renderableTableData;
    }
}
