package com.samgameryt.nightvisionplus.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.samgameryt.nightvisionplus.Main;

import java.io.IOException;

public class ConnectionEvents implements Listener {

    private Main main;

    public ConnectionEvents(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(main.getToggledArray().contains(p.getUniqueId())) {
            Bukkit.getScheduler().runTaskLater(main, () -> p.removePotionEffect(PotionEffectType.NIGHT_VISION), 3);
            Bukkit.getScheduler().runTaskLater(main, () -> p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 32767, 200)), 5);
        } else {
            for(String s : main.dataYML.getConfigurationSection("").getKeys(false)) {
                if(p.getUniqueId().toString().equals(s)) {
                    if(main.dataYML.getBoolean(s + ".nv-toggled")) {
                        p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 32767, 200));
                        main.getToggledArray().add(p.getUniqueId());
                        main.dataYML.set(s, null);
                        try {
                            main.dataYML.save(main.dataFile);
                        } catch (IOException exp) {
                            exp.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
