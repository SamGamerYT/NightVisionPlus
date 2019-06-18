package com.samgameryt.nightvisionplus.commands;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.samgameryt.nightvisionplus.Main;
import com.samgameryt.nightvisionplus.utilities.StringUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVision implements CommandExecutor {

    private Main main;

    public NightVision(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 0) {
                if(p.hasPermission("nv.use")) {
                    if(main.getToggledArray().contains(p.getUniqueId())) {
                        if(main.getConfig().getBoolean("send-action-bar") && main.actionBarCapable) {
                            ActionBarAPI.sendActionBar(p, StringUtils.translate(main.getConfig().getString("nightvision-toggled.action-bar").replace("{toggled}", main.getConfig().getString("nightvision-toggled.disabled"))));
                            main.getToggledArray().remove(p.getUniqueId());
                            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                        if(main.getConfig().getBoolean("send-message") || !main.actionBarCapable) {
                            main.getToggledArray().remove(p.getUniqueId());
                            p.sendMessage(StringUtils.translate(main.getConfig().getString("nightvision-toggled.message").replace("{toggled}", main.getConfig().getString("nightvision-toggled.disabled"))));
                            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                    } else {
                        if(main.getConfig().getBoolean("send-action-bar") && main.actionBarCapable) {
                            main.getToggledArray().add(p.getUniqueId());
                            ActionBarAPI.sendActionBar(p, StringUtils.translate(main.getConfig().getString("nightvision-toggled.action-bar").replace("{toggled}", main.getConfig().getString("nightvision-toggled.enabled"))), -1);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 32767, 200));
                        }
                        if(main.getConfig().getBoolean("send-message") || !main.actionBarCapable) {
                            main.getToggledArray().add(p.getUniqueId());
                            p.sendMessage(StringUtils.translate(main.getConfig().getString("nightvision-toggled.message").replace("{toggled}", main.getConfig().getString("nightvision-toggled.enabled"))));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 32767, 200));
                        }
                    }
                } else {
                    p.sendMessage(StringUtils.translate(main.getConfig().getString("no-permission")));
                }
            } else {
                if((args.length == 1) && args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                    if(p.hasPermission("nv.reload")) {
                        main.reloadConfig();
                        p.sendMessage(StringUtils.translate(main.getConfig().getString("config-reloaded")));
                    } else {
                        p.sendMessage(StringUtils.translate(main.getConfig().getString("no-permission")));
                    }
                } else {
                    for(String s : main.getConfig().getStringList("usage-message")) {
                        p.sendMessage(StringUtils.translate(s));
                    }
                }
            }
        } else {
            if((args.length == 1) && args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                main.reloadConfig();
                sender.sendMessage(StringUtils.translate(main.getConfig().getString("config-reloaded")));
            } else {
                for(String s : main.getConfig().getStringList("usage-message")) {
                    sender.sendMessage(StringUtils.translate(s));
                }
            }
        }
        return false;
    }

}
