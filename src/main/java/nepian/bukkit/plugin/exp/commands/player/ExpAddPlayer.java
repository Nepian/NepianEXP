package nepian.bukkit.plugin.exp.commands.player;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpAddPlayer extends ExpCommand {

	public ExpAddPlayer() {
		super(CommandData.ADD_PLAYER);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		Main plugin = Main.getInstance();

		for (int i = 2; i < args.length; i++) {
			Player player = plugin.getPlayerMan().getPlayer(args[i]);
			if (player == null) {
				plugin.sendMessage(sender, Lang.ERROR_NOT_ONLINE_PLAYER.get()
						.replace("{player}", args[i]));
				continue;
			}

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
					return true;
				}
			}

			Integer playerOldExp = ExpManager.getTotalExp(player);
			Integer playerOldLevel = ExpManager.getLevel(player);

			ExpManager.addExp(player, changeAmount);

			Integer playerNewExp = ExpManager.getTotalExp(player);
			Integer playerNewLevel = ExpManager.getLevel(player);

			String changeMsg = Lang.EXP_CHANGE.get()
					.replace("{oldExp}", playerOldExp.toString())
					.replace("{oldLevel}", playerOldLevel.toString())
					.replace("{newExp}", playerNewExp.toString())
					.replace("{newLevel}", playerNewLevel.toString());

			plugin.sendMessage(sender, Lang.EXP_ADD.get()
					.replace("{player}", player.getName())
					.replace("{amount}", changeAmount.toString()));
			plugin.sendMessage(sender, changeMsg);
			plugin.sendMessage(player, Lang.EXP_ADD_TARGET.get()
					.replace("{player}", sender.getName())
					.replace("{amount}", changeAmount.toString()));
			plugin.sendMessage(player, changeMsg);
		}

		return true;
	}

}
