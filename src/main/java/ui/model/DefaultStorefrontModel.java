package ui.model;

import bcheck.BCheck;
import ui.controller.StoreController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;

public class DefaultStorefrontModel implements StorefrontModel {
    private final StoreController controller;
    private final List<BCheck> allAvailableBChecks;
    private final PropertyChangeSupport propertyChangeSupport;

    private State state;
    private String searchFilter;
    private String status;
    private BCheck selectedBCheck;

    public DefaultStorefrontModel(StoreController controller) {
        this.controller = controller;

        this.state = State.START;
        this.allAvailableBChecks = synchronizedList(new ArrayList<>());
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void setSelectedBCheck(BCheck selectedBCheck) {
        BCheck oldselectedBCheck = this.selectedBCheck;
        this.selectedBCheck = selectedBCheck;
        propertyChangeSupport.firePropertyChange(SELECTED_BCHECK_CHANGED, oldselectedBCheck, selectedBCheck);
    }

    @Override
    public BCheck getSelectedBCheck() {
        return selectedBCheck;
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
    public List<BCheck> getAvailableBChecks() {
        return allAvailableBChecks;
    }

    @Override
    public List<BCheck> getFilteredBChecks() {
        return controller.findMatchingBChecks(searchFilter);
    }

    @Override
    public State state() {
        return state;
    }

    @Override
    public void updateModel(List<BCheck> bChecks, State state) {
        this.state = state;

        allAvailableBChecks.clear();
        allAvailableBChecks.addAll(bChecks);
        allAvailableBChecks.sort(comparing(BCheck::name));
        propertyChangeSupport.firePropertyChange(BCHECKS_UPDATED, null, allAvailableBChecks);

        String status = switch (state) {
            case START -> "Loading";
            case INITIAL_LOAD -> "Loaded %d BCheck scripts".formatted(allAvailableBChecks.size());
            case REFRESH -> "Refreshed. Loaded %d BCheck scripts".formatted(allAvailableBChecks.size());
            case ERROR -> "Error loading BChecks from repository";
        };

        setStatus(status);

        if (!allAvailableBChecks.contains(selectedBCheck)) {
            setSelectedBCheck(null);
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
