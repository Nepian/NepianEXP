package nepian.bukkit.plugin.exp.commands;

import java.util.ArrayList;

import nepian.bukkit.plugin.exp.Logger;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Permissions;

import org.bukkit.command.CommandSender;

/**
 * コンフィグファイルをコマンドで操作するクラス
 * @author Nepian
 *
 */
public class Config {
	private static final Main plugin = Main.getInstance();

	/**
	 * コマンド使用時に呼び出されるメソッド
	 * @param sender
	 * @param label
	 * @param args コマンド(config <action> <-> <->)
	 */
	public static void useCommand(CommandSender sender, String label, String[] args) {
		if (!Permissions.CONFIG.hasPermission(sender, label, args)) return;

		if (1 < args.length) {
			String action = args[1];
			if (action.equalsIgnoreCase("reload")) { reload(sender, label, args); return; }
		}

		showAllConfigData(sender);
	}

	/**
	 * コンフィグファイルをリロードするメソッド
	 * @param sender
	 * @param label
	 * @param args コマンド(config reload)
	 */
	private static void reload(CommandSender sender, String label, String[] args) {
		if (!Permissions.CONFIG_RELOAD.hasPermission(sender, label, args)) return;

		Configs.reload();
		plugin.sendMessage(sender, Lang.EXP_CONFIG_RELOAD_MESSAGE.get());
		Logger.log(Lang.EXP_CONFIG_RELOAD_LOG.get());
	}

	/**
	 * コンフィグデータを表示するメソッド
	 * @param sender
	 */
	private static void showAllConfigData(CommandSender sender) {
		ArrayList<String> params = Configs.getAllParam();
		for (String param : params) {
			plugin.sendMessage(sender, param);
		}
	}
}
