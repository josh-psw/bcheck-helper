package ui.view.pane.storefront.bcheck;

import bcheck.Item;
import logging.Logger;
import ui.controller.StoreController;
import ui.model.StorefrontModel;
import ui.view.pane.storefront.ActionCallbacks;
import ui.view.pane.storefront.ActionController;
import ui.view.pane.storefront.SaveLocation;

import java.nio.file.Path;
import java.util.concurrent.Executor;

import static ui.view.component.filechooser.ChooseMode.SAVE_FILES_ONLY;
import static ui.view.pane.storefront.ActionCallbacks.INERT_CALLBACKS;

class DefaultActionController<T extends Item> implements ActionController {
    private final StorefrontModel<T> model;
    private final StoreController<T> storeController;
    private final SaveLocation saveLocation;
    private final Executor executor;
    private final Logger logger;

    DefaultActionController(
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

    @Override
    public void importSelected() {
        storeController.importItem(model.getSelectedItem());
    }

    @Override
    public void copySelected() {
        T selectedItem = model.getSelectedItem();
        storeController.copyItem(selectedItem);
        model.setStatus("Copied " + selectedItem.name() + " to clipboard");
    }

    @Override
    public void saveSelected() {
        saveSelected(INERT_CALLBACKS);
    }

    @Override
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

    @Override
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
