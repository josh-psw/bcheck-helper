import bcheck.BCheckFactory;
import bcheck.BCheckManager;
import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import client.github.GitHubClient;
import fetcher.BCheckFetcher;
import file.finder.BCheckFileFinder;
import file.system.FileSystem;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
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
        var logger = api.logging();
        var persistence = api.persistence();
        var settingsController = new SettingsController(persistence);

        var requestSender = new RequestSender(api.http(), logger);
        var gitHubClient = new GitHubClient(requestSender, settingsController.gitHubSettings(), logger);
        var tempFileCreator = new TempFileCreator(logger);
        var fileSystem = new FileSystem(logger);
        var bCheckFactory = new BCheckFactory(logger, tempFileCreator, fileSystem);
        var zipExtractor = new ZipExtractor(logger);
        var bCheckFileFinder = new BCheckFileFinder();
        var onlineBCheckFetcher = new BCheckFetcher(bCheckFactory, gitHubClient, tempFileCreator, zipExtractor, bCheckFileFinder, settingsController.gitHubSettings());
        var executor = new CloseablePooledExecutor();

        var bcheckStore = new BCheckStore(
                new BCheckManager(onlineBCheckFetcher),
                new ClipboardManager(),
                fileSystem,
                settingsController,
                executor,
                new IconFactory(api.userInterface()),
                gitHubClient,
                bCheckFactory,
                logger
        );

        api.userInterface().registerSuiteTab(TAB_TITLE, bcheckStore);
        api.extension().registerUnloadingHandler(executor::close);
    }
}
