package ui.view.pane.storefront;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import static java.awt.BorderLayout.CENTER;
import static java.awt.event.HierarchyEvent.SHOWING_CHANGED;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.SwingUtilities.invokeLater;

public class StorefrontPanel extends JPanel {
    public StorefrontPanel(JPanel previewPanel, JPanel tablePanel) {
        super(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT);
        splitPane.add(tablePanel);
        splitPane.add(previewPanel);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(createEmptyBorder(0, 10, 0, 5));

        add(splitPane, CENTER);
        addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                if (e.getChangeFlags() == SHOWING_CHANGED && e.getComponent().isShowing()) {
                    invokeLater(() -> splitPane.setDividerLocation(0.5));
                    removeHierarchyListener(this);
                }
            }
        });
    }
}