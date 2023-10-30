import bcheck.BCheckFactory;
import bcheck.BCheckManager;
import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.persistence.Persistence;
import client.GitHubClient;
import fetcher.BCheckFetcher;
import file.finder.BCheckFileFinder;
import file.system.FileSystem;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import network.RequestSender;
import settings.controller.SettingsController;
import ui.clipboard.ClipboardManager;
import ui.controller.StoreController;
import ui.view.BCheckStore;
import ui.view.pane.storefront.Storefront;

public class Extension implements BurpExtension {
    private static final String TAB_TITLE = "BCheck Helper";

    @Override
    public void initialize(MontoyaApi api) {
        Logging logger = api.logging();
        Persistence persistence = api.persistence();
        SettingsController settingsController = new SettingsController(persistence);

        RequestSender requestSender = new RequestSender(api.http(), logger);
        BCheckFactory bCheckFactory = new BCheckFactory(logger);
        GitHubClient gitHubClient = new GitHubClient(requestSender);
        TempFileCreator tempFileCreator = new TempFileCreator(logger);
        ZipExtractor zipExtractor = new ZipExtractor(logger);
        BCheckFileFinder bCheckFileFinder = new BCheckFileFinder();
        BCheckFetcher onlineBCheckFetcher = new BCheckFetcher(bCheckFactory, gitHubClient, tempFileCreator, zipExtractor, bCheckFileFinder, settingsController.gitHubSettings());

        BCheckManager bCheckManager = new BCheckManager(onlineBCheckFetcher);
        ClipboardManager clipboardManager = new ClipboardManager();
        FileSystem fileSystem = new FileSystem(logger);
        StoreController storeController = new StoreController(bCheckManager, clipboardManager, fileSystem);
        Storefront storefront = new Storefront(storeController, settingsController.defaultSaveLocationSettings());
        BCheckStore bcheckStore = new BCheckStore(settingsController, storefront);

        api.userInterface().registerSuiteTab(TAB_TITLE, bcheckStore);
        api.extension().registerUnloadingHandler(() -> {
            gitHubClient.close();
            storefront.close();
        });
    }
}
