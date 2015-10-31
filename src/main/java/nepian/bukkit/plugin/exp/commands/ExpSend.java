package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpSend extends ExpCommand {

	public ExpSend() {
		super(CommandData.SEND);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return true;
		if (!super.checkEqualLength(3, sender, label, args)) return false;

		Main plugin = Main.getInstance();
		Player player = plugin.getPlayerMan().getPlayer(args[2]);

		if (player == null) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ONLINE_PLAYER.get()
					.replace("{player}", args[2]));
			return true;
		}

		Player pSender = (Player) sender;
		Integer amount = 0;
		try {
			if (args[1].endsWith("L")) {
				int endIndex = args[1].lastIndexOf('L');
				int levelAmount = Integer.valueOf(args[1].substring(0, endIndex));
				amount = -1 * ExpManager.getExpLevelToLevel(pSender, -levelAmount);
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

		if (amount > ExpManager.getTotalExp(pSender)) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ENOUGH_EXP.get());
			return true;
		}

		Integer senderOldLevel = ExpManager.getLevel(pSender);
		Integer senderOldExp = ExpManager.getTotalExp(pSender);
		Integer playerOldLevel = ExpManager.getLevel(player);
		Integer playerOldExp = ExpManager.getTotalExp(player);

		ExpManager.addExp(player, amount);
		ExpManager.addExp(pSender, -amount);

		Integer senderNewLevel = ExpManager.getLevel(pSender);
		Integer senderNewExp = ExpManager.getTotalExp(pSender);
		Integer playerNewLevel = ExpManager.getLevel(player);
		Integer playerNewExp = ExpManager.getTotalExp(player);

		plugin.sendMessage(pSender, Lang.EXP_SEND_SENDER.get()
				.replace("{player}", player.getName())
				.replace("{amount}", amount.toString()));
		plugin.sendMessage(pSender, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", senderOldExp.toString())
				.replace("{oldLevel}", senderOldLevel.toString())
				.replace("{newExp}", senderNewExp.toString())
				.replace("{newLevel}", senderNewLevel.toString()));
		plugin.sendMessage(player, Lang.EXP_SEND_TARGET.get()
				.replace("{player}", sender.getName())
				.replace("{amount}", amount.toString()));
		plugin.sendMessage(player, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", playerOldExp.toString())
				.replace("{oldLevel}", playerOldLevel.toString())
				.replace("{newExp}", playerNewExp.toString())
				.replace("{newLevel}", playerNewLevel.toString()));

		return true;
	}

}
