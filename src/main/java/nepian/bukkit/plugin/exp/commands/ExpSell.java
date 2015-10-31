package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpSell extends ExpCommand {

	public ExpSell() {
		super(CommandData.SELL);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(2, sender, label, args)) return false;

		Main plugin = Main.getInstance();
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
				return false;
			}
		}

		if (sellAmount <= 0) {
			plugin.sendMessage(sender, Lang.ERROR_NUMBER_NOT_POSITIVE.get());
			return true;
		}

		if (sellAmount > ExpManager.getTotalExp(player)) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ENOUGH_EXP.get());
			return true;
		}

		int money = (int) Math.floor(sellAmount * Configs.EXP_SELL_RATE.getDouble());
		EconomyResponse response = plugin.getEconomy().depositPlayer(player, money);
		if (!response.transactionSuccess()) {
			String msg = Lang.ERROR_ECONOMY_FAULT.get()
					.replace("{error}", response.errorMessage);
			plugin.sendMessage(sender, msg);
			return true;
		}

		Integer oldExp = ExpManager.getTotalExp(player);
		Integer oldLevel = ExpManager.getLevel(player);

		ExpManager.addExp(player, -sellAmount);

		Integer newExp = ExpManager.getTotalExp(player);
		Integer newLevel = ExpManager.getLevel(player);

		plugin.sendMessage(sender, Lang.EXP_SELL.get()
				.replace("{amount}", sellAmount.toString()));
		plugin.sendMessage(sender, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", oldExp.toString())
				.replace("{oldLevel}", oldLevel.toString())
				.replace("{newExp}", newExp.toString())
				.replace("{newLevel}", newLevel.toString()));

		return true;
	}

}
