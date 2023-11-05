package ui.model;

import bcheck.BCheck;
import ui.controller.StoreController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class StorefrontModel {
    public static final String SELECTED_BCHECK_CHANGED = "selectedBCheckChanged";
    public static final String SEARCH_FILTER_CHANGED = "searchFilterChanged";
    public static final String STATUS_CHANGED = "statusChanged";

    private final StoreController controller;
    private final PropertyChangeSupport propertyChangeSupport;

    private String searchFilter;
    private String status;
    private BCheck selectedBCheck;

    public StorefrontModel(StoreController controller) {
        this.controller = controller;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void setSelectedBCheck(BCheck selectedBCheck) {
        BCheck oldselectedBCheck = this.selectedBCheck;
        this.selectedBCheck = selectedBCheck;
        propertyChangeSupport.firePropertyChange(SELECTED_BCHECK_CHANGED, oldselectedBCheck, selectedBCheck);
    }

    public BCheck getSelectedBCheck() {
        return selectedBCheck;
    }

    public void setSearchFilter(String searchFilter) {
        String oldSearchFilter = this.searchFilter;
        this.searchFilter = searchFilter;
        propertyChangeSupport.firePropertyChange(SEARCH_FILTER_CHANGED, oldSearchFilter, searchFilter);
    }

    public void setStatus(String status) {
        String oldStatus = this.status;
        this.status = status;
        propertyChangeSupport.firePropertyChange(STATUS_CHANGED, oldStatus, status);
    }

    public List<BCheck> getFilteredBChecks() {
        return controller.findMatchingBChecks(searchFilter);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
