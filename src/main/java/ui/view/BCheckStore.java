package ui.view;

import settings.controller.SettingsController;
import ui.controller.StoreController;
import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;

import javax.swing.*;
import java.util.concurrent.Executor;

public class BCheckStore extends JTabbedPane {
    public BCheckStore(StoreController storeController, SettingsController settingsController, Executor executor) {
        add("Store", new Storefront(storeController, settingsController.defaultSaveLocationSettings(), executor));
        add("Settings", new Settings(settingsController));
    }
}
