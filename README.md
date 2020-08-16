# PluginVerifier

Program developed for use in HSSP summer 2020 class: **Game Design and Development with Minecraft in Java**

This program will automatically check uploaded plugin code for violations. Specifically, each challenge will have certain restrictions (ex: no events can be used, only this specific event can be used, no flying). The PluginVerifier is used after their plugin has been verified to compile correctly as a last-step verification process.

This program helps automate the plugin development environment during the challenge component of class.

For more information on how to teach this class, see https://minecraftgamedev.github.io/teach/.

## How to use this program

You will need a .jar version of the file. In a terminal, you can check if a project's code does not violate any rules in the challenge via:

        java -jar PluginVerifier LEVEL PATH_TO_MAIN_PROJECT_FOLDER
        
* LEVEL is a number from 1-6

* PATH_TO_MAIN_PROJECT_FOLDER is a path that directs the program to the main project folder of the plugin. This is the folder BEFORE the src folder.

The program will either return a message starting with:

        VERIFICATION SUCCESS
        
if the code passed in does not have any violations, or

        ERROR: ---
        
along with an error message if the code passed in has a violation.