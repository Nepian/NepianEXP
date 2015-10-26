package nepian.bukkit.plugin.exp.configration;

import java.util.ArrayList;

import nepian.bukkit.plugin.exp.Main;

import org.bukkit.configuration.file.FileConfiguration;

public enum Configs {
	EXP_BUY_RATE,
	EXP_SELL_RATE,
	COLOR_LOGS,
	DEBUG_MODE,
	LANGUAGE_FILE;

	private static Main plugin = Main.getInstance();
	private static FileConfiguration config = plugin.getConfig();

	public static void reload() {
		plugin.reloadConfig();
		config = plugin.getConfig();
	}

	private String getKey() {
		return name().toLowerCase().replace("_", "-");
	}

	public static void set(String key, Object value) {
		config.set(key, value);
	}

	public int getInt() {
		return config.getInt(getKey());
	}

	public double getDouble() {
		return config.getDouble(getKey());
	}

	public String getString() {
		return config.getString(getKey());
	}

	public boolean getBoolean() {
		return config.getBoolean(getKey());
	}

	public static ArrayList<String> getAllParam() {
		ArrayList<String> params = new ArrayList<String>();
		for (Configs param : values()) {
			String key = param.getKey();
			params.add("&e" + key + "&r : &c" + param.getString());
		}
		return params;
	}

	public static Configs getEnum(String key) {
		for (Configs config : values()) {
			if (config.getKey().equals(key)) {
				return config;
			}
		}
		return null;
	}
}
