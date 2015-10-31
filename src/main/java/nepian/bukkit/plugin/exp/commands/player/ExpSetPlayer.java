package nepian.bukkit.plugin.exp.commands.player;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpSetPlayer extends ExpCommand {

	public ExpSetPlayer(Main instance) {
		super(CommandData.SET_PLAYER);
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

			Integer playerOldLevel = ExpManager.getLevel(player);
			Integer playerOldExp = ExpManager.getTotalExp(player);

			ExpManager.resetExp(player);
			ExpManager.addExp(player, amount);

			Integer playerNewLevel = ExpManager.getLevel(player);
			Integer playerNewExp = ExpManager.getTotalExp(player);

			plugin.sendMessage(sender, Lang.EXP_SET_SENDER.get()
					.replace("{player}", player.getName())
					.replace("{amount}", amount.toString()));
			plugin.sendMessage(player, Lang.EXP_SET_TARGET.get()
					.replace("{player}", sender.getName())
					.replace("{amount}", amount.toString()));
			plugin.sendMessage(player, Lang.EXP_CHANGE.get()
					.replace("{oldExp}", playerOldExp.toString())
					.replace("{oldLevel}", playerOldLevel.toString())
					.replace("{newExp}", playerNewExp.toString())
					.replace("{newLevel}", playerNewLevel.toString()));
		}

		return true;
	}

}
