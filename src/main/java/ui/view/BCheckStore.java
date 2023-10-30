package ui.view;

import settings.controller.SettingsController;
import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;

import javax.swing.*;

public class BCheckStore extends JTabbedPane {
    public BCheckStore(SettingsController settingsController, Storefront storefront) {
        add("Store", storefront);
        add("Settings", new Settings(settingsController));
    }
}
