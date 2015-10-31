package nepian.bukkit.plugin.exp.configration;

import java.io.File;

import nepian.bukkit.plugin.exp.Main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public enum CommandData {
	HELP("help", "help", "nepian.exp.help", "�g�p�\�ȃR�}���h�̈ꗗ��\������"),
	INFO("info", "info [player]...", "nepian.exp.info", "�o���l�̏���\������"),
	INFO_PLAYER("player", "info <player> [player]...", "nepian.exp.info.player", "�w��v���C���[�̌o���l�̏���\������"),
	BUY("buy", "buy { <exp> or <level>L }", "nepian.exp.buy", "�o���l���w������"),
	SELL("sell", "sell { <exp> or <level>L }", "nepian.exp.sell", "�o���l�𔄋p����"),
	SEND("send", "send { <exp> or <level>L } <player>", "nepian.exp.send", "�w��v���C���[�Ɍo���l�𑗂�"),
	ADD("add", "add { <exp> or <level>L } [player]...", "nepian.exp.add", "�o���l��ǉ�����"),
	ADD_PLAYER("player", "add { <exp> or <level>L } <player> [player]...", "nepian.exp.add.player", "�w��v���C���[�Ɍo���l��ǉ�����"),
	SET("set", "set { <exp> or <level>L } [player]...", "nepian.exp.set", "�o���l��ݒ肷��"),
	SET_PLAYER("player", "set { <exp> or <level>L } <player> [player]...", "nepian.exp.set.player", "�w��v���C���[�̌o���l��ݒ肷��"),
	RESET("reset", "reset [player]...", "nepian.exp.reset", "�o���l���O�ɂ���"),
	RESET_PLAYER("player", "reset <player> [player]...", "nepian.exp.reset.player", "�w��v���C���[�̌o���l���O�ɂ���"),
	RELOAD("reload", "reload", "nepian.exp.reload", "�v���O�C�����ēǂݍ��݂���"),
	CONFIG("config", "config (reload)", "nepian.exp.config", "config.yml�̃f�[�^���ꗗ�\������"),
	CONFIG_RELOAD("reload", "config reload", "nepian.exp.config.reload", "config.yml���ēǂݍ��݂���"),
	CONFIG_SET("set", "config set <key> <value>", "nepian.exp.config.set", "config.yml�̃f�[�^������������");

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
