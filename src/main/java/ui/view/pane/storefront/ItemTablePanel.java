package ui.view.pane.storefront;

import data.Item;
import data.Tags;
import settings.tags.TagColors;
import ui.controller.TablePanelController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;
import ui.model.table.ItemTableModel;
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

public class ItemTablePanel<T extends Item> extends JPanel {
    private final TablePanelController<T> panelController;
    private final JTable itemTable;
    private final ItemTableModel<T> tableModel;
    private final JButton refreshButton;
    private final Executor executor;
    private final JComponent searchBar;
    private final StorefrontModel<T> model;
    private final ActionController<T> actionController;
    private final Supplier<Font> fontSupplier;
    private final TagRenderer tagRenderer;

    public ItemTablePanel(TablePanelController<T> panelController,
                          StorefrontModel<T> storefrontModel,
                          Executor executor,
                          IconFactory iconFactory,
                          ActionController<T> actionController,
                          Supplier<Font> fontSupplier,
                          TagColors tagColors) {
        super(new BorderLayout());

        this.panelController = panelController;
        this.executor = executor;
        this.model = storefrontModel;
        this.actionController = actionController;
        this.fontSupplier = fontSupplier;
        this.tagRenderer = new TagRenderer(tagColors);
        this.searchBar = new SearchBar(iconFactory, storefrontModel);
        this.tableModel = new ItemTableModel<>();
        this.itemTable = new JTable() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        this.refreshButton = new JButton("Refresh");

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(10);
        setLayout(borderLayout);

        setupTablePanel();

        model.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(SEARCH_FILTER_CHANGED)) {
                List<T> filteredItems = model.getFilteredItems();
                tableModel.setItems(filteredItems);

                String script = filteredItems.size() == 1 ? "script" : "scripts";
                String message = "Showing %d %s".formatted(filteredItems.size(), script);
                model.setStatus(message);

                if (!filteredItems.contains(model.getSelectedItem())) {
                    model.setSelectedItem(null);
                }
            }
        });

        loadItems();
    }

    private void setupTablePanel() {
        model.setSearchFilter("");
        tableModel.setItems(model.getFilteredItems());

        JPopupMenu popupMenu = new ItemPopupMenu<>(actionController);
        popupMenu.addPopupMenuListener(new InertPopupMenuListener()
        {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                invokeLater(() -> {
                    int rowAtPoint = itemTable.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), itemTable));
                    if (rowAtPoint > -1) {
                        itemTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                    }
                });
            }
        });

        itemTable.setComponentPopupMenu(popupMenu);

        itemTable.setModel(tableModel);
        itemTable.setAutoCreateRowSorter(true);
        itemTable.setSelectionMode(SINGLE_SELECTION);
        itemTable.getTableHeader().setReorderingAllowed(false);
        itemTable.getSelectionModel().addListSelectionListener(e -> handleTableRowChange(e, tableModel));
        itemTable.setDefaultRenderer(Tags.class, tagRenderer);

        int rowHeight = (int) (fontSupplier.get().getSize() * 1.5);
        itemTable.setRowHeight(rowHeight);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());

        refreshButton.addActionListener(e -> loadItems());

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
        add(new JScrollPane(itemTable), CENTER);
        setBorder(createEmptyBorder(0, 0, 5, 5));
    }

    private void loadItems() {
        refreshButton.setEnabled(false);

        executor.execute(() -> {
            panelController.loadData();
            tableModel.setItems(model.getFilteredItems());

            if (tableModel.getRowCount() > 0) {
                itemTable.addRowSelectionInterval(0, 0);
            }

            refreshButton.setEnabled(true);

            int tagsColumnIndex = TAGS.columnIndex();
            int maxTagColumnWidth = findMaxColumnWidth(tagsColumnIndex);

            invokeLater(() -> {
                TableColumnModel columnModel = itemTable.getColumnModel();

                for (int i = 0; i < columnModel.getColumnCount(); i++) {
                    TableColumn column = columnModel.getColumn(i);
                    int width = i == tagsColumnIndex ? maxTagColumnWidth : column.getWidth();
                    column.setPreferredWidth(width);
                }

                itemTable.invalidate();
            });
        });
    }

    private int findMaxColumnWidth(int columnIndex) {
        int maxWidth = 0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Dimension preferredSize = itemTable.prepareRenderer(tagRenderer, i, columnIndex).getPreferredSize();
            int width = (int) preferredSize.getWidth();

            maxWidth = Math.max(maxWidth, width);
        }

        return maxWidth;
    }

    private void handleTableRowChange(ListSelectionEvent selectionEvent, ItemTableModel<T> tableModel) {
        if (selectionEvent.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = itemTable.getSelectedRow();

        if (selectedRow >= 0) {
            T newSelectedItem = tableModel.getItemAtRow(itemTable.convertRowIndexToModel(selectedRow));
            model.setSelectedItem(newSelectedItem);
        } else {
            T previouslySelectedItem = model.getSelectedItem();
            int modelRow = tableModel.getItemRow(previouslySelectedItem);

            if (modelRow >= 0) {
                int viewRow = itemTable.convertRowIndexToView(modelRow);
                itemTable.getSelectionModel().setSelectionInterval(viewRow, viewRow);
            }
        }
    }
}