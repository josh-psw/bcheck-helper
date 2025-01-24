package ui.view.pane.storefront;

import burp.Burp;
import logging.Logger;
import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import ui.controller.StoreController;
import ui.controller.TablePanelController.DefaultTablePanelController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class BCheckStorefront implements Storefront {
    private final String title;
    private final JPanel panel;

    public BCheckStorefront(String title,
                            StoreController storeController,
                            StorefrontModel storefrontModel,
                            Burp burp,
                            DefaultSaveLocationSettingsReader saveLocationSettingsReader,
                            Executor executor,
                            IconFactory iconFactory,
                            Logger logger,
                            Supplier<Font> fontSupplier) {
        this.title = title;

        BCheckActionController actionController = new BCheckActionController(
                storefrontModel,
                storeController,
                new SaveLocation(saveLocationSettingsReader),
                executor,
                logger
        );

        BCheckPreviewPanel previewPanel = new BCheckPreviewPanel(storefrontModel, actionController, burp);

        BCheckTablePanel tablePanel = new BCheckTablePanel(
                new DefaultTablePanelController(storeController),
                storefrontModel,
                burp,
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