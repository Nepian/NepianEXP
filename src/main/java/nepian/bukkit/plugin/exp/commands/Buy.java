package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Permissions;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Buy {
	private static final Main plugin = Main.getInstance();

	public static void useCommand(CommandSender sender, String label, String[] args) {
		if (!Permissions.BUY.hasPermission(sender, label, args)) return;

		if (args.length < 2) {
			plugin.sendMessage(sender, Lang.EXP_BUY_DESCRIPTION.get());
			plugin.sendMessage(sender, Lang.EXP_BUY_EXAMPLE.get().replace("{label}", label));
			return;
		}

		Player player = (Player) sender;
		Integer buyAmount = 0;

		boolean isBuyLevel = args[1].endsWith("L");
		if (isBuyLevel) {
			int endIndex = args[1].lastIndexOf('L');
			int buyLevelAmount = Integer.valueOf(args[1].substring(0, endIndex));
			buyAmount = ExpManager.getExpLevelToLevel(player, buyLevelAmount);
		} else {
			try {
				buyAmount = Integer.valueOf(args[1]);
			} catch (NumberFormatException e) {
				plugin.sendMessage(sender, Lang.ERROR_NOT_VALID_NUMBER.get());
				return;
			}
		}

		if (buyAmount <= 0) {
			plugin.sendMessage(sender, Lang.ERROR_NUMBER_NOT_POSITIVE.get());
			return;
		}

		int money = (int) Math.floor(buyAmount * Configs.EXP_BUY_RATE.getDouble());

		if (money > plugin.getEconomy().getBalance(player)) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ENOUGH_MONEY.get());
			return;
		}

		EconomyResponse response = plugin.getEconomy().withdrawPlayer(player, money);
		if (!response.transactionSuccess()) {
			String msg = Lang.ERROR_ECONOMY_FAULT.get()
					.replace("{error}", response.errorMessage);
			plugin.sendMessage(sender, msg);
			return;
		}

		Integer oldLevel = ExpManager.getLevel(player);
		Integer oldExp = ExpManager.getTotalExp(player);

		ExpManager.addExp(player, buyAmount);

		Integer newLevel = ExpManager.getLevel(player);
		Integer newExp = ExpManager.getTotalExp(player);

		String msg = Lang.EXP_BUY.get()
				.replace("{amount}", buyAmount.toString());
		String msg2 = Lang.EXP_CHANGE.get()
				.replace("{oldExp}", oldExp.toString())
				.replace("{oldLevel}", oldLevel.toString())
				.replace("{newExp}", newExp.toString())
				.replace("{newLevel}", newLevel.toString());

		plugin.sendMessage(sender, msg);
		plugin.sendMessage(sender, msg2);
	}
}
