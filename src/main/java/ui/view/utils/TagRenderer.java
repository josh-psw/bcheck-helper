package ui.view.utils;

import bcheck.BCheck.Tags;
import settings.tags.TagColors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

import static java.awt.Color.BLACK;

public class TagRenderer implements TableCellRenderer {
    private static final int HORIZONTAL_SPACING = 10;
    private static final int VERTICAL_SPACING = 10;
    private static final Insets TAG_INSETS = new Insets(VERTICAL_SPACING, HORIZONTAL_SPACING, VERTICAL_SPACING, HORIZONTAL_SPACING);
    private static final Border CELL_BORDER = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    private final TableCellRenderer defaultTableCellRenderer;
    private final TagColors tagColors;

    public TagRenderer(TagColors tagColors) {
        this.tagColors = tagColors;
        this.defaultTableCellRenderer = new DefaultTableCellRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component defaultComponent = defaultTableCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (!(value instanceof Tags tags)) {
            return defaultComponent;
        }

        JPanel cellComponent = new JPanel(new TagLayout());

        cellComponent.setForeground(defaultComponent.getForeground());
        cellComponent.setBackground(defaultComponent.getBackground());
        cellComponent.setBorder(CELL_BORDER);

        for (String tag : tags.tags()) {
            JButton tagComponent = new JButton(tag);
            tagComponent.putClientProperty("JButton.buttonType", "roundRect");
            tagComponent.setMargin(TAG_INSETS);

            tagColors.tagColor(tag).ifPresent(color -> {
                tagComponent.setForeground(BLACK);
                tagComponent.setBackground(color);
            });

            cellComponent.add(tagComponent);
        }

        return cellComponent;
    }

    private static class TagLayout implements LayoutManager {

        @Override
        public void layoutContainer(Container container) {
            synchronized (container.getTreeLock()) {
                Insets containerInsets = container.getInsets();

                int x = containerInsets.left;
                int y = containerInsets.top;

                int internalHeight = container.getSize().height - containerInsets.bottom - y;

                for (Component tagComponent : container.getComponents()) {
                    if (tagComponent.isVisible()) {
                        tagComponent.setBounds(
                                x,
                                y,
                                tagComponent.getPreferredSize().width,
                                internalHeight
                        );

                        x += tagComponent.getSize().width + HORIZONTAL_SPACING;
                    }
                }
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container container) {
            return preferredLayoutSize(container);
        }

        @Override
        public Dimension preferredLayoutSize(Container container) {
            synchronized (container.getTreeLock()) {
                int width = 0;
                int height = 0;

                for (Component tagComponent : container.getComponents()) {
                    if (tagComponent.isVisible()) {
                        Dimension tagComponentPreferredSize = tagComponent.getPreferredSize();

                        width += tagComponentPreferredSize.width + HORIZONTAL_SPACING;
                        height = Math.max(height, tagComponentPreferredSize.height);
                    }
                }

                Insets insets = container.getInsets();
                width += insets.left + insets.right;
                height += insets.top + insets.bottom;

                return new Dimension(width, height);
            }
        }

        @Override
        public void addLayoutComponent(String name, Component component) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeLayoutComponent(Component component) {
            throw new UnsupportedOperationException();
        }
    }
}
