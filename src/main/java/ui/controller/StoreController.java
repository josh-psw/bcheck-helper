package ui.controller;

import bcheck.BCheck;
import bcheck.BCheckManager;
import file.system.FileSystem;
import ui.clipboard.ClipboardManager;

import java.nio.file.Path;
import java.util.List;

public class StoreController {
    private final BCheckManager bCheckManager;
    private final ClipboardManager clipboardManager;
    private final FileSystem fileSystem;

    public StoreController(
            BCheckManager bCheckManager,
            ClipboardManager clipboardManager,
            FileSystem fileSystem
    ) {
        this.bCheckManager = bCheckManager;
        this.clipboardManager = clipboardManager;
        this.fileSystem = fileSystem;
    }

    public List<BCheck> availableBChecks() {
        return bCheckManager.availableBChecks();
    }

    public void refresh() {
        bCheckManager.refresh();
    }

    public List<BCheck> findMatchingBChecks(String text) {
        String searchText = text.toLowerCase();

        return availableBChecks().stream()
                .filter(bCheck -> bCheck.name().toLowerCase().contains(searchText.toLowerCase()) ||
                        bCheck.author().toLowerCase().contains(searchText.toLowerCase()) ||
                        bCheck.description().toLowerCase().contains(searchText.toLowerCase()) ||
                        bCheck.tags().contains(searchText.toLowerCase()))
                .toList();
    }

    public void copyBCheck(BCheck bCheck) {
        clipboardManager.copy(bCheck.script());
    }

    public void saveBCheck(BCheck bCheck, Path savePath) {
        fileSystem.saveFile(bCheck.script(), savePath);
    }
}
