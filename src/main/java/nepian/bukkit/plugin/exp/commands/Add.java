package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Add {
	private static final Main plugin = Main.getInstance();

	public static void useCommand(CommandSender sender, String label, String[] args) {
		if (!Permissions.ADD.hasPermission(sender, label, args)) return;

		if (args.length < 2) {
			plugin.sendMessage(sender, Lang.EXP_ADD_DESCRIPTION.get());
			plugin.sendMessage(sender, Lang.EXP_ADD_EXAMPLE.get().replace("{label}", label));
			return;
		}

		Player player = (Player) sender;
		Integer changeAmount = 0;

		boolean isChangeLevel = args[1].endsWith("L");
		if (isChangeLevel) {
			int endIndex = args[1].lastIndexOf('L');
			int changeLevelAmount = Integer.valueOf(args[1].substring(0, endIndex));
			changeAmount = ExpManager.getExpLevelToLevel(player, changeLevelAmount);
		} else {
			try {
				changeAmount = Integer.valueOf(args[1]);
			} catch (NumberFormatException e) {
				plugin.sendMessage(sender, Lang.ERROR_NOT_VALID_NUMBER.get());
				return;
			}
		}

		Integer oldLevel = ExpManager.getLevel(player);
		Integer oldExp = ExpManager.getTotalExp(player);

		ExpManager.addExp(player, changeAmount);

		Integer newLevel = ExpManager.getLevel(player);
		Integer newExp = ExpManager.getTotalExp(player);

		String msg = Lang.EXP_ADD.get()
				.replace("{amount}", changeAmount.toString());
		String msg2 = Lang.EXP_CHANGE.get()
				.replace("{oldExp}", oldExp.toString())
				.replace("{oldLevel}", oldLevel.toString())
				.replace("{newExp}", newExp.toString())
				.replace("{newLevel}", newLevel.toString());

		plugin.sendMessage(sender, msg);
		plugin.sendMessage(sender, msg2);
	}
}
