package me.tazadejava.main;

import me.tazadejava.levelverifier.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Plugin {

    private File mainFolder, sourceFolder;

    private List<PluginClass> sourceFiles;

    private Plugin(File mainFolder) {
        this.mainFolder = mainFolder;
        sourceFolder = new File(mainFolder.getAbsolutePath() + "/src/main/java/");

        sourceFiles = new ArrayList<>();
    }

    public static Plugin init(String path) {
        File pluginFolder = new File(path);

        if(!pluginFolder.isDirectory()) {
            System.out.println("ERROR: Not a plugin folder! Make sure the path points to a directory!");
            return null;
        }

        if(!(new File(pluginFolder.getAbsolutePath() + "/src/").exists())) {
            System.out.println("ERROR: There is no src folder! Are you sure this is a plugin folder?");
            return null;
        }

        return new Plugin(pluginFolder);
    }

    private void discoverSourceFiles(String sourcePath, File currentDirectory) {
        for(File file : currentDirectory.listFiles()) {
            if(file.isDirectory()) {
                if(sourcePath.isEmpty()) {
                    discoverSourceFiles(file.getName(), file);
                } else {
                    if(file.length() == 0) {
                        System.out.println("ERROR: EMPTY FILE PASSED IN.");
                        continue;
                    }

                    discoverSourceFiles(sourcePath + "." + file.getName(), file);
                }
            } else {
                sourceFiles.add(new PluginClass(sourcePath, file));
            }
        }
    }

    public void discoverSourceFiles() {
        discoverSourceFiles("", sourceFolder);
    }

    public void parseSourceFiles() {
        for(PluginClass source : sourceFiles) {
            source.parse();
        }
    }

    public void verifyPlugin(Level level) {
        if(!level.satisfiesConstraints(sourceFiles)) {
            System.out.println("ERROR: " + level.getErrorMessage());
        } else {
            if(level.hasWarnings()) {
                System.out.println("VERIFICATION SUCCESS (WITH WARNINGS)");
            } else {
                System.out.println("VERIFICATION SUCCESS");
            }
        }
    }
}
