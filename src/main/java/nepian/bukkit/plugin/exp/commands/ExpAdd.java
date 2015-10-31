package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.commands.player.ExpAddPlayer;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpAdd extends ExpCommand {

	public ExpAdd() {
		super(CommandData.ADD);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		if (args.length > 2) {
			new ExpAddPlayer().useCommand(sender, label, args);
			return false;
		}

		Main plugin = Main.getInstance();
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
				return false;
			}
		}

		Integer playerOldExp = ExpManager.getTotalExp(player);
		Integer playerOldLevel = ExpManager.getLevel(player);

		ExpManager.addExp(player, changeAmount);

		Integer playerNewExp = ExpManager.getTotalExp(player);
		Integer playerNewLevel = ExpManager.getLevel(player);

		plugin.sendMessage(sender, Lang.EXP_ADD.get()
				.replace("{player}", player.getName())
				.replace("{amount}", changeAmount.toString()));
		plugin.sendMessage(player, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", playerOldExp.toString())
				.replace("{oldLevel}", playerOldLevel.toString())
				.replace("{newExp}", playerNewExp.toString())
				.replace("{newLevel}", playerNewLevel.toString()));

		return true;
	}

}
