package ui.model;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface StorefrontModel<T> {
    String SELECTED_ITEM_CHANGED = "selectedItemChanged";
    String SEARCH_FILTER_CHANGED = "searchFilterChanged";
    String STATUS_CHANGED = "statusChanged";
    String ITEMS_UPDATED = "itemsUpdated";

    void setSelectedItem(T selectedItem);

    T getSelectedItem();

    void setSearchFilter(String searchFilter);

    void setStatus(String status);

    List<T> getAvailableItems();

    List<T> getFilteredItems();

    void addPropertyChangeListener(PropertyChangeListener listener);

    State state();

    void updateModel(List<T> items, State state);
}
