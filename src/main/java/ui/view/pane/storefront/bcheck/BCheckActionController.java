package ui.view.pane.storefront.bcheck;

import bcheck.BCheck;
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

class BCheckActionController implements ActionController
{
    private final StorefrontModel<BCheck> model;
    private final StoreController storeController;
    private final SaveLocation saveLocation;
    private final Executor executor;
    private final Logger logger;

    BCheckActionController(
            StorefrontModel<BCheck> model,
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

    @Override
    public void importSelected() {
        storeController.importBCheck(model.getSelectedItem());
    }

    @Override
    public void copySelected() {
        BCheck selectedBCheck = model.getSelectedItem();
        storeController.copyBCheck(selectedBCheck);
        model.setStatus("Copied BCheck " + selectedBCheck.name() + " to clipboard");
    }

    @Override
    public void saveSelected() {
        saveSelected(INERT_CALLBACKS);
    }

    @Override
    public void saveSelected(ActionCallbacks actionCallbacks) {
        BCheck selectedBCheck = model.getSelectedItem();

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

    @Override
    public void saveAllVisible(ActionCallbacks actionCallbacks) {
        saveLocation.find().ifPresent(path -> {
            actionCallbacks.actionBegun();
            model.setStatus("");

            executor.execute(() -> {
                try {
                    model.getFilteredItems().forEach(bCheck -> storeController.saveBCheck(bCheck, path.resolve(bCheck.filename())));
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
