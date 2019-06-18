package com.samgameryt.nightvisionplus.events;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.samgameryt.nightvisionplus.Main;
import com.samgameryt.nightvisionplus.utilities.StringUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerEvents implements Listener {

    private Main main;

    public PlayerEvents(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if(main.getToggledArray().contains(p.getUniqueId())) {
            if(e.getItem().getType().equals(Material.MILK_BUCKET)) {
                if(main.getConfig().getBoolean("milk-bucket-remove")) {
                    main.getToggledArray().remove(p.getUniqueId());
                    if(main.getConfig().getBoolean("send-action-bar") && main.actionBarCapable) {
                        ActionBarAPI.sendActionBar(p, StringUtils.translate(main.getConfig().getString("nightvision-toggled.action-bar").replace("{toggled}", main.getConfig().getString("nightvision-toggled.disabled"))));
                    }
                    if(main.getConfig().getBoolean("send-message") || !main.actionBarCapable) {
                        p.sendMessage(StringUtils.translate(main.getConfig().getString("nightvision-toggled.message").replace("{toggled}", main.getConfig().getString("nightvision-toggled.disabled"))));
                    }
                } else {
                    Bukkit.getScheduler().runTaskLater(main, () -> p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 32767, 200)), 1);
                }
            }
        }
    }

    @EventHandler
    public void onSpawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if(main.getConfig().getBoolean("keep-effect-ondeath")) {
            if(main.getToggledArray().contains(p.getUniqueId())) {
                Bukkit.getScheduler().runTaskLater(main, () -> p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 32767, 200)), 1);
            }
        } else {
            if(main.getToggledArray().contains(p.getUniqueId())) {
                main.getToggledArray().remove(p.getUniqueId());
            }
        }
    }

}
