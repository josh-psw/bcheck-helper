package ui.view;

import ui.view.pane.settings.Settings;
import ui.view.pane.storefront.Storefront;

import javax.swing.*;

public class Store extends JTabbedPane {
    public Store(Settings settings, Storefront... storefronts) {
        for (Storefront storefront : storefronts)
        {
            add(storefront.title(), storefront.panel());
        }

        add("Settings", settings);
    }
}