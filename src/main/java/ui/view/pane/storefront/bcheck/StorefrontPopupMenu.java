package ui.view.pane.storefront.bcheck;

import ui.view.pane.storefront.ActionController;

import javax.swing.*;

public class StorefrontPopupMenu extends JPopupMenu {
    StorefrontPopupMenu(ActionController actionController, String type) {
        JMenuItem importBCheckMenuItem = new JMenuItem("Import " + type);
        importBCheckMenuItem.addActionListener(l -> actionController.importSelected());
        add(importBCheckMenuItem);

        JMenuItem copyBCheckMenuItem = new JMenuItem("Copy " + type);
        copyBCheckMenuItem.addActionListener(l -> actionController.copySelected());
        add(copyBCheckMenuItem);

        JMenuItem saveBCheckMenuItem = new JMenuItem("Save " + type);
        saveBCheckMenuItem.addActionListener(l -> actionController.saveSelected());
        add(saveBCheckMenuItem);
    }
}
