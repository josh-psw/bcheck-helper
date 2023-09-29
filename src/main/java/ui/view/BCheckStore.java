package ui.view;

import settings.controller.SettingsController;
import ui.controller.StoreController;
import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;

import javax.swing.*;

public class BCheckStore extends JTabbedPane {
    public BCheckStore(StoreController storeController, SettingsController settingsController) {
        add("Store", new Storefront(storeController, settingsController.defaultSaveLocationSettings()));
        add("Settings", new Settings(settingsController));
    }
}
