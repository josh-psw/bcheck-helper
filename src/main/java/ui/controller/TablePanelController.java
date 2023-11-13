package ui.controller;

public interface TablePanelController {
    void loadData();

    class DefaultTablePanelController implements TablePanelController {
        private final StoreController storeController;

        public DefaultTablePanelController(StoreController storeController) {
            this.storeController = storeController;
        }

        @Override
        public void loadData() {
            storeController.loadData();
        }
    }
}
