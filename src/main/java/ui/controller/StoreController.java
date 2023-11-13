package ui.controller;

import bcheck.BCheck;
import fetcher.BCheckFetcher;
import file.system.FileSystem;
import ui.clipboard.ClipboardManager;
import ui.model.StorefrontModel;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static ui.model.State.ERROR;

public class StoreController {
    private final StorefrontModel model;
    private final BCheckFetcher onlineBCheckFetcher;
    private final ClipboardManager clipboardManager;
    private final FileSystem fileSystem;

    public StoreController(
            StorefrontModel model,
            BCheckFetcher bCheckFetcher,
            ClipboardManager clipboardManager,
            FileSystem fileSystem
    ) {
        this.model = model;
        this.onlineBCheckFetcher = bCheckFetcher;
        this.clipboardManager = clipboardManager;
        this.fileSystem = fileSystem;
    }

    public void loadData() {
        model.setStatus("");

        try {
            model.updateModel(onlineBCheckFetcher.fetchAllBChecks(), model.state().nextState());
        } catch (Exception e) {
            model.updateModel(emptyList(), ERROR);
        }
    }

    public List<BCheck> findMatchingBChecks(String searchText) {
        Predicate<BCheck> filter = searchText.isBlank() ? bCheck -> true : new BCheckFilterPredicate(searchText);

        return model.getAvailableBChecks().stream().filter(filter).toList();
    }

    public void copyBCheck(BCheck bCheck) {
        clipboardManager.copy(bCheck.script());
    }

    public void saveBCheck(BCheck bCheck, Path savePath) {
        fileSystem.saveFile(bCheck.script(), savePath);
    }

    private static class BCheckFilterPredicate implements Predicate<BCheck> {
        private final String searchText;

        private BCheckFilterPredicate(String searchText) {
            this.searchText = searchText.toLowerCase();
        }

        @Override
        public boolean test(BCheck bCheck) {
            return bCheck.name().toLowerCase().contains(searchText) ||
                    bCheck.author().toLowerCase().contains(searchText) ||
                    bCheck.description().toLowerCase().contains(searchText) ||
                    bCheck.tags().contains(searchText);
        }
    }
}
