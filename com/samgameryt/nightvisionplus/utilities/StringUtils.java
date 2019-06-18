package com.samgameryt.nightvisionplus.utilities;

import org.bukkit.ChatColor;

public class StringUtils {

    public static String translate(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
