package fm.zeldaboy111;

import com.sun.istack.internal.NotNull;

import java.io.File;

public enum Files {
    FILE1("file1.yml"),
    ;

    private static final File dataFolder = Main.plugin.getDataFolder();
    private String path;
    private Boolean isPreMade;
    private FileCreator fileCreator;
    private Boolean fileCreated;

    Files(@NotNull String path) {
        this.path = path;
        this.isPreMade = false;
        prepareAndCreateFile();
    }
    Files(@NotNull String path, @NotNull Boolean isPreMade) {
        this.path = path;
        this.isPreMade = (isPreMade == null ? false : isPreMade);
        prepareAndCreateFile();
    }
    private void prepareAndCreateFile() {
        fileCreated = false;
        // create FileCreator instance, set 'fileCreated' to true/false based upon whether the value is null or not set
        try {
            fileCreator = new FileCreator(getAbsolutePath(), isPreMade);
            fileCreated = true;

        } catch(Exception e) { fileCreated = false; e.printStackTrace(); }
    }

    public String getPath() {
        return path;
    }
    public String getAbsolutePath() {
        return dataFolder.toString() + "\\" + getPath();
    }
    @Override
    public String toString() {
        return "Path: " + getPath() +
                "\nAbsolute path: " + getAbsolutePath() +
                "\nPre-made: " + isPreMade +
                "\nFile created: " + fileCreated +
                "\n";
    }

}
