package ui.clipboard;

import java.awt.datatransfer.StringSelection;

import static java.awt.Toolkit.getDefaultToolkit;

public class ClipboardManager {
    public void copy(String text) {
        getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new StringSelection(text),
                        null
                );
    }
}
