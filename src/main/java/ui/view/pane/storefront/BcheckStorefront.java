package ui.view.pane.storefront;

import burp.Burp;
import logging.Logger;
import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import ui.controller.StoreController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class BCheckStorefront implements Storefront
{
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
                            Supplier<Font> fontSupplier)
    {
        this.title = title;
        this.panel = new BCheckStorefrontPanel(
                storeController,
                storefrontModel,
                burp,
                saveLocationSettingsReader,
                executor,
                iconFactory,
                logger,
                fontSupplier);
    }

    @Override
    public String title()
    {
        return title;
    }

    @Override
    public JPanel panel()
    {
        return panel;
    }
}