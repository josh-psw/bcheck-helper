package ui.model;

import bcheck.BCheck;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.function.Supplier;

public class LateInitializationStorefrontModel implements StorefrontModel {
    private final Supplier<StorefrontModel> modelSupplier;

    public LateInitializationStorefrontModel(Supplier<StorefrontModel> modelSupplier) {
        this.modelSupplier = modelSupplier;
    }

    @Override
    public void setSelectedBCheck(BCheck selectedBCheck) {
        model().setSelectedBCheck(selectedBCheck);
    }

    @Override
    public BCheck getSelectedBCheck() {
        return model().getSelectedBCheck();
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
    public List<BCheck> getAvailableBChecks() {
        return model().getAvailableBChecks();
    }

    @Override
    public List<BCheck> getFilteredBChecks() {
        return model().getFilteredBChecks();
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
    public void updateModel(List<BCheck> bChecks, State state) {
        model().updateModel(bChecks, state);
    }

    private StorefrontModel model() {
        return modelSupplier.get();
    }
}
