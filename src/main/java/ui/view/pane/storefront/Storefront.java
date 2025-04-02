package ui.view.pane.storefront;

import data.ImportMetadata;
import data.Item;
import logging.Logger;
import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import settings.tags.TagColors;
import ui.controller.StoreController;
import ui.controller.TablePanelController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class Storefront<T extends Item> {
    private final String title;
    private final JPanel panel;

    public Storefront(String title,
                      StoreController<T> storeController,
                      StorefrontModel<T> storefrontModel,
                      DefaultSaveLocationSettingsReader saveLocationSettingsReader,
                      Executor executor,
                      IconFactory iconFactory,
                      Logger logger,
                      Supplier<Font> fontSupplier,
                      ImportMetadata importMetadata,
                      TagColors tagColors) {
        this.title = title;

        ActionController<T> actionController = new ActionController<>(
                storefrontModel,
                storeController,
                new SaveLocation(saveLocationSettingsReader),
                executor,
                logger
        );

        PreviewPanel<T> previewPanel = new PreviewPanel<>(storefrontModel, actionController, importMetadata);

        ItemTablePanel<T> tablePanel = new ItemTablePanel<>(
                new TablePanelController<>(storeController),
                storefrontModel,
                executor,
                iconFactory,
                actionController,
                fontSupplier,
                tagColors
        );

        this.panel = new StorefrontPanel(previewPanel, tablePanel);
    }

    public String title() {
        return title;
    }

    public Component component() {
        return panel;
    }
}