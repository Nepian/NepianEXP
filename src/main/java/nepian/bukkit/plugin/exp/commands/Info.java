package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Info {
	private static final Main plugin = Main.getInstance();

	public static void useCommand(CommandSender sender, String label, String[] args) {
		if (!Permissions.INFO.hasPermission(sender, label, args)) return;

		Player player = (Player) sender;
		int totalExp = ExpManager.getTotalExp(player);
		int level = ExpManager.getLevel(player);
		int next = ExpManager.getRestExpNeededToLevelUp(player);

		String msg1 = Lang.EXP_INFO_TOTALEXP.get()
				.replace("{totalExp}", String.valueOf(totalExp))
				.replace("{level}", String.valueOf(level));

		String msg2 = Lang.EXP_INFO_NEXT.get()
				.replace("{next}", String.valueOf(next));

		plugin.sendMessage(sender, msg1);
		plugin.sendMessage(sender, msg2);
	}
}
