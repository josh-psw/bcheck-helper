package ui.view.pane.storefront;

import data.Item;
import ui.icons.IconFactory;
import ui.icons.IconType;
import ui.model.StorefrontModel;
import ui.view.listener.SingleHandlerDocumentListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Cursor.DEFAULT_CURSOR;
import static java.awt.Cursor.HAND_CURSOR;
import static java.awt.event.HierarchyEvent.SHOWING_CHANGED;
import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.icons.IconType.CLOSE;
import static ui.icons.IconType.SEARCH;

public class SearchBar extends JTextField {
    private static final String PLACEHOLDER = "Search by name, author, description or tags";
    private static final double CLOSE_ICON_FONT_SCALE_FACTOR = 0.85;

    private final IconFactory iconFactory;
    private final JLabel iconLabel;

    private IconType iconType;
    private int scaledIconSize;

    public SearchBar(IconFactory iconFactory, StorefrontModel<? extends Item> storefrontModel) {
        this.iconFactory = iconFactory;
        this.scaledIconSize = scaledIconSize();
        this.iconType = SEARCH;
        this.iconLabel = new JLabel(iconFactory.scaledIconFor(iconType, scaledIconSize));

        iconLabel.setBorder(createEmptyBorder(0, 0, 0, 5));

        putClientProperty("JTextField.placeholderText", PLACEHOLDER);
        putClientProperty("JTextField.trailingComponent", iconLabel);

        this.getDocument().addDocumentListener(new SingleHandlerDocumentListener(e -> {
            IconType newIconType = e.getDocument().getLength() == 0 ? SEARCH : CLOSE;

            if (newIconType != iconType) {
                iconType = newIconType;
                updateIcon();
            }

            storefrontModel.setSearchFilter(getText());
        }));

        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (iconType == CLOSE) {
                    setText("");
                    setCursor(new Cursor(DEFAULT_CURSOR));
                } else {
                    super.mouseClicked(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (iconType == CLOSE) {
                    setCursor(new Cursor(HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (iconType == CLOSE) {
                    setCursor(new Cursor(DEFAULT_CURSOR));
                }
            }
        });

        addHierarchyListener(e -> {
            if (e.getChangeFlags() == SHOWING_CHANGED && e.getComponent().isShowing()) {
                requestFocusInWindow();
            }
        });
    }

    private int scaledIconSize() {
        int fontSize = getFont().getSize();
        return iconType == SEARCH ? fontSize : (int) (fontSize * CLOSE_ICON_FONT_SCALE_FACTOR);
    }

    private void updateIcon() {
        iconLabel.setIcon(iconFactory.scaledIconFor(iconType, scaledIconSize));
    }

    @Override
    public void updateUI() {
        super.updateUI();

        if (iconFactory != null) {
            scaledIconSize = scaledIconSize();
            updateIcon();
        }
    }
}
