package com.nepian.nepianexp.configuration;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.nepian.nepianexp.NepianEXP;

public class Logger {
	public static void log(String msg) {
		msg = ChatColor.translateAlternateColorCodes('&', "&3[&d" + NepianEXP.getInstance().getName() + "&3]&r " + msg);
		if (Configs.COLOR_LOGS.getBoolean()) {
			Bukkit.getServer().getConsoleSender().sendMessage(msg);
			return;
		}
		Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor(msg));
	}

	public static void debug(String msg) {
		if (Configs.DEBUG_MODE.getBoolean()) {
			Logger.log("&7[&eDEBUG&7]&r " + msg);
		}
	}
}
