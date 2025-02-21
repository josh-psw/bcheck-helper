package ui.view.pane.storefront.bcheck;

import bcheck.Item;
import logging.Logger;
import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import ui.controller.StoreController;
import ui.controller.TablePanelController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;
import ui.view.pane.storefront.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class ItemStorefront<T extends Item> implements Storefront {
    private final String title;
    private final JPanel panel;

    public ItemStorefront(String title,
                          StoreController<T> storeController,
                          StorefrontModel<T> storefrontModel,
                          DefaultSaveLocationSettingsReader saveLocationSettingsReader,
                          Executor executor,
                          IconFactory iconFactory,
                          Logger logger,
                          Supplier<Font> fontSupplier) {
        this.title = title;

        ActionController actionController = new DefaultActionController<>(
                storefrontModel,
                storeController,
                new SaveLocation(saveLocationSettingsReader),
                executor,
                logger
        );

        PreviewPanel<T> previewPanel = new PreviewPanel<>(storefrontModel, actionController);

        ItemTablePanel<T> tablePanel = new ItemTablePanel<>(
                new TablePanelController<>(storeController),
                storefrontModel,
                executor,
                iconFactory,
                actionController,
                fontSupplier
        );

        this.panel = new StorefrontPanel(previewPanel, tablePanel);
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public Component component() {
        return panel;
    }
}