package ui.view;

import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;

import javax.swing.*;

public class BCheckStore extends JTabbedPane {
    public BCheckStore(Settings settings, Storefront storefront) {
        add("Store", storefront);
        add("Settings", settings);
    }
}
