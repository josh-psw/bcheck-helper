package ui.view;

import data.Item;
import ui.view.pane.storefront.Storefront;

import javax.swing.*;
import java.awt.*;

public class Store<T extends Item> extends JTabbedPane {
    public Store(Component settings, Storefront<T>... storefronts) {
        for (Storefront<T> storefront : storefronts) {
            add(storefront.title(), storefront.component());
        }

        add("Settings", settings);
    }
}