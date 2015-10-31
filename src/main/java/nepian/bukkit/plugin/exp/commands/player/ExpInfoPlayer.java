package nepian.bukkit.plugin.exp.commands.player;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpInfoPlayer extends ExpCommand {

	public ExpInfoPlayer() {
		super(CommandData.INFO_PLAYER);
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

			int totalExp = ExpManager.getTotalExp(player);
			int level = ExpManager.getLevel(player);
			int next = ExpManager.getRestExpNeededToLevelUp(player);

			plugin.sendMessage(sender, Lang.EXP_INFO.get()
					.replace("{player}", player.getName()));
			plugin.sendMessage(sender, Lang.EXP_INFO_TOTALEXP.get()
					.replace("{totalExp}", String.valueOf(totalExp))
					.replace("{level}", String.valueOf(level)));
			plugin.sendMessage(sender, Lang.EXP_INFO_NEXT.get()
					.replace("{next}", String.valueOf(next)));
		}

		return true;
	}

}
