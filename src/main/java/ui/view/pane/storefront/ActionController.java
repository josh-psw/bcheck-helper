package ui.view.pane.storefront;

public interface ActionController
{
    void importSelectedBCheck();

    void copySelectedBCheck();

    void saveSelectedBCheck();

    void saveSelectedBCheck(ActionCallbacks actionCallbacks);

    void saveAllVisibleBChecks(ActionCallbacks actionCallbacks);
}
