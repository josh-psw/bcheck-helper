package ui.view.pane.storefront;

import bcheck.BCheck;
import bcheck.BCheck.Tags;
import settings.tags.TagColors;
import ui.controller.TablePanelController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;
import ui.model.table.BCheckTableModel;
import ui.view.listener.InertPopupMenuListener;
import ui.view.utils.TagRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.GridBagConstraints.NONE;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static javax.swing.SwingUtilities.invokeLater;
import static ui.model.StorefrontModel.SEARCH_FILTER_CHANGED;
import static ui.model.table.TableColumnMetadata.TAGS;

class TablePanel extends JPanel {
    private final TablePanelController panelController;
    private final JTable bCheckTable;
    private final BCheckTableModel tableModel;
    private final JButton refreshButton;
    private final Executor executor;
    private final JComponent searchBar;
    private final StorefrontModel model;
    private final ActionController actionController;
    private final Supplier<Font> fontSupplier;
    private final TagRenderer tagRenderer;

    TablePanel(TablePanelController panelController,
               StorefrontModel storefrontModel,
               Executor executor,
               IconFactory iconFactory,
               ActionController actionController,
               Supplier<Font> fontSupplier) {
        super(new BorderLayout());

        this.panelController = panelController;
        this.executor = executor;
        this.model = storefrontModel;
        this.actionController = actionController;
        this.fontSupplier = fontSupplier;
        this.searchBar = new SearchBar(iconFactory, storefrontModel);
        this.tableModel = new BCheckTableModel();
        this.refreshButton = new JButton("Refresh");
        this.tagRenderer = new TagRenderer(new TagColors());
        this.bCheckTable = new JTable() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(10);
        setLayout(borderLayout);

        setupTablePanel();

        model.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(SEARCH_FILTER_CHANGED)) {
                List<BCheck> filteredBChecks = model.getFilteredBChecks();
                tableModel.setBChecks(filteredBChecks);

                String script = filteredBChecks.size() == 1 ? "script" : "scripts";
                String message = "Showing %d %s".formatted(filteredBChecks.size(), script);
                model.setStatus(message);

                if (!filteredBChecks.contains(model.getSelectedBCheck())) {
                    model.setSelectedBCheck(null);
                }
            }
        });

        loadBChecks();
    }

    private void setupTablePanel() {
        model.setSearchFilter("");
        tableModel.setBChecks(model.getFilteredBChecks());

        JPopupMenu popupMenu = new BCheckPopupMenu(actionController);
        popupMenu.addPopupMenuListener(new InertPopupMenuListener()
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
        });

        bCheckTable.setComponentPopupMenu(popupMenu);

        bCheckTable.setModel(tableModel);
        bCheckTable.setAutoCreateRowSorter(true);
        bCheckTable.setSelectionMode(SINGLE_SELECTION);
        bCheckTable.getTableHeader().setReorderingAllowed(false);
        bCheckTable.getSelectionModel().addListSelectionListener(e -> handleTableRowChange(e, tableModel));
        bCheckTable.setDefaultRenderer(Tags.class, tagRenderer);

        int rowHeight = (int) (fontSupplier.get().getSize() * 1.5);
        bCheckTable.setRowHeight(rowHeight);

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

        add(topPanel, NORTH);
        add(new JScrollPane(bCheckTable), CENTER);
        setBorder(createEmptyBorder(0, 0, 5, 5));
    }

    private void loadBChecks() {
        refreshButton.setEnabled(false);

        executor.execute(() -> {
            panelController.loadData();
            tableModel.setBChecks(model.getFilteredBChecks());

            if (tableModel.getRowCount() > 0) {
                bCheckTable.addRowSelectionInterval(0, 0);
            }

            refreshButton.setEnabled(true);

            int tagsColumnIndex = TAGS.columnIndex();
            int maxTagColumnWidth = findMaxColumnWidth(tagsColumnIndex);

            invokeLater(() -> {
                TableColumnModel columnModel = bCheckTable.getColumnModel();

                for (int i = 0; i < columnModel.getColumnCount(); i++) {
                    TableColumn column = columnModel.getColumn(i);
                    int width = i == tagsColumnIndex ? maxTagColumnWidth : column.getWidth();
                    column.setPreferredWidth(width);
                }

                bCheckTable.invalidate();
            });
        });
    }

    private int findMaxColumnWidth(int columnIndex) {
        int maxWidth = 0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Dimension preferredSize = bCheckTable.prepareRenderer(tagRenderer, i, columnIndex).getPreferredSize();
            int width = (int) preferredSize.getWidth();

            maxWidth = Math.max(maxWidth, width);
        }

        return maxWidth;
    }

    private void handleTableRowChange(ListSelectionEvent selectionEvent, BCheckTableModel tableModel) {
        if (selectionEvent.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = bCheckTable.getSelectedRow();

        if (selectedRow >= 0) {
            BCheck newSelectedBCheck = tableModel.getBCheckAtRow(bCheckTable.convertRowIndexToModel(selectedRow));
            model.setSelectedBCheck(newSelectedBCheck);
        } else {
            BCheck previouslySelectedBCheck = model.getSelectedBCheck();
            int modelRow = tableModel.getBCheckRow(previouslySelectedBCheck);

            if (modelRow >= 0) {
                int viewRow = bCheckTable.convertRowIndexToView(modelRow);
                bCheckTable.getSelectionModel().setSelectionInterval(viewRow, viewRow);
            }
        }
    }
}
