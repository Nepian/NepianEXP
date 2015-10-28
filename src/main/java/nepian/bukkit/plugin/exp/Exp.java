package nepian.bukkit.plugin.exp;

import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Permissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * EXP関連のコマンドを管理するコマンド
 * @author Nepian
 *
 */
public class Exp implements CommandExecutor {
	private Main plugin;

	public Exp(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!(sender instanceof Player)) {
			String msg = Lang.ERROR_PLAYER_COMMAND.get();
			plugin.sendMessage(sender, msg);
			return true;
		}

		if (!Permissions.EXP.hasPermission(sender, label, args)) return true;

		if (args.length >= 1) {
			String expAction = args[0];
			Commands com = Commands.getCommand(expAction);
			if (com != null) {
				com.useCommand(sender, label, args);
				return true;
			}
		}

		String msg = Lang.EXP_VERSION.get()
				.replace("{name}", plugin.getName())
				.replace("{version}", plugin.getDescription().getVersion());
		plugin.sendMessage(sender, msg);

		return true;
	}
}
