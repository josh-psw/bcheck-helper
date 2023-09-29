package ui.view.pane.storefront;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SearchBar extends JTextField {
    private static final String PLACEHOLDER = "Search by name, author, description or tags";

    public SearchBar() {
        super();

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if (PLACEHOLDER.equals(getText())) {
                    setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

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

    public boolean isPlaceholderShowing() {
        return PLACEHOLDER.equals(getText());
    }
}
