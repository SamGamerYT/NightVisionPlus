package com.samgameryt.nightvisionplus;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.samgameryt.nightvisionplus.commands.NightVision;
import com.samgameryt.nightvisionplus.events.ConnectionEvents;
import com.samgameryt.nightvisionplus.events.PlayerEvents;
import com.samgameryt.nightvisionplus.updatehandler.UpdateChecker;
import com.samgameryt.nightvisionplus.utilities.LogType;
import com.samgameryt.nightvisionplus.utilities.Logger;

import java.util.ArrayList;
import java.util.UUID;
import java.io.*;

public class Main extends JavaPlugin {

    UpdateChecker checker = new UpdateChecker(this);
    Logger log = new Logger();

    public File dataFile = new File(this.getDataFolder() + File.separator + "data" + File.separator + "playerdata.yml");
    public FileConfiguration dataYML = YamlConfiguration.loadConfiguration(dataFile);

    private ArrayList<UUID> toggled = new ArrayList<>();

    public boolean actionBarCapable;

    public void onEnable() {
        checker.checkUpdate();

        if(Bukkit.getPluginManager().getPlugin("ActionBarAPI") == null) {
            log.printLine("NV+", "The ActionBarAPI was not found, ActionBars will not work!", LogType.WARNING);
            actionBarCapable = false;
        } else {
            actionBarCapable = true;
        }

        saveDefaultConfig();

        getCommand("nightvision").setExecutor(new NightVision(this));

        Bukkit.getPluginManager().registerEvents(new ConnectionEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(this), this);
    }

    public void onDisable() {
        log.printLine("NV+", "Saving data to the playerdata.yml!", LogType.INFO);
        for(UUID u : toggled) {
            dataYML.set(u.toString() + ".nv-toggled", true);
        }
        try {
            log.printLine("NV+", "Saving the playerdata.yml!", LogType.INFO);
            dataYML.save(dataFile);
        } catch (IOException e) {
            log.printLine("NV+", "Error while saving the playerdata.yml!", LogType.ERROR);
            e.printStackTrace();
        }
    }

    public ArrayList<UUID> getToggledArray() {
        return toggled;
    }

}
