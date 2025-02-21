package ui.model;

import data.Item;
import ui.controller.StoreController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;

public class DefaultStorefrontModel<T extends Item> implements StorefrontModel<T> {
    private final StoreController<T> controller;
    private final List<T> allAvailableItems;
    private final PropertyChangeSupport propertyChangeSupport;

    private State state;
    private String searchFilter;
    private String status;
    private T selectedItem;

    public DefaultStorefrontModel(StoreController<T> controller) {
        this.controller = controller;

        this.state = State.START;
        this.allAvailableItems = synchronizedList(new ArrayList<>());
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void setSelectedItem(T selectedItem) {
        T oldSelectedItem = this.selectedItem;
        this.selectedItem = selectedItem;
        propertyChangeSupport.firePropertyChange(SELECTED_ITEM_CHANGED, oldSelectedItem, selectedItem);
    }

    @Override
    public T getSelectedItem() {
        return selectedItem;
    }

    @Override
    public void setSearchFilter(String searchFilter) {
        String oldSearchFilter = this.searchFilter;
        this.searchFilter = searchFilter;
        propertyChangeSupport.firePropertyChange(SEARCH_FILTER_CHANGED, oldSearchFilter, searchFilter);
    }

    @Override
    public void setStatus(String status) {
        String oldStatus = this.status;
        this.status = status;
        propertyChangeSupport.firePropertyChange(STATUS_CHANGED, oldStatus, status);
    }

    @Override
    public List<T> getAvailableItems() {
        return allAvailableItems;
    }

    @Override
    public List<T> getFilteredItems() {
        return controller.findMatchingItems(searchFilter);
    }

    @Override
    public State state() {
        return state;
    }

    @Override
    public void updateModel(List<T> items, State state) {
        this.state = state;

        allAvailableItems.clear();
        allAvailableItems.addAll(items);
        allAvailableItems.sort(comparing(Item::name));
        propertyChangeSupport.firePropertyChange(ITEMS_UPDATED, null, allAvailableItems);

        String status = switch (state) {
            case START -> "Loading";
            case INITIAL_LOAD -> "Loaded %d items".formatted(allAvailableItems.size());
            case REFRESH -> "Refreshed. Loaded %d items".formatted(allAvailableItems.size());
            case ERROR -> "Error loading from repository";
        };

        setStatus(status);

        if (!allAvailableItems.contains(selectedItem)) {
            setSelectedItem(null);
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
