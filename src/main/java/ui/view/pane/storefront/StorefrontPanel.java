package ui.view.pane.storefront;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

class StorefrontPanel extends JPanel {
    StorefrontPanel(JPanel previewPanel, JPanel tablePanel) {
        super(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT);
        splitPane.add(tablePanel);
        splitPane.add(previewPanel);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(createEmptyBorder(0, 10, 0, 5));

        add(splitPane, CENTER);
    }
}