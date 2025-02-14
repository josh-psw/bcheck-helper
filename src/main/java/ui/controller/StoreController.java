package ui.controller;

import bcheck.BCheck;
import bcheck.ItemFilter;
import bcheck.ItemImporter;
import file.system.FileSystem;
import repository.Repository;
import ui.clipboard.ClipboardManager;
import ui.model.StorefrontModel;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static ui.model.State.ERROR;

public class StoreController {
    private final StorefrontModel<BCheck> model;
    private final Repository repository;
    private final ItemImporter<BCheck> bCheckImporter;
    private final ClipboardManager clipboardManager;
    private final FileSystem fileSystem;
    private final ItemFilter<BCheck> itemFilter;

    public StoreController(
            StorefrontModel<BCheck> model,
            Repository repository,
            ItemImporter<BCheck> bCheckImporter,
            ClipboardManager clipboardManager,
            FileSystem fileSystem,
            ItemFilter<BCheck> itemFilter
    ) {
        this.model = model;
        this.repository = repository;
        this.bCheckImporter = bCheckImporter;
        this.clipboardManager = clipboardManager;
        this.fileSystem = fileSystem;
        this.itemFilter = itemFilter;
    }

    public void loadData() {
        model.setStatus("");

        try {
            model.updateModel(repository.loadAllBChecks(), model.state().nextState());
        } catch (Exception e) {
            model.updateModel(emptyList(), ERROR);
        }
    }

    public List<BCheck> findMatchingBChecks(String searchText) {
        Predicate<BCheck> filter = searchText.isBlank()
                ? item -> true
                : item -> itemFilter.filter(item, searchText);

        return model.getAvailableItems().stream().filter(filter).toList();
    }

    public void importBCheck(BCheck bCheck) {
        try {
            bCheckImporter.importItem(bCheck);
            model.setStatus("Successfully imported BCheck: " + bCheck.name());
        } catch (IllegalStateException e) {
            model.setStatus("Error imported BCheck: " + bCheck.name());
        }
    }

    public void copyBCheck(BCheck bCheck) {
        clipboardManager.copy(bCheck.content());
    }

    public void saveBCheck(BCheck bCheck, Path savePath) {
        fileSystem.saveFile(bCheck.content(), savePath);
    }
}
