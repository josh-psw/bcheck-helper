package ui.view.pane.storefront;

import bcheck.BCheck;
import bcheck.BCheck.Tags;
import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import settings.tags.TagColors;
import ui.controller.StoreController;
import ui.icons.IconFactory;
import ui.model.table.BCheckTableModel;
import ui.view.listener.SingleHandlerDocumentListener;
import ui.view.utils.TagRenderer;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.List;
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
import static ui.view.component.filechooser.ChooseMode.FILES_ONLY;

public class Storefront extends JPanel {
    private final StoreController storeController;
    private final SaveLocation saveLocation;
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
    private final SearchBar searchBar;

    public Storefront(StoreController storeController,
                      DefaultSaveLocationSettingsReader saveLocationSettingsReader,
                      Executor executor,
                      IconFactory iconFactory) {
        this.storeController = storeController;
        this.executor = executor;
        this.saveLocation = new SaveLocation(saveLocationSettingsReader);
        this.searchBar = new SearchBar(iconFactory);

        initialiseUi();
        loadBChecks();
    }

    private void initialiseUi() {
        setLayout(new BorderLayout());

        setupButtons();
        setupTablePanel();
        setupPreviewPanel();
        setupSplitPane();
        setupAncestorListener();

        add(splitPane);
    }

    private void setupButtons() {
        copyButton.addActionListener(e -> {
            BCheck selectedBCheck = getSelectedBCheck();

            storeController.copyBCheck(selectedBCheck);
            statusLabel.setText("Copied BCheck " + selectedBCheck.name() + " to clipboard");
        });

        saveButton.addActionListener(e -> {
            BCheck selectedBCheck = getSelectedBCheck();

            saveLocation.find(FILES_ONLY, selectedBCheck.filename())
                    .ifPresent(path -> {
                        saveButton.setEnabled(false);
                        statusLabel.setText("");

                        executor.execute(() -> {
                            Path savePath = path.toFile().isDirectory() ? path.resolve(selectedBCheck.filename()) : path;

                            storeController.saveBCheck(selectedBCheck, savePath);
                            statusLabel.setText("Saved BCheck to " + savePath);
                            saveButton.setEnabled(true);
                        });
                    });
        });

        saveAllButton.addActionListener(e ->
                saveLocation.find().ifPresent(path -> {
                    saveAllButton.setEnabled(false);
                    statusLabel.setText("");

                    executor.execute(() -> {
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            BCheck bCheck = tableModel.getBCheckAtRow(bCheckTable.convertRowIndexToModel(i));
                            storeController.saveBCheck(bCheck, path.resolve(bCheck.filename()));
                        }

                        statusLabel.setText("Saved all BChecks to " + path);
                        saveAllButton.setEnabled(true);
                    });
                }));
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
        tableModel.setBChecks(storeController.findMatchingBChecks(""));

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.addPopupMenuListener(new PopupMenuListener()
        {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(() -> {
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
        copyBCheckMenuItem.addActionListener(l -> {
            BCheck selectedBCheck = getSelectedBCheck();

            storeController.copyBCheck(selectedBCheck);
            statusLabel.setText("Copied BCheck " + selectedBCheck.name() + " to clipboard");
        });

        JMenuItem saveBCheckMenuItem = new JMenuItem("Save BCheck");
        saveBCheckMenuItem.addActionListener(l -> {
            BCheck selectedBCheck = getSelectedBCheck();

            saveLocation.find(FILES_ONLY, selectedBCheck.filename())
                    .ifPresent(path -> {
                                statusLabel.setText("");
                                executor.execute(() -> {

                                    Path savePath = path.toFile().isDirectory() ? path.resolve(selectedBCheck.filename()) : path;
                                    storeController.saveBCheck(selectedBCheck, savePath);
                                    statusLabel.setText("Saved BCheck to " + savePath);
                                });
                            }
                    );
        });

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

        searchBar.getDocument().addDocumentListener(new SingleHandlerDocumentListener(e -> updateTable()));

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
            statusLabel.setText("");
            storeController.loadData();
            updateTable();

            if (tableModel.getRowCount() > 0) {
                bCheckTable.addRowSelectionInterval(0, 0);
            }

            statusLabel.setText(storeController.status());
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

    private void setupAncestorListener() {
        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                invokeLater(() -> event.getComponent().requestFocusInWindow());
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        });
    }

    private void updateTable() {
        List<BCheck> bChecks = storeController.findMatchingBChecks(searchBar.getText());

        tableModel.setBChecks(bChecks);
    }

    private BCheck getSelectedBCheck() {
        int selectedRow = bCheckTable.getSelectedRow();
        int selectedModelRow = bCheckTable.convertRowIndexToModel(selectedRow);

        return tableModel.getBCheckAtRow(selectedModelRow);
    }

    private void handleTableRowChange(ListSelectionEvent selectionEvent, BCheckTableModel tableModel) {
        if (selectionEvent.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = bCheckTable.getSelectedRow();

        String previewText = selectedRow >= 0
                ? tableModel.getBCheckAtRow(bCheckTable.convertRowIndexToModel(selectedRow)).script()
                : "";

        codePreview.setText(previewText);
        codePreview.setCaretPosition(0);
    }
}
