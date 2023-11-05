package ui.view.pane.storefront;

import bcheck.BCheck;
import bcheck.BCheck.Tags;
import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import settings.tags.TagColors;
import ui.controller.StoreController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;
import ui.model.table.BCheckTableModel;
import ui.view.pane.storefront.ActionCallbacks.ButtonTogglingActionCallbacks;
import ui.view.utils.TagRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.util.concurrent.Executor;

import static java.awt.BorderLayout.*;
import static java.awt.FlowLayout.LEADING;
import static java.awt.GridBagConstraints.NONE;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.SwingConstants.VERTICAL;
import static javax.swing.SwingUtilities.invokeLater;
import static ui.model.StorefrontModel.SEARCH_FILTER_CHANGED;
import static ui.model.StorefrontModel.STATUS_CHANGED;

public class Storefront extends JPanel {
    private final StoreController storeController;
    private final JButton copyButton = new JButton("Copy to clipboard");
    private final JButton saveButton = new JButton("Save to file");
    private final JButton saveAllButton = new JButton("Save all BChecks to disk");
    private final JLabel statusLabel = new JLabel();
    private final JPanel previewPanel = new JPanel();
    private final JPanel tablePanel = new JPanel();
    private final JTextArea codePreview = new JTextArea();
    private final JTable bCheckTable = new JTable();
    private final JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT);
    private final BCheckTableModel tableModel = new BCheckTableModel();
    private final JButton refreshButton = new JButton("Refresh");
    private final Executor executor;
    private final JComponent searchBar;
    private final StorefrontModel model;
    private final ActionController actionController;

    public Storefront(StoreController storeController,
                      StorefrontModel storefrontModel,
                      DefaultSaveLocationSettingsReader saveLocationSettingsReader,
                      Executor executor,
                      IconFactory iconFactory) {
        this.storeController = storeController;
        this.executor = executor;
        this.model = storefrontModel;
        this.searchBar = new SearchBar(iconFactory, storefrontModel);
        this.actionController = new ActionController(
                model,
                storeController,
                new SaveLocation(saveLocationSettingsReader),
                executor
        );

        initialiseUi();
        loadBChecks();
    }

    private void initialiseUi() {
        setLayout(new BorderLayout());

        setupButtons();
        setupTablePanel();
        setupPreviewPanel();
        setupSplitPane();

        add(splitPane);

        model.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case STATUS_CHANGED -> statusLabel.setText((String) evt.getNewValue());
                case SEARCH_FILTER_CHANGED -> tableModel.setBChecks(model.getFilteredBChecks());
            }
        });
    }

    private void setupButtons() {
        copyButton.addActionListener(e -> actionController.copySelectedBCheck());
        saveButton.addActionListener(e -> actionController.saveSelectedBCheck(new ButtonTogglingActionCallbacks(saveButton)));
        saveAllButton.addActionListener(e -> actionController.saveAllVisibleBChecks(new ButtonTogglingActionCallbacks(saveAllButton)));
    }

    private void setupSplitPane() {
        JScrollPane previewScrollPane = new JScrollPane(previewPanel);

        previewScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        splitPane.add(tablePanel);
        splitPane.add(previewScrollPane);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(createEmptyBorder(0, 10, 0, 5));
    }

    private void setupTablePanel() {
        model.setSearchFilter("");
        tableModel.setBChecks(model.getFilteredBChecks());

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.addPopupMenuListener(new PopupMenuListener()
        {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                invokeLater(() -> {
                    int rowAtPoint = bCheckTable.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), bCheckTable));
                    if (rowAtPoint > -1) {
                        bCheckTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        JMenuItem copyBCheckMenuItem = new JMenuItem("Copy BCheck");
        copyBCheckMenuItem.addActionListener(l -> actionController.copySelectedBCheck());

        JMenuItem saveBCheckMenuItem = new JMenuItem("Save BCheck");
        saveBCheckMenuItem.addActionListener(l -> actionController.saveSelectedBCheck());

        popupMenu.add(copyBCheckMenuItem);
        popupMenu.add(saveBCheckMenuItem);
        bCheckTable.setComponentPopupMenu(popupMenu);

        bCheckTable.setModel(tableModel);
        bCheckTable.setAutoCreateRowSorter(true);
        bCheckTable.setSelectionMode(SINGLE_SELECTION);
        bCheckTable.getTableHeader().setReorderingAllowed(false);
        bCheckTable.getSelectionModel().addListSelectionListener(e -> handleTableRowChange(e, tableModel));
        bCheckTable.setDefaultRenderer(Tags.class, new TagRenderer(new TagColors()));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());

        refreshButton.addActionListener(e -> loadBChecks());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 0, 0, 12);

        topPanel.add(searchBar, constraints);

        constraints.gridx = 1;
        constraints.weightx = 0;
        constraints.fill = NONE;
        constraints.insets = new Insets(10, 0, 0, 0);

        topPanel.add(refreshButton, constraints);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(10);

        tablePanel.setLayout(borderLayout);

        tablePanel.add(topPanel, NORTH);
        tablePanel.add(new JScrollPane(bCheckTable), CENTER);
        tablePanel.setBorder(createEmptyBorder(0, 0, 5, 5));
    }

    private void loadBChecks() {
        refreshButton.setEnabled(false);

        executor.execute(() -> {
            model.setStatus("");
            storeController.loadData();
            tableModel.setBChecks(model.getFilteredBChecks());

            if (tableModel.getRowCount() > 0) {
                bCheckTable.addRowSelectionInterval(0, 0);
            }

            model.setStatus(storeController.status());
            refreshButton.setEnabled(true);
        });
    }

    private void setupPreviewPanel() {
        Font monospacedFont = new Font(
                "monospaced",
                codePreview.getFont().getStyle(),
                codePreview.getFont().getSize()
        );

        codePreview.setEditable(false);
        codePreview.setFont(monospacedFont);
        codePreview.setWrapStyleWord(true);

        JScrollPane codePreviewScrollPane = new JScrollPane(codePreview);

        JPanel actionPanel = new JPanel(new FlowLayout(LEADING));
        actionPanel.add(copyButton);
        actionPanel.add(saveButton);
        actionPanel.add(new JSeparator(VERTICAL));
        actionPanel.add(saveAllButton);
        actionPanel.add(new JSeparator(VERTICAL));
        actionPanel.add(statusLabel);

        previewPanel.setLayout(new BorderLayout());
        previewPanel.add(codePreviewScrollPane, CENTER);
        previewPanel.add(actionPanel, SOUTH);
    }

    private void handleTableRowChange(ListSelectionEvent selectionEvent, BCheckTableModel tableModel) {
        if (selectionEvent.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = bCheckTable.getSelectedRow();

        BCheck selectedBCheck = selectedRow >= 0
                ? tableModel.getBCheckAtRow(bCheckTable.convertRowIndexToModel(selectedRow))
                : null;

        model.setSelectedBCheck(selectedBCheck);
        String previewText = selectedBCheck == null ? "" : selectedBCheck.script();

        codePreview.setText(previewText);
        codePreview.setCaretPosition(0);
    }
}
