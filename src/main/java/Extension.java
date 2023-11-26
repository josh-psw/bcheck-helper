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
import ui.view.BCheckStore;
import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;
import utils.CloseablePooledExecutor;

import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unused")
public class Extension implements BurpExtension {
    private static final String TAB_TITLE = "BCheck Helper";

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

        AtomicReference<StorefrontModel> modelReference = new AtomicReference<>();
        StorefrontModel lateInitializationStorefrontModel = new LateInitializationStorefrontModel(modelReference::get);
        StoreController storeController = new StoreController(
                lateInitializationStorefrontModel,
                repository,
                clipboardManager,
                fileSystem
        );

        StorefrontModel storefrontModel = new DefaultStorefrontModel(storeController);
        modelReference.set(storefrontModel);

        Settings settings = new Settings(settingsController);

        Storefront storefront = new Storefront(
                storeController,
                storefrontModel,
                settingsController.defaultSaveLocationSettings(),
                executor,
                iconFactory,
                logger,
                () -> api.userInterface().currentDisplayFont()
        );

        BCheckStore bcheckStore = new BCheckStore(settings, storefront);

        api.userInterface().registerSuiteTab(TAB_TITLE, bcheckStore);
        api.extension().registerUnloadingHandler(executor::close);
    }
}
