package ui.view.pane.storefront;

import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import ui.controller.StoreController;
import ui.controller.TablePanelController;
import ui.controller.TablePanelController.DefaultTablePanelController;
import ui.icons.IconFactory;
import ui.model.StorefrontModel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executor;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

public class Storefront extends JPanel {
    public Storefront(StoreController storeController,
                      StorefrontModel storefrontModel,
                      DefaultSaveLocationSettingsReader saveLocationSettingsReader,
                      Executor executor,
                      IconFactory iconFactory) {
        super(new BorderLayout());

        ActionController actionController = new ActionController(
                storefrontModel,
                storeController,
                new SaveLocation(saveLocationSettingsReader),
                executor
        );

        JPanel previewPanel = new PreviewPanel(storefrontModel, actionController);

        TablePanelController panelController = new DefaultTablePanelController(storeController);
        JPanel tablePanel = new TablePanel(panelController, storefrontModel, executor, iconFactory, actionController);

        JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT);
        splitPane.add(tablePanel);
        splitPane.add(previewPanel);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(createEmptyBorder(0, 10, 0, 5));

        add(splitPane, CENTER);
    }
}
