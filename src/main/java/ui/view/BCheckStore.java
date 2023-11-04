package ui.view;

import settings.controller.SettingsController;
import ui.controller.StoreController;
import ui.icons.IconFactory;
import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;

import javax.swing.*;
import java.util.concurrent.Executor;

public class BCheckStore extends JTabbedPane {
    public BCheckStore(StoreController storeController,
                       SettingsController settingsController,
                       Executor executor,
                       IconFactory iconFactory) {
        add("Store", new Storefront(storeController, settingsController.defaultSaveLocationSettings(), executor, iconFactory));
        add("Settings", new Settings(settingsController));
    }
}
