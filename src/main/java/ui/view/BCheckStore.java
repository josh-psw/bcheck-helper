package ui.view;

import bcheck.BCheckManager;
import file.system.FileSystem;
import settings.controller.SettingsController;
import ui.clipboard.ClipboardManager;
import ui.controller.StoreController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;
import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;

import javax.swing.*;
import java.util.concurrent.Executor;

public class BCheckStore extends JTabbedPane {
    public BCheckStore(BCheckManager bCheckManager,
                       ClipboardManager clipboardManager,
                       FileSystem fileSystem,
                       SettingsController settingsController,
                       Executor executor,
                       IconFactory iconFactory) {
        StoreController storeController = new StoreController(bCheckManager, clipboardManager, fileSystem);
        StorefrontModel storefrontModel = new StorefrontModel(storeController);

        add("Store", new Storefront(storeController, storefrontModel, settingsController.defaultSaveLocationSettings(), executor, iconFactory));
        add("Settings", new Settings(settingsController));
    }
}
