package nepian.bukkit.plugin.exp.configration;

import java.io.File;

import nepian.bukkit.plugin.exp.Main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {
	ERROR_COMMAND("&4�R�}���h���Ԉ���Ă��܂�&r"),
	ERROR_COMMAND_USAGE("�g����: &6/{label} {usage}&r"),
	ERROR_PLAYER_COMMAND("&4���̃R�}���h�̓v���C���[�������p�ł��܂���&r"),
	ERROR_COMMAND_NO_PERMISSION("&6/{label} {usage} &4�����s���邽�߂̌���������܂���&r"),
	ERROR_ECONOMY_FAULT("&4�G���[���������܂���: &7{error}&r"),
	ERROR_NOT_VALID_NUMBER("&4���l����͂��Ă�������&r"),
	ERROR_NUMBER_NOT_POSITIVE("&41�ȏ�̐��l����͂��Ă�������&r"),
	ERROR_NOT_ENOUGH_MONEY("&4���������s�����Ă��܂�&r"),
	ERROR_NOT_ENOUGH_EXP("&4�o���l���s�����Ă��܂�&r"),
	ERROR_NOT_ONLINE_PLAYER("&4�w�肵���v���C���[ &9{player}&4 �͑��݂��܂���&r"),

	EXP_CHANGE("&a&l{oldExp}&r (lvl: &2{oldLevel}&r) �� &a&l{newExp}&r (lvl: &2{newLevel}&r)"),
	EXP_HELP("&c/{label} {usage}&r : &e{description}&r"),

	EXP_INFO("&6{player}&b �̌o���l����\��&r"),
	EXP_INFO_TOTALEXP("�o���l����... &a&l{totalExp}&r (lvl: &2{level}&r)"),
	EXP_INFO_NEXT("���̃��x���܂ł���... &d&l{next}&r"),

	EXP_BUY("�o���l�� &d&l{amount}&r �w�����܂���"),

	EXP_SELL("�o���l�� &d&l{amount}&r ���p���܂���"),

	EXP_SEND_SENDER("�o���l�� &6{player}&r �� &d&l{amount}&r �n���܂���"),
	EXP_SEND_TARGET("�o���l�� &6{player}&r ���� &d&l{amount}&r �󂯎��܂���"),

	EXP_ADD("�o���l�� &6{player}&r �� &d&l{amount}&r �ǉ����܂���"),
	EXP_ADD_TARGET("&6{player}&r �ɂ���Čo���l�� &d&l{amount}&r �ǉ�����܂���"),

	EXP_SET_SENDER("&6{player}&r �̌o���l�� &d&l{amount}&r �ɐݒ肵�܂���"),
	EXP_SET_TARGET("&6{player}&r �ɂ���Čo���l�� &d&l{amount}&r �ɐݒ肳��܂���"),

	EXP_RESET_TARGET_MESSAGE("&6{sender}&b �ɂ���Čo���l�����Z�b�g����܂���&r"),
	EXP_RESET_SENDER_MESSAGE("&6{target}&b �̌o���l�����Z�b�g���܂���&r"),

	EXP_CONFIG_KEY_AND_VALUE("&c{key}&r : &e{value}&r"),
	EXP_CONFIG_RELOAD_MESSAGE("&b�R���t�B�O�������[�h���܂���&r"),
	EXP_CONFIG_RELOAD_LOG("&b�R���t�B�O�������[�h����܂���&r"),
	EXP_CONFIG_SET_NOT_KEY("&c[�G���[] &e{key}&c �����݂��܂���&r"),
	EXP_CONFIG_SET_CHANGE("&c{key}&r : &e{oldValue}&r �� &e{newValue}&r"),
	EXP_CONFIG_SET_END("&c�����[�h�R�}���h���s��Ƀf�[�^�����f����܂�&r"),

	EXP_RELOAD("&d�v���O�C���������[�h���܂���&r"),

	EXP_MESSAGE("�g�p�\�ȃR�}���h�� &6/exp help&r �Ŋm�F�ł��܂�"),
	EXP_VERSION("&d{name} v{version}.&r");

	private final static Main plugin = Main.getInstance();
	private static File configFile = new File(plugin.getDataFolder(), Configs.LANGUAGE_FILE.getString());
	private static FileConfiguration config;
	private String def;

	static {
		saveDefault();
		reload();
	}

	private Lang(String def) {
		this.def = def;
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
		String value = config.getString(getKey(), def);
		if (value == null) {
			Logger.log("Missing lang data: " + getKey());
			value = "&c[missing lang data]";
		}
		return ChatColor.translateAlternateColorCodes('&', value);
	}
}
