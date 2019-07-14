package com.samgameryt.nightvisionplus.updatehandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.samgameryt.nightvisionplus.Main;
import com.samgameryt.nightvisionplus.utilities.LogType;
import com.samgameryt.nightvisionplus.utilities.Logger;

public class UpdateChecker {

    private Main main;

    public UpdateChecker(Main main) {
        this.main = main;
    }

    Logger logger = new Logger();

    private int resourceNumber = 58282;

    public String getKey() {
        return key;
    }

    public int getResourceNumber() {
        return resourceNumber;
    }

    public void checkUpdate() {
        if(main.getConfig().getBoolean("check-for-updates")) {
            logger.printLine("NV+", "Checking for updates...", LogType.INFO);
            try {
                HttpURLConnection connection = (HttpURLConnection)(new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceNumber)).openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.getOutputStream().write((key + resourceNumber).getBytes());
                String v = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                if (v.equalsIgnoreCase(main.getDescription().getVersion())) {
                    logger.printLine("NV+", "NightVision+ is up to date!", LogType.INFO);
                } else {
                    logger.printLine("NV+", "There is an update available for NightVision+!", LogType.WARNING);
                    logger.printLine("NV+", "Download it at spigotmc.org/resources/nightvision-1-14-support-added-with-source-code.58282", LogType.WARNING);
                }
            } catch (IOException e) {
                logger.printLine("NV+", "Could not connect to Spigot", LogType.ERROR);
                e.printStackTrace();
            }
        }
    }

}
