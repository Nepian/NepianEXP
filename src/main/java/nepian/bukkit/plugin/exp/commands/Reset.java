package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reset {
	private static final Main plugin = Main.getInstance();

	public static void useCommand(CommandSender sender, String label, String[] args) {
		if (!Permissions.RESET.hasPermission(sender, label, args)) return;

		if (args.length != 2) {
			plugin.sendMessage(sender, Lang.ERROR_COMMAND.get());
			plugin.sendMessage(sender, "Žg‚¢•û: /exp reset <player>");
			return;
		}

		Player target = getPlayer(args[1]);

		if (target == null) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ONLINE_PLAYER.get());
			return;
		}

		ExpManager.resetExp(target);

		plugin.sendMessage(sender, Lang.EXP_RESET_SENDER_MESSAGE.get()
				.replace("{target}", target.getName()));
		plugin.sendMessage(target, Lang.EXP_RESET_TARGET_MESSAGE.get()
				.replace("{sender}", sender.getName()));
	}

	private static Player getPlayer(String name) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}

		return null;
	}
}
