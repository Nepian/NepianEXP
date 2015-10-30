package nepian.bukkit.plugin.exp.configration;

import java.util.ArrayList;

import nepian.bukkit.plugin.exp.Main;

import org.bukkit.configuration.file.FileConfiguration;

public enum Configs {
	EXP_BUY_RATE(1.0),
	EXP_SELL_RATE(1.0),
	COLOR_LOGS(true),
	DEBUG_MODE(false),
	LANGUAGE_FILE("lang-jp.yml");

	private static Main plugin = Main.getInstance();
	private static FileConfiguration config = plugin.getConfig();
	private Object def;

	private Configs(Object def) {
		this.def = def;
	}

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
		return config.getInt(getKey(), (int) def);
	}

	public double getDouble() {
		return config.getDouble(getKey(), (double) def);
	}

	public String getString() {
		return config.getString(getKey(), (String) def);
	}

	public boolean getBoolean() {
		return config.getBoolean(getKey(), (boolean) def);
	}

	public static ArrayList<String> getAllParam() {
		ArrayList<String> params = new ArrayList<String>();
		for (Configs value : values()) {
			String key = value.getKey();
			params.add(Lang.EXP_CONFIG_KEY_AND_VALUE.get()
					.replace("{key}", key)
					.replace("{value}", value.getString()));
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
