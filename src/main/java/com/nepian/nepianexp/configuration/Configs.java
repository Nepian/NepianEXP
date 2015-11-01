package com.nepian.nepianexp.configuration;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;

import com.nepian.nepianexp.NepianEXP;

public enum Configs {
	EXP_BUY_RATE(1.0),
	EXP_SELL_RATE(1.0),
	COLOR_LOGS(true),
	DEBUG_MODE(false),
	LANGUAGE_FILE("lang-jp.yml"),
	EXP_CMD_DESCRIPTIONS("exp-cmd-decriptions.yml");

	private static NepianEXP plugin = NepianEXP.getInstance();
	private static FileConfiguration config = plugin.getConfig();
	private final Object def;

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

	public void set(String value) {
		config.set(getKey(), value);
	}

	private String getString(Object def) {
		return config.getString(getKey(), String.valueOf(def));
	}

	public String getString() {
		return getString(def);
	}

	public boolean getBoolean() {
		try {
			return Boolean.valueOf(getString(def));
		} catch (Exception e) {
			return (boolean) def;
		}
	}

	public double getDouble() {
		try {
			return Double.valueOf(getString(def));
		} catch (Exception e) {
			return (double) def;
		}
	}

	public static ArrayList<String> getAllParam() {
		ArrayList<String> params = new ArrayList<String>();
		for (Configs config : values()) {
			String key = config.getKey();
			params.add(Lang.EXP_CONFIG_KEY_AND_VALUE.get()
					.replace("{key}", key)
					.replace("{value}", config.getString()));
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
