package ui.controller;

import bcheck.BCheck;
import bcheck.BCheckManager;
import file.system.FileSystem;
import ui.clipboard.ClipboardManager;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class StoreController {
    private final BCheckManager bCheckManager;
    private final ClipboardManager clipboardManager;
    private final FileSystem fileSystem;

    public StoreController(
            BCheckManager bCheckManager,
            ClipboardManager clipboardManager,
            FileSystem fileSystem
    ) {
        this.bCheckManager = bCheckManager;
        this.clipboardManager = clipboardManager;
        this.fileSystem = fileSystem;
    }

    public String status() {
        return switch (bCheckManager.state()) {
            case START -> "Loading";
            case INITIAL_LOAD -> "Loaded %d BCheck scripts".formatted(bCheckManager.numberOfBChecks());
            case REFRESH -> "Refreshed";
            case ERROR -> "Error contacting GitHub repository";
        };
    }

    public void loadData() {
        bCheckManager.loadData();
    }

    public List<BCheck> findMatchingBChecks(String searchText) {
        Predicate<BCheck> filter = searchText.isBlank() ? bCheck -> true : new BCheckFilterPredicate(searchText);

        return bCheckManager.availableBChecks().stream().filter(filter).toList();
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
