package ui.view.pane.storefront.bcheck;

import burp.Burp;
import ui.view.pane.storefront.ActionController;

import javax.swing.*;

import static burp.Burp.Capability.BCHECK_IMPORT;

class BCheckPopupMenu extends JPopupMenu {

    BCheckPopupMenu(ActionController actionController, Burp burp) {
        if (burp.hasCapability(BCHECK_IMPORT)) {
            JMenuItem importBCheckMenuItem = new JMenuItem("Import BCheck");
            importBCheckMenuItem.addActionListener(l -> actionController.importSelected());
            add(importBCheckMenuItem);
        }

        JMenuItem copyBCheckMenuItem = new JMenuItem("Copy BCheck");
        copyBCheckMenuItem.addActionListener(l -> actionController.copySelected());
        add(copyBCheckMenuItem);

        JMenuItem saveBCheckMenuItem = new JMenuItem("Save BCheck");
        saveBCheckMenuItem.addActionListener(l -> actionController.saveSelected());
        add(saveBCheckMenuItem);
    }
}
