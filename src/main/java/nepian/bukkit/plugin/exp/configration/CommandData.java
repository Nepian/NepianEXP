package nepian.bukkit.plugin.exp.configration;

import java.io.File;

import nepian.bukkit.plugin.exp.Main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public enum CommandData {
	HELP("help", "help", "nepian.exp.help", "使用可能なコマンドの一覧を表示する"),
	INFO("info", "info [player]...", "nepian.exp.info", "経験値の情報を表示する"),
	INFO_PLAYER("player", "info <player> [player]...", "nepian.exp.info.player", "指定プレイヤーの経験値の情報を表示する"),
	BUY("buy", "buy { <exp> or <level>L }", "nepian.exp.buy", "経験値を購入する"),
	SELL("sell", "sell { <exp> or <level>L }", "nepian.exp.sell", "経験値を売却する"),
	SEND("send", "send { <exp> or <level>L } <player>", "nepian.exp.send", "指定プレイヤーに経験値を送る"),
	ADD("add", "add { <exp> or <level>L } [player]...", "nepian.exp.add", "経験値を追加する"),
	ADD_PLAYER("player", "add { <exp> or <level>L } <player> [player]...", "nepian.exp.add.player", "指定プレイヤーに経験値を追加する"),
	SET("set", "set { <exp> or <level>L } [player]...", "nepian.exp.set", "経験値を設定する"),
	SET_PLAYER("player", "set { <exp> or <level>L } <player> [player]...", "nepian.exp.set.player", "指定プレイヤーの経験値を設定する"),
	RESET("reset", "reset [player]...", "nepian.exp.reset", "経験値を０にする"),
	RESET_PLAYER("player", "reset <player> [player]...", "nepian.exp.reset.player", "指定プレイヤーの経験値を０にする"),
	RELOAD("reload", "reload", "nepian.exp.reload", "プラグインを再読み込みする"),
	CONFIG("config", "config (reload)", "nepian.exp.config", "config.ymlのデータを一覧表示する"),
	CONFIG_RELOAD("reload", "config reload", "nepian.exp.config.reload", "config.ymlを再読み込みする"),
	CONFIG_SET("set", "config set <key> <value>", "nepian.exp.config.set", "config.ymlのデータを書き換える");

	private CommandData(String permission, String description) {
		this.permission = permission;
		this.description = description;
	}

	private CommandData(String name, String usage, String permission, String description) {
		this.name = name;
		this.usage = usage;
		this.permission = permission;
		this.description = description;
	}

	private static final Main plugin;
	private static File configFile;
	private static FileConfiguration config;
	private String name;
	private String usage;
	private String permission;
	private String description;

	static {
		plugin = Main.getInstance();
		configFile = new File(plugin.getDataFolder(),
				Configs.EXP_CMD_DESCRIPTIONS.getString());
		CommandData.saveDefault();
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public static void reload() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public static void saveDefault() {
		if (!configFile.exists()) {
			plugin.saveResource(
					Configs.EXP_CMD_DESCRIPTIONS.getString(), false);
		}
	}

	private String getKey() {
		return name().toLowerCase().replace("_", "-");
	}

	private String get(String key, String def) {
		String value = config.getString(key, def);
		if (value == null) {
			Logger.log("Missing lang data: " + key);
			value = "&c[missing lang data]";
		}
		return ChatColor.translateAlternateColorCodes('&', value);
	}

	public String getDescription() {
		return get(getKey() + ".description", description);
	}

	public String getName() {
		return name;
	}

	public String getUsage() {
		return usage;
	}

	public String getPermission() {
		return permission;
	}
}
