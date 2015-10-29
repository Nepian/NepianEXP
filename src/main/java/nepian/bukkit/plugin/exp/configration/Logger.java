package nepian.bukkit.plugin.exp.configration;

import java.util.logging.Level;

import nepian.bukkit.plugin.exp.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
	public static void log(String msg) {
		msg = ChatColor.translateAlternateColorCodes('&', "&3[&d" + Main.getInstance().getName() + "&3]&r " + msg);
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
