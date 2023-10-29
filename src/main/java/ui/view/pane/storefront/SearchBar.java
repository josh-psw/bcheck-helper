package ui.view.pane.storefront;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SearchBar extends JTextField {
    private static final String PLACEHOLDER = "Search by name, author, description or tags";

    public SearchBar() {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (PLACEHOLDER.equals(getText())) {
                    setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!hasSearchText()) {
                    setText(PLACEHOLDER);
                }
            }
        });

        setText(PLACEHOLDER);
    }

    public boolean hasSearchText() {
        return !getText().isBlank() && !isPlaceholderShowing();
    }

    private boolean isPlaceholderShowing() {
        return PLACEHOLDER.equals(getText());
    }
}
