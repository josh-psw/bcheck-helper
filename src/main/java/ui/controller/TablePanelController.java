package ui.controller;

import bcheck.Item;

public interface TablePanelController {
    void loadData();

    class DefaultTablePanelController<T extends Item> implements TablePanelController {
        private final StoreController<T> storeController;

        public DefaultTablePanelController(StoreController<T> storeController) {
            this.storeController = storeController;
        }

        @Override
        public void loadData() {
            storeController.loadData();
        }
    }
}
