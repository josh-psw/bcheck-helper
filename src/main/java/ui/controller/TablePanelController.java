package ui.controller;

import data.Item;

public class TablePanelController<T extends Item> {
    private final StoreController<T> storeController;

    public TablePanelController(StoreController<T> storeController) {
        this.storeController = storeController;
    }

    public void loadData() {
        storeController.loadData();
    }
}