import bcheck.BCheck;
import bcheck.BCheckFilter;
import bcheck.ItemImporter;
import bcheck.ItemImporter.BCheckItemImporter;
import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.persistence.Persistence;
import file.system.FileSystem;
import logging.Logger;
import repository.Repository;
import repository.RepositoryFacadeFactory;
import settings.controller.SettingsController;
import ui.clipboard.ClipboardManager;
import ui.controller.StoreController;
import ui.icons.IconFactory;
import ui.model.DefaultStorefrontModel;
import ui.model.LateInitializationStorefrontModel;
import ui.model.StorefrontModel;
import ui.view.Store;
import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;
import ui.view.pane.storefront.StorefrontPanel;
import ui.view.pane.storefront.bcheck.BCheckStorefront;
import utils.CloseablePooledExecutor;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unused")
public class Extension implements BurpExtension {
    private static final String EXTENSION_NAME = "Extensibility Helper";

    @Override
    public void initialize(MontoyaApi api) {
        Persistence persistence = api.persistence();
        SettingsController settingsController = new SettingsController(persistence.preferences());
        Logger logger = new Logger(api.logging(), settingsController.debugSettings());

        Repository repository = RepositoryFacadeFactory.from(logger, api.http(), settingsController);
        CloseablePooledExecutor executor = new CloseablePooledExecutor();

        IconFactory iconFactory = new IconFactory(api.userInterface());
        ClipboardManager clipboardManager = new ClipboardManager();
        FileSystem fileSystem = new FileSystem(logger);

        AtomicReference<StorefrontModel<BCheck>> modelReference = new AtomicReference<>();
        StorefrontModel<BCheck> lateInitializationStorefrontModel = new LateInitializationStorefrontModel<>(modelReference::get);
        ItemImporter<BCheck> bCheckImporter = new BCheckItemImporter(api.scanner().bChecks(), logger);

        StoreController storeController = new StoreController(
                lateInitializationStorefrontModel,
                repository,
                bCheckImporter,
                clipboardManager,
                fileSystem,
                new BCheckFilter()
        );

        StorefrontModel<BCheck> storefrontModel = new DefaultStorefrontModel(storeController);
        modelReference.set(storefrontModel);

        Settings settings = new Settings(settingsController);

        Storefront bCheckStorefront = new BCheckStorefront(
                "BCheck Store",
                storeController,
                storefrontModel,
                settingsController.defaultSaveLocationSettings(),
                executor,
                iconFactory,
                logger,
                () -> api.userInterface().currentDisplayFont()
        );

        Storefront bambdaStorefront = new Storefront() {
            @Override
            public String title() {
                return "Bambda Store";
            }

            @Override
            public Component component() {
                return new StorefrontPanel(new JPanel(), new JPanel());
            }
        };

        Store store = new Store(settings, bCheckStorefront, bambdaStorefront);

        api.extension().setName(EXTENSION_NAME);
        api.userInterface().registerSuiteTab(EXTENSION_NAME, store);
        api.extension().registerUnloadingHandler(executor::close);
    }
}
