package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reset {
	private static final Main plugin = Main.getInstance();

	public static void useCommand(CommandSender sender, String label, String[] args) {
		if (!Permissions.RESET.hasPermission(sender, label, args)) return;

		Player player = (Player)sender;
		ExpManager.resetExp(player);
		String msg = Lang.EXP_RESET.get();
		plugin.sendMessage(sender, msg);
	}
}
