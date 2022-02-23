package xyz.lotho.me.utils;

import net.md_5.bungee.api.ChatColor;

public class Chat {

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
