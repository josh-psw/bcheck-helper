package ui.view.pane.storefront;

import bcheck.BCheck;
import ui.model.StorefrontModel;
import ui.view.pane.storefront.ActionCallbacks.ButtonTogglingActionCallbacks;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.FlowLayout.LEADING;
import static javax.swing.SwingConstants.VERTICAL;
import static ui.model.StorefrontModel.*;

class PreviewPanel extends JPanel {
    private final StorefrontModel model;
    private final ActionController actionController;
    private final JLabel statusLabel;
    private final JButton copyButton;
    private final JButton saveButton;
    private final JButton saveAllButton;

    PreviewPanel(StorefrontModel storefrontModel, ActionController actionController) {
        super(new BorderLayout());

        this.model = storefrontModel;
        this.actionController = actionController;

        this.statusLabel = new JLabel();
        this.copyButton = new JButton("Copy to clipboard");
        this.saveButton = new JButton("Save to file");
        this.saveAllButton = new JButton("Save all BChecks to disk");

        JTextArea codePreview = buildCodePreview();

        add(new JScrollPane(codePreview), CENTER);
        add(buildActionPanel(), SOUTH);

        model.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case STATUS_CHANGED -> statusLabel.setText((String) evt.getNewValue());
                case SELECTED_BCHECK_CHANGED -> {
                    BCheck selectedBCheck = model.getSelectedBCheck();
                    boolean bCheckSelected = selectedBCheck != null;

                    copyButton.setEnabled(bCheckSelected);
                    saveButton.setEnabled(bCheckSelected);

                    String previewText = bCheckSelected ? selectedBCheck.script() : "";

                    codePreview.setText(previewText);
                    codePreview.setCaretPosition(0);
                }
                case SEARCH_FILTER_CHANGED, BCHECKS_UPDATED -> {
                    boolean bChecksEmpty = model.getFilteredBChecks().isEmpty();
                    saveAllButton.setEnabled(!bChecksEmpty);
                }
            }
        });
    }

    private JTextArea buildCodePreview() {
        JTextArea codePreview = new JTextArea();
        Font monospacedFont = new Font(
                "monospaced",
                codePreview.getFont().getStyle(),
                codePreview.getFont().getSize()
        );

        codePreview.setEditable(false);
        codePreview.setFont(monospacedFont);
        codePreview.setWrapStyleWord(true);
        codePreview.setComponentPopupMenu(new BCheckPopupMenu(actionController));

        return codePreview;
    }

    private JComponent buildActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(LEADING));

        copyButton.addActionListener(e -> actionController.copySelectedBCheck());
        saveButton.addActionListener(e -> actionController.saveSelectedBCheck(new ButtonTogglingActionCallbacks(saveButton)));
        saveAllButton.addActionListener(e -> actionController.saveAllVisibleBChecks(new ButtonTogglingActionCallbacks(saveAllButton)));

        actionPanel.add(copyButton);
        actionPanel.add(saveButton);
        actionPanel.add(new JSeparator(VERTICAL));
        actionPanel.add(saveAllButton);
        actionPanel.add(new JSeparator(VERTICAL));
        actionPanel.add(statusLabel);

        return actionPanel;
    }
}
