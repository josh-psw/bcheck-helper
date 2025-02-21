import bcheck.BCheck;
import bcheck.BCheckFilter;
import bcheck.ItemImporter.BCheckItemImporter;
import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.persistence.Persistence;
import logging.Logger;
import repository.Repository;
import repository.RepositoryFacadeFactory;
import settings.controller.SettingsController;
import ui.view.Store;
import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;
import ui.view.pane.storefront.StorefrontFactory;
import utils.CloseablePooledExecutor;

@SuppressWarnings("unused")
public class Extension implements BurpExtension {
    private static final String EXTENSION_NAME = "Extensibility Helper";

    @Override
    public void initialize(MontoyaApi api) {
        Persistence persistence = api.persistence();

        SettingsController settingsController = new SettingsController(persistence.preferences());

        Logger logger = new Logger(api.logging(), settingsController.debugSettings());

        Repository<BCheck> repository = RepositoryFacadeFactory.from(logger, api.http(), settingsController);

        CloseablePooledExecutor executor = new CloseablePooledExecutor();

        StorefrontFactory storefrontFactory = new StorefrontFactory(logger, api.userInterface(), settingsController, executor);

        Storefront<BCheck> bCheckStorefront = storefrontFactory.build(
                "BCheck Store",
                new BCheckFilter(),
                repository,
                new BCheckItemImporter(api.scanner().bChecks(), logger)
        );

        Settings settings = new Settings(settingsController);

        Store store = new Store(settings, bCheckStorefront);

        api.extension().setName(EXTENSION_NAME);
        api.userInterface().registerSuiteTab(EXTENSION_NAME, store);
        api.extension().registerUnloadingHandler(executor::close);
    }
}
