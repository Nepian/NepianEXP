package nepian.bukkit.plugin.exp.configration;

import java.io.File;

import nepian.bukkit.plugin.exp.Main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {
	ERROR_COMMAND,
	ERROR_COMMAND_USAGE,
	ERROR_PLAYER_COMMAND,
	ERROR_COMMAND_NO_PERMISSION,
	ERROR_ECONOMY_FAULT,
	ERROR_NOT_VALID_NUMBER,
	ERROR_NUMBER_NOT_POSITIVE,
	ERROR_NOT_ENOUGH_MONEY,
	ERROR_NOT_ENOUGH_EXP,
	ERROR_NOT_ONLINE_PLAYER,

	EXP_CHANGE,
	EXP_HELP,

	EXP_INFO,
	EXP_INFO_TOTALEXP,
	EXP_INFO_NEXT,

	EXP_BUY,

	EXP_SELL,

	EXP_ADD,
	EXP_ADD_TARGET,

	EXP_SET_SENDER,
	EXP_SET_TARGET,

	EXP_RESET_TARGET_MESSAGE,
	EXP_RESET_SENDER_MESSAGE,

	EXP_CONFIG_KEY_AND_VALUE,
	EXP_CONFIG_RELOAD_MESSAGE,
	EXP_CONFIG_RELOAD_LOG,

	EXP_RELOAD,
	EXP_VERSION;

	private final static Main plugin = Main.getInstance();
	private static File configFile = new File(plugin.getDataFolder(), Configs.LANGUAGE_FILE.getString());
	private static FileConfiguration config;

	static {
		saveDefault();
		reload();
	}

	public static void reload() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public static void saveDefault() {
		if (!configFile.exists()) {
			plugin.saveResource(
					Configs.LANGUAGE_FILE.getString(), false);
		}
	}

	private String getKey() {
		return name().toLowerCase().replace("_", "-");
	}

	public String get() {
		String value = config.getString(getKey());
		if (value == null) {
			Logger.log("Missing lang data: " + getKey());
			value = "&c[missing lang data]";
		}
		return ChatColor.translateAlternateColorCodes('&', value);
	}
}
