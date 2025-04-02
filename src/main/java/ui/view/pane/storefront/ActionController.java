package ui.view.pane.storefront;

import data.Item;
import logging.Logger;
import ui.controller.StoreController;
import ui.model.StorefrontModel;

import java.nio.file.Path;
import java.util.concurrent.Executor;

import static ui.view.component.filechooser.ChooseMode.SAVE_FILES_ONLY;
import static ui.view.pane.storefront.ActionCallbacks.INERT_CALLBACKS;

public class ActionController<T extends Item> {
    private final StorefrontModel<T> model;
    private final StoreController<T> storeController;
    private final SaveLocation saveLocation;
    private final Executor executor;
    private final Logger logger;

    public ActionController(
            StorefrontModel<T> model,
            StoreController<T> storeController,
            SaveLocation saveLocation,
            Executor executor,
            Logger logger) {
        this.model = model;
        this.storeController = storeController;
        this.saveLocation = saveLocation;
        this.executor = executor;
        this.logger = logger;
    }

    public void importSelected() {
        storeController.importItem(model.getSelectedItem());
    }

    public void copySelected() {
        T selectedItem = model.getSelectedItem();
        storeController.copyItem(selectedItem);
        model.setStatus("Copied " + selectedItem.name() + " to clipboard");
    }

    public void saveSelected() {
        saveSelected(INERT_CALLBACKS);
    }

    public void saveSelected(ActionCallbacks actionCallbacks) {
        T selectedItem = model.getSelectedItem();

        saveLocation.find(SAVE_FILES_ONLY, selectedItem.filename())
                .ifPresent(path -> {
                    actionCallbacks.actionBegun();
                    model.setStatus("");

                    executor.execute(() -> {
                        Path savePath = path.toFile().isDirectory() ? path.resolve(selectedItem.filename()) : path;

                        try {
                            storeController.saveItem(selectedItem, savePath);
                            model.setStatus("Saved to " + savePath);
                        } catch (RuntimeException e) {
                            logger.logError(e);
                            model.setStatus("Error saving.");
                        } finally {
                            actionCallbacks.actionComplete();
                        }
                    });
                });
    }

    public void saveAllVisible(ActionCallbacks actionCallbacks) {
        saveLocation.find().ifPresent(path -> {
            actionCallbacks.actionBegun();
            model.setStatus("");

            executor.execute(() -> {
                try {
                    model.getFilteredItems().forEach(item -> storeController.saveItem(item, path.resolve(item.filename())));
                    model.setStatus("Saved all items to " + path);
                } catch (RuntimeException e) {
                    logger.logError(e);
                    model.setStatus("Error saving item.");
                } finally {
                    actionCallbacks.actionComplete();
                }
            });
        });
    }
}
