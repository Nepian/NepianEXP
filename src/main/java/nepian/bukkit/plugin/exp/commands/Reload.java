package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.Logger;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Permissions;

import org.bukkit.command.CommandSender;

public class Reload {
	private static final Main plugin = Main.getInstance();

	public static void useCommand(CommandSender sender, String label, String[] args) {
		if (!Permissions.RELOAD.hasPermission(sender, label, args)) return;

		Configs.reload();
		Lang.reload();

		String msg = Lang.EXP_RELOAD.get()
				.replace("{name}", plugin.getName());
		plugin.sendMessage(sender, msg);
		Logger.log(msg);
	}
}
