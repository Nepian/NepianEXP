package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Permissions;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sell {
	private static final Main plugin = Main.getInstance();

	public static void useCommand(CommandSender sender, String label, String[] args) {
		if (!Permissions.SELL.hasPermission(sender, label, args)) return;

		if (args.length < 2) {
			plugin.sendMessage(sender, Lang.EXP_SELL_DESCRIPTION.get());
			plugin.sendMessage(sender, Lang.EXP_SELL_EXAMPLE.get().replace("{label}", label));
			return;
		}

		Player player = (Player) sender;
		Integer sellAmount = 0;

		boolean isSellLevel = args[1].endsWith("L");
		if (isSellLevel) {
			int endIndex = args[1].lastIndexOf('L');
			int sellLevelAmount = Integer.valueOf(args[1].substring(0, endIndex));
			sellAmount = -1 * ExpManager.getExpLevelToLevel(player, -sellLevelAmount);
		} else {
			try {
				sellAmount = Integer.valueOf(args[1]);
			} catch (NumberFormatException e) {
				plugin.sendMessage(sender, Lang.ERROR_NOT_VALID_NUMBER.get());
				return;
			}
		}

		if (sellAmount <= 0) {
			plugin.sendMessage(sender, Lang.ERROR_NUMBER_NOT_POSITIVE.get());
			return;
		}

		if (sellAmount > ExpManager.getTotalExp(player)) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ENOUGH_EXP.get());
			return;
		}

		int money = (int) Math.floor(sellAmount * Configs.EXP_SELL_RATE.getDouble());
		EconomyResponse response = plugin.getEconomy().depositPlayer(player, money);
		if (!response.transactionSuccess()) {
			String msg = Lang.ERROR_ECONOMY_FAULT.get()
					.replace("{error}", response.errorMessage);
			plugin.sendMessage(sender, msg);
			return;
		}

		Integer oldLevel = ExpManager.getLevel(player);
		Integer oldExp = ExpManager.getTotalExp(player);

		ExpManager.addExp(player, -sellAmount);

		Integer newLevel = ExpManager.getLevel(player);
		Integer newExp = ExpManager.getTotalExp(player);

		String msg = Lang.EXP_SELL.get()
				.replace("{amount}", sellAmount.toString());
		String msg2 = Lang.EXP_CHANGE.get()
				.replace("{oldExp}", oldExp.toString())
				.replace("{oldLevel}", oldLevel.toString())
				.replace("{newExp}", newExp.toString())
				.replace("{newLevel}", newLevel.toString());

		plugin.sendMessage(sender, msg);
		plugin.sendMessage(sender, msg2);
	}
}
