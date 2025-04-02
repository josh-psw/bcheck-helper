package ui.view.pane.storefront;

import javax.swing.*;

public class ItemPopupMenu extends JPopupMenu {
    public ItemPopupMenu(ActionController actionController) {
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
