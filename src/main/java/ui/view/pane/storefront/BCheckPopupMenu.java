package ui.view.pane.storefront;

import javax.swing.*;

class BCheckPopupMenu extends JPopupMenu {

    BCheckPopupMenu(ActionController actionController) {
        JMenuItem importBCheckMenuItem = new JMenuItem("Import BCheck");
        importBCheckMenuItem.addActionListener(l -> actionController.importSelectedBCheck());
        add(importBCheckMenuItem);

        JMenuItem copyBCheckMenuItem = new JMenuItem("Copy BCheck");
        copyBCheckMenuItem.addActionListener(l -> actionController.copySelectedBCheck());
        add(copyBCheckMenuItem);

        JMenuItem saveBCheckMenuItem = new JMenuItem("Save BCheck");
        saveBCheckMenuItem.addActionListener(l -> actionController.saveSelectedBCheck());
        add(saveBCheckMenuItem);
    }
}
