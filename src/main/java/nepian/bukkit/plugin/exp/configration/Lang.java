package nepian.bukkit.plugin.exp.configration;

import java.io.File;

import nepian.bukkit.plugin.exp.Main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {
	ERROR_COMMAND("&4コマンドが間違っています&r"),
	ERROR_COMMAND_USAGE("使い方: &6/{label} {usage}&r"),
	ERROR_PLAYER_COMMAND("&4このコマンドはプレイヤーしか利用できません&r"),
	ERROR_COMMAND_NO_PERMISSION("&6/{label} {usage} &4を実行するための権限がありません&r"),
	ERROR_ECONOMY_FAULT("&4エラーが発生しました: &7{error}&r"),
	ERROR_NOT_VALID_NUMBER("&4数値を入力してください&r"),
	ERROR_NUMBER_NOT_POSITIVE("&41以上の数値を入力してください&r"),
	ERROR_NOT_ENOUGH_MONEY("&4所持金が不足しています&r"),
	ERROR_NOT_ENOUGH_EXP("&4経験値が不足しています&r"),
	ERROR_NOT_ONLINE_PLAYER("&4指定したプレイヤー &9{player}&4 は存在しません&r"),

	EXP_CHANGE("&a&l{oldExp}&r (lvl: &2{oldLevel}&r) ⇒ &a&l{newExp}&r (lvl: &2{newLevel}&r)"),
	EXP_HELP("&c/{label} {usage}&r : &e{description}&r"),

	EXP_INFO("&6{player}&b の経験値情報を表示&r"),
	EXP_INFO_TOTALEXP("経験値総量... &a&l{totalExp}&r (lvl: &2{level}&r)"),
	EXP_INFO_NEXT("次のレベルまであと... &d&l{next}&r"),

	EXP_BUY("経験値を &d&l{amount}&r 購入しました"),

	EXP_SELL("経験値を &d&l{amount}&r 売却しました"),

	EXP_SEND_SENDER("経験値を &6{player}&r に &d&l{amount}&r 渡しました"),
	EXP_SEND_TARGET("経験値を &6{player}&r から &d&l{amount}&r 受け取りました"),

	EXP_ADD("経験値を &6{player}&r に &d&l{amount}&r 追加しました"),
	EXP_ADD_TARGET("&6{player}&r によって経験値が &d&l{amount}&r 追加されました"),

	EXP_SET_SENDER("&6{player}&r の経験値を &d&l{amount}&r に設定しました"),
	EXP_SET_TARGET("&6{player}&r によって経験値が &d&l{amount}&r に設定されました"),

	EXP_RESET_TARGET_MESSAGE("&6{sender}&b によって経験値がリセットされました&r"),
	EXP_RESET_SENDER_MESSAGE("&6{target}&b の経験値をリセットしました&r"),

	EXP_CONFIG_KEY_AND_VALUE("&c{key}&r : &e{value}&r"),
	EXP_CONFIG_RELOAD_MESSAGE("&bコンフィグをリロードしました&r"),
	EXP_CONFIG_RELOAD_LOG("&bコンフィグがリロードされました&r"),
	EXP_CONFIG_SET_NOT_KEY("&c[エラー] &e{key}&c が存在しません&r"),
	EXP_CONFIG_SET_CHANGE("&c{key}&r : &e{oldValue}&r ⇒ &e{newValue}&r"),
	EXP_CONFIG_SET_END("&cリロードコマンド実行後にデータが反映されます&r"),

	EXP_RELOAD("&dプラグインをリロードしました&r"),

	EXP_MESSAGE("使用可能なコマンドは &6/exp help&r で確認できます"),
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
