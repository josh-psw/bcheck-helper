package ui.view.pane.storefront;

import javax.swing.*;

public class SearchBar extends JTextField {
    private static final String PLACEHOLDER = "Search by name, author, description or tags";

    public SearchBar() {
        putClientProperty("JTextField.placeholderText", PLACEHOLDER);
    }
}
