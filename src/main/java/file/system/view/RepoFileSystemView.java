package file.system.view;

import javax.swing.*;
import java.io.File;
import java.util.List;

import static java.lang.String.join;
import static java.util.Arrays.copyOfRange;

public class RepoFileSystemView extends CustomFileSystemView {
    private static final String PATH_SPLITTER = "/"; //todo: make this a char

    private final String repo;
    private final List<String> repoFolders;

    public RepoFileSystemView(String repo, List<String> repoFolders) {
        this.repo = repo;
        this.repoFolders = repoFolders;

        repoFolders.add(PATH_SPLITTER);
    }

    @Override
    public boolean isRoot(File f) {
        return f.getName().equals(PATH_SPLITTER);
    }

    @Override
    public Boolean isTraversable(File f) {
        return true;
    }

    @Override
    public String getSystemDisplayName(File f) {
        return f.getName();
    }

    @Override
    public String getSystemTypeDescription(File f) {
        return "Structure of " + repo;
    }

    @Override
    public boolean isParent(File folder, File file) {
        return super.isParent(folder, file);
    }

    @Override
    public File getChild(File parent, String fileName) {
        for (var folder : repoFolders) {
            if (folder.equals(parent + PATH_SPLITTER + fileName)) {
                return new RepoFile(folder);
            }
        }

        throw new RuntimeException("Can't find child for parent " + parent + " and file " + fileName);
    }

    @Override
    public boolean isFileSystem(File f) {
        return false;
    }

    @Override
    public boolean isHiddenFile(File f) {
        return false;
    }

    @Override
    public boolean isFileSystemRoot(File dir) {
        return dir.getName().equals(PATH_SPLITTER);
    }

    @Override
    public boolean isComputerNode(File dir) {
        return false;
    }

    @Override
    public File[] getRoots() {
        return new RepoFile[]{
                new RepoFile(PATH_SPLITTER)
        };
    }

    @Override
    public File getHomeDirectory() {
        return new RepoFile(PATH_SPLITTER);
    }

    @Override
    public File createFileObject(String path) {
        return new RepoFile(path);
    }

    @Override
    public File createFileObject(File dir, String filename) {
        return new RepoFile(dir);
    }

    @Override
    public File getDefaultDirectory() {
        return new RepoFile(PATH_SPLITTER);
    }

    @Override
    public Icon getSystemIcon(File f, int width, int height) {
        return UIManager.getIcon("FileView.directoryIcon");
    }

    @Override
    public Icon getSystemIcon(File f) {
        return UIManager.getIcon("FileView.directoryIcon");
    }

    @Override
    public File[] getFiles(File dir, boolean useFileHiding) {
        String directoryPath = dir.getPath();

        if (directoryPath.isEmpty() || PATH_SPLITTER.equals(directoryPath)) {
            return repoFolders.stream()
                    .filter(folder -> !folder.contains(PATH_SPLITTER))
                    .map(RepoFile::new)
                    .toArray(RepoFile[]::new);
        }

        return repoFolders.stream().filter(folder -> {
                    var prefix = directoryPath + PATH_SPLITTER;
                    var indexOfPrefix = folder.indexOf(prefix);

                    return indexOfPrefix == 0 &&
                            occurrencesOfStringInString('/', folder) == occurrencesOfStringInString('/', prefix);
                })
                .map(RepoFile::new)
                .toArray(RepoFile[]::new);
    }

    @Override
    public File getParentDirectory(File dir) {
        var path = dir.getAbsolutePath();
        var pathSegments = path.split(PATH_SPLITTER);
        var numberOfSegments = pathSegments.length;

        if (pathSegments.length > 1) {
            var newPathSegments = copyOfRange(pathSegments, 0, numberOfSegments - 1);
            var newPath = join(PATH_SPLITTER, newPathSegments);

            return new RepoFile(newPath);
        }

        return new RepoFile("/");
    }

    @Override
    public File[] getChooserComboBoxFiles() {
        return new RepoFile[0];
    }

    @Override
    public boolean isLink(File file) {
        return false;
    }

    @Override
    public File getLinkLocation(File file) {
        return null;
    }

    @Override
    protected File createFileSystemRoot(File f) {
        return new RepoFile(PATH_SPLITTER);
    }

    @Override
    public File createNewFolder(File containingDir) {
        return null;
    }

    @Override
    public String root() {
        return PATH_SPLITTER;
    }

    private int occurrencesOfStringInString(char charToFind, String stringToSearch) {
        var count = 0;

        for (var i = 0; i < stringToSearch.length(); i++) {
            if (stringToSearch.charAt(i) == charToFind) {
                count++;
            }
        }

        return count;
    }

    private static class RepoFile extends File {
        private RepoFile(File file) {
            super(file.toString());
        }

        private RepoFile(String pathname) {
            super(pathname);
        }

        @Override
        public boolean exists() {
            return true;
        }

        @Override
        public String getAbsolutePath() {
            return getPath();
        }

        @Override
        public String getCanonicalPath() {
            return getPath();
        }

        @Override
        public boolean isFile() {
            return false;
        }

        @Override
        public boolean isDirectory() {
            return true;
        }

        @Override
        public File getCanonicalFile() {
            return new RepoFile(getCanonicalPath());
        }

        @Override
        public boolean isAbsolute() {
            return true;
        }

        @Override
        public File getAbsoluteFile() {
            return new RepoFile(getAbsolutePath());
        }

        @Override
        public File getParentFile() {
            File parent = super.getParentFile();

            if (parent != null) {
                parent = new RepoFile(super.getParentFile());
            }

            return parent;
        }
    }
}
