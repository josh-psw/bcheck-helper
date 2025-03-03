package ui.controller;

import data.Item;
import data.ItemFilter;
import data.ItemImporter;
import file.system.FileSystem;
import repository.Repository;
import ui.clipboard.ClipboardManager;
import ui.model.StorefrontModel;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static ui.model.State.ERROR;

public class StoreController<T extends Item> {
    private final StorefrontModel<T> model;
    private final Repository<T> repository;
    private final ItemImporter<T> itemImporter;
    private final ClipboardManager clipboardManager;
    private final FileSystem fileSystem;
    private final ItemFilter<T> itemFilter;

    public StoreController(
            StorefrontModel<T> model,
            Repository<T> repository,
            ItemImporter<T> itemImporter,
            ClipboardManager clipboardManager,
            FileSystem fileSystem,
            ItemFilter<T> itemFilter
    ) {
        this.model = model;
        this.repository = repository;
        this.itemImporter = itemImporter;
        this.clipboardManager = clipboardManager;
        this.fileSystem = fileSystem;
        this.itemFilter = itemFilter;
    }

    public void loadData() {
        model.setStatus("");

        try {
            model.updateModel(repository.loadAllItems(), model.state().nextState());
        } catch (Exception e) {
            model.updateModel(emptyList(), ERROR);
        }
    }

    public List<T> findMatchingItems(String searchText) {
        Predicate<T> filter = searchText.isBlank()
                ? item -> true
                : item -> itemFilter.filter(item, searchText);

        return model.getAvailableItems().stream().filter(filter).toList();
    }

    public void importItem(T item) {
        try {
            itemImporter.importItem(item);
            model.setStatus("Successfully imported item: " + item.name());
        } catch (IllegalStateException e) {
            model.setStatus("Error importing item: " + item.name());
        }
    }

    public void copyItem(T item) {
        clipboardManager.copy(item.content());
    }

    public void saveItem(T item, Path savePath) {
        fileSystem.saveFile(item.content(), savePath);
    }
}
