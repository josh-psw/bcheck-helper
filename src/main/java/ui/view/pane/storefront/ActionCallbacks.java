package ui.view.pane.storefront;

import javax.swing.*;

public interface ActionCallbacks {
    void actionBegun();

    void actionComplete();

    ActionCallbacks INERT_CALLBACKS = new ActionCallbacks() {
        @Override
        public void actionBegun() {
        }

        @Override
        public void actionComplete() {
        }
    };

    class ButtonTogglingActionCallbacks implements ActionCallbacks {
        private final JButton button;

        public ButtonTogglingActionCallbacks(JButton button) {
            this.button = button;
        }

        @Override
        public void actionBegun() {
            button.setEnabled(false);
        }

        @Override
        public void actionComplete() {
            button.setEnabled(true);
        }
    }
}
