package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.commands.player.ExpResetPlayer;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpReset extends ExpCommand {

	public ExpReset() {
		super(CommandData.RESET);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		if (args.length > 1) {
			new ExpResetPlayer().useCommand(sender, label, args);
			return true;
		}

		Player player = (Player) sender;

		Integer playerOldExp = ExpManager.getTotalExp(player);
		Integer playerOldLevel = ExpManager.getLevel(player);

		ExpManager.resetExp(player);

		Integer playerNewExp = ExpManager.getTotalExp(player);
		Integer playerNewLevel = ExpManager.getLevel(player);

		Main.getInstance().sendMessage(player, Lang.EXP_RESET_SENDER_MESSAGE.get()
				.replace("{target}", player.getName()));
		Main.getInstance().sendMessage(player, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", playerOldExp.toString())
				.replace("{oldLevel}", playerOldLevel.toString())
				.replace("{newExp}", playerNewExp.toString())
				.replace("{newLevel}", playerNewLevel.toString()));

		return true;
	}
}
