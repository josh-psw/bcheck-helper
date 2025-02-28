package ui.view;

import ui.view.pane.storefront.Storefront;

import javax.swing.*;
import java.awt.*;

public class Store extends JTabbedPane {
    public Store(Component settings, Storefront... storefronts) {
        for (Storefront storefront : storefronts) {
            add(storefront.title(), storefront.component());
        }

        add("Settings", settings);
    }
}