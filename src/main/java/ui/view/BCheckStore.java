package ui.view;

import bcheck.BCheckFactory;
import bcheck.BCheckManager;
import burp.api.montoya.logging.Logging;
import client.github.GitHubClient;
import event.EventListener;
import file.system.FileSystem;
import settings.controller.SettingsController;
import ui.clipboard.ClipboardManager;
import ui.controller.StoreController;
import ui.controller.submission.SubmitterController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;
import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;
import ui.view.pane.submitter.Submitter;

import javax.swing.*;
import java.util.concurrent.Executor;

public class BCheckStore extends JTabbedPane {
    public BCheckStore(
            BCheckManager bCheckManager,
            ClipboardManager clipboardManager,
            FileSystem fileSystem,
            SettingsController settingsController,
            Executor executor,
            IconFactory iconFactory,
            GitHubClient gitHubClient,
            BCheckFactory bCheckFactory,
            EventListener eventListener,
            Logging logger
    ) {
        var storeController = new StoreController(bCheckManager, clipboardManager, fileSystem);
        var submitterController = new SubmitterController(settingsController.gitHubSettings(), gitHubClient, bCheckFactory, logger);
        var storefrontModel = new StorefrontModel(storeController);

        add("Store", new Storefront(storeController, storefrontModel, settingsController.defaultSaveLocationSettings(), executor, iconFactory));
        add("Submit BCheck", new Submitter(settingsController.gitHubSettings(), submitterController, eventListener, executor));
        add("Settings", new Settings(settingsController));
    }
}
