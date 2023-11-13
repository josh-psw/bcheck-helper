import bcheck.BCheckFactory;
import bcheck.BCheckManager;
import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.persistence.Persistence;
import client.GitHubClient;
import fetcher.BCheckFetcher;
import file.finder.BCheckFileFinder;
import file.system.FileSystem;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import logging.Logger;
import network.RequestSender;
import settings.controller.SettingsController;
import ui.clipboard.ClipboardManager;
import ui.icons.IconFactory;
import ui.view.BCheckStore;
import utils.CloseablePooledExecutor;

public class Extension implements BurpExtension {
    private static final String TAB_TITLE = "BCheck Helper";

    @Override
    public void initialize(MontoyaApi api) {
        Persistence persistence = api.persistence();
        SettingsController settingsController = new SettingsController(persistence);

        Logger logger = new Logger(api.logging(), settingsController.debugSettings());
        RequestSender requestSender = new RequestSender(api.http(), logger);
        BCheckFactory bCheckFactory = new BCheckFactory(logger);
        GitHubClient gitHubClient = new GitHubClient(requestSender);
        TempFileCreator tempFileCreator = new TempFileCreator(logger);
        ZipExtractor zipExtractor = new ZipExtractor(logger);
        BCheckFileFinder bCheckFileFinder = new BCheckFileFinder();
        BCheckFetcher onlineBCheckFetcher = new BCheckFetcher(bCheckFactory, gitHubClient, tempFileCreator, zipExtractor, bCheckFileFinder, settingsController.gitHubSettings());
        CloseablePooledExecutor executor = new CloseablePooledExecutor();

        BCheckStore bcheckStore = new BCheckStore(
                new BCheckManager(onlineBCheckFetcher),
                new ClipboardManager(),
                new FileSystem(logger),
                settingsController,
                executor,
                new IconFactory(api.userInterface()),
                logger
        );

        api.userInterface().registerSuiteTab(TAB_TITLE, bcheckStore);
        api.extension().registerUnloadingHandler(executor::close);
    }
}
