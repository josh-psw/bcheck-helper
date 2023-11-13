package ui.model;

import bcheck.BCheck;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface StorefrontModel {
    String SELECTED_BCHECK_CHANGED = "selectedBCheckChanged";
    String SEARCH_FILTER_CHANGED = "searchFilterChanged";
    String STATUS_CHANGED = "statusChanged";
    String BCHECKS_UPDATED = "bchecksUpdated";

    void setSelectedBCheck(BCheck selectedBCheck);

    BCheck getSelectedBCheck();

    void setSearchFilter(String searchFilter);

    void setStatus(String status);

    List<BCheck> getAvailableBChecks();

    List<BCheck> getFilteredBChecks();

    void addPropertyChangeListener(PropertyChangeListener listener);

    State state();

    void updateModel(List<BCheck> bChecks, State state);
}
