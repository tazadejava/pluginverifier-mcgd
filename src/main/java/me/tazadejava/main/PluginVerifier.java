package me.tazadejava.main;

import me.tazadejava.levelverifier.*;

public class PluginVerifier {

    /*
    Args format:

    1st argument: level (1-6)
    2nd argument: full path to the plugin base folder (before the src folder)

    Will either pass (message starts with "VERIFICATION SUCCESS"), or throw an error if a condition was violated. (starts with "ERROR:")

     */
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("ERROR: You must specify the level and a path to the plugin base folder!");
        } else {
            Plugin plugin = Plugin.init(args[1]);

            if(plugin != null) {
                Level level;
                switch(args[0]) {
                    case "1":
                        level = new Level1();
                        break;
                    case "2":
                        level = new Level2();
                        break;
                    case "3":
                        level = new Level3();
                        break;
                    case "4":
                        level = new Level4();
                        break;
                    case "5":
                        level = new Level5();
                        break;
                    case "6":
                        level = new Level6();
                        break;
                    default:
                        System.out.println("ERROR: Invalid level!");
                        return;
                }

                System.out.println("PARSING FOR LEVEL " + args[0]);

                plugin.discoverSourceFiles();
                plugin.parseSourceFiles();
                plugin.verifyPlugin(level);
            }
        }
    }
}
