package nepian.bukkit.plugin.exp.commands.player;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpResetPlayer extends ExpCommand {

	public ExpResetPlayer() {
		super(CommandData.RESET_PLAYER);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		Main plugin = Main.getInstance();

		for (int i = 1; i < args.length; i++) {
			Player player = plugin.getPlayerMan().getPlayer(args[i]);
			if (player == null) {
				plugin.sendMessage(sender, Lang.ERROR_NOT_ONLINE_PLAYER.get()
						.replace("{player}", args[i]));
				continue;
			}

			Integer playerOldExp = ExpManager.getTotalExp(player);
			Integer playerOldLevel = ExpManager.getLevel(player);

			ExpManager.resetExp(player);

			Integer playerNewExp = ExpManager.getTotalExp(player);
			Integer playerNewLevel = ExpManager.getLevel(player);

			plugin.sendMessage(sender, Lang.EXP_RESET_SENDER_MESSAGE.get()
					.replace("{target}", player.getName()));
			plugin.sendMessage(player, Lang.EXP_RESET_TARGET_MESSAGE.get()
					.replace("{sender}", sender.getName()));
			plugin.sendMessage(player, Lang.EXP_CHANGE.get()
					.replace("{oldExp}", playerOldExp.toString())
					.replace("{oldLevel}", playerOldLevel.toString())
					.replace("{newExp}", playerNewExp.toString())
					.replace("{newLevel}", playerNewLevel.toString()));
		}

		return true;
	}
}
