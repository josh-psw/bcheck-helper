package ui.view.pane.storefront;

import data.Item;

import javax.swing.*;

public class ItemPopupMenu<T extends Item> extends JPopupMenu {
    public ItemPopupMenu(ActionController<T> actionController) {
        JMenuItem importBCheckMenuItem = new JMenuItem("Import item");
        importBCheckMenuItem.addActionListener(l -> actionController.importSelected());
        add(importBCheckMenuItem);

        JMenuItem copyBCheckMenuItem = new JMenuItem("Copy item");
        copyBCheckMenuItem.addActionListener(l -> actionController.copySelected());
        add(copyBCheckMenuItem);

        JMenuItem saveBCheckMenuItem = new JMenuItem("Save item");
        saveBCheckMenuItem.addActionListener(l -> actionController.saveSelected());
        add(saveBCheckMenuItem);
    }
}
