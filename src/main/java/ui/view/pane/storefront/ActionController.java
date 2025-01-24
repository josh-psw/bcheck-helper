package ui.view.pane.storefront;

public interface ActionController
{
    void importSelected();

    void copySelected();

    void saveSelected();

    void saveSelected(ActionCallbacks actionCallbacks);

    void saveAllVisible(ActionCallbacks actionCallbacks);
}
