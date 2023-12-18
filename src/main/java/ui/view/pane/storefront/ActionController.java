package ui.view.pane.storefront;

import bcheck.BCheck;
import logging.Logger;
import ui.controller.StoreController;
import ui.model.StorefrontModel;

import java.nio.file.Path;
import java.util.concurrent.Executor;

import static ui.view.component.filechooser.ChooseMode.SAVE_FILES_ONLY;
import static ui.view.pane.storefront.ActionCallbacks.INERT_CALLBACKS;

class ActionController {
    private final StorefrontModel model;
    private final StoreController storeController;
    private final SaveLocation saveLocation;
    private final Executor executor;
    private final Logger logger;

    ActionController(
            StorefrontModel model,
            StoreController storeController,
            SaveLocation saveLocation,
            Executor executor,
            Logger logger) {
        this.model = model;
        this.storeController = storeController;
        this.saveLocation = saveLocation;
        this.executor = executor;
        this.logger = logger;
    }

    public void importSelectedBCheck() {
        storeController.importBCheck(model.getSelectedBCheck());
    }

    void copySelectedBCheck() {
        BCheck selectedBCheck = model.getSelectedBCheck();
        storeController.copyBCheck(selectedBCheck);
        model.setStatus("Copied BCheck " + selectedBCheck.name() + " to clipboard");
    }

    void saveSelectedBCheck() {
        saveSelectedBCheck(INERT_CALLBACKS);
    }

    void saveSelectedBCheck(ActionCallbacks actionCallbacks) {
        BCheck selectedBCheck = model.getSelectedBCheck();

        saveLocation.find(SAVE_FILES_ONLY, selectedBCheck.filename())
                .ifPresent(path -> {
                    actionCallbacks.actionBegun();
                    model.setStatus("");

                    executor.execute(() -> {
                        Path savePath = path.toFile().isDirectory() ? path.resolve(selectedBCheck.filename()) : path;

                        try {
                            storeController.saveBCheck(selectedBCheck, savePath);
                            model.setStatus("Saved BCheck to " + savePath);
                        } catch (RuntimeException e) {
                            logger.logError(e);
                            model.setStatus("Error saving BCheck!");
                        } finally {
                            actionCallbacks.actionComplete();
                        }
                    });
                });
    }

    void saveAllVisibleBChecks(ActionCallbacks actionCallbacks) {
        saveLocation.find().ifPresent(path -> {
            actionCallbacks.actionBegun();
            model.setStatus("");

            executor.execute(() -> {
                try {
                    model.getFilteredBChecks().forEach(bCheck -> storeController.saveBCheck(bCheck, path.resolve(bCheck.filename())));
                    model.setStatus("Saved all BChecks to " + path);
                } catch (RuntimeException e) {
                    logger.logError(e);
                    model.setStatus("Error saving BCheck!");
                } finally {
                    actionCallbacks.actionComplete();
                }
            });
        });
    }
}
