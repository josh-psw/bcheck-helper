package ui.view.pane.storefront.bcheck;

import bcheck.BCheck;
import logging.Logger;
import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import ui.controller.StoreController;
import ui.controller.TablePanelController.DefaultTablePanelController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;
import ui.view.pane.storefront.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class BCheckStorefront implements Storefront {
    private final String title;
    private final JPanel panel;

    public BCheckStorefront(String title,
                            StoreController storeController,
                            StorefrontModel<BCheck> storefrontModel,
                            DefaultSaveLocationSettingsReader saveLocationSettingsReader,
                            Executor executor,
                            IconFactory iconFactory,
                            Logger logger,
                            Supplier<Font> fontSupplier) {
        this.title = title;

        ActionController actionController = new BCheckActionController(
                storefrontModel,
                storeController,
                new SaveLocation(saveLocationSettingsReader),
                executor,
                logger
        );

        PreviewPanel<BCheck> previewPanel = new PreviewPanel<>(storefrontModel, actionController);

        BCheckTablePanel tablePanel = new BCheckTablePanel(
                new DefaultTablePanelController(storeController),
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