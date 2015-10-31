package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.commands.player.ExpSetPlayer;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpSet extends ExpCommand {

	public ExpSet() {
		super(CommandData.SET);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		Main plugin = Main.getInstance();

		if (args.length > 2) {
			new ExpSetPlayer(plugin).useCommand(sender, label, args);
			return false;
		}

		if (!super.checkEqualLength(2, sender, label, args)) return false;

		Player player = (Player) sender;

		Integer amount = 0;
		try {
			if (args[1].endsWith("L")) {
				int endIndex = args[1].lastIndexOf('L');
				int levelAmount = Integer.valueOf(args[1].substring(0, endIndex));
				amount = ExpManager.convertLevelToExp(levelAmount);
			} else {
				amount = Integer.valueOf(args[1]);
			}
		} catch (Exception e) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_VALID_NUMBER.get());
			return false;
		}

		if (amount <= 0) {
			plugin.sendMessage(sender, Lang.ERROR_NUMBER_NOT_POSITIVE.get());
			return true;
		}

		Integer playerOldExp = ExpManager.getTotalExp(player);
		Integer playerOldLevel = ExpManager.getLevel(player);

		ExpManager.resetExp(player);
		ExpManager.addExp(player, amount);

		Integer playerNewExp = ExpManager.getTotalExp(player);
		Integer playerNewLevel = ExpManager.getLevel(player);

		plugin.sendMessage(sender, Lang.EXP_SET_SENDER.get()
				.replace("{player}", player.getName())
				.replace("{amount}", amount.toString()));
		plugin.sendMessage(player, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", playerOldExp.toString())
				.replace("{oldLevel}", playerOldLevel.toString())
				.replace("{newExp}", playerNewExp.toString())
				.replace("{newLevel}", playerNewLevel.toString()));

		return true;
	}
}