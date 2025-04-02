package ui.model;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.function.Supplier;

public class LateInitializationStorefrontModel<T> implements StorefrontModel<T> {
    private final Supplier<StorefrontModel<T>> modelSupplier;

    public LateInitializationStorefrontModel(Supplier<StorefrontModel<T>> modelSupplier) {
        this.modelSupplier = modelSupplier;
    }

    @Override
    public void setSelectedItem(T selectedItem) {
        model().setSelectedItem(selectedItem);
    }

    @Override
    public T getSelectedItem() {
        return model().getSelectedItem();
    }

    @Override
    public void setSearchFilter(String searchFilter) {
        model().setSearchFilter(searchFilter);
    }

    @Override
    public void setStatus(String status) {
        model().setStatus(status);
    }

    @Override
    public List<T> getAvailableItems() {
        return model().getAvailableItems();
    }

    @Override
    public List<T> getFilteredItems() {
        return model().getFilteredItems();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        model().addPropertyChangeListener(listener);
    }

    @Override
    public State state() {
        return model().state();
    }

    @Override
    public void updateModel(List<T> items, State state) {
        model().updateModel(items, state);
    }

    private StorefrontModel<T> model() {
        return modelSupplier.get();
    }
}
