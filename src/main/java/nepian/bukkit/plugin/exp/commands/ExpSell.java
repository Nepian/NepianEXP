package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpSell extends ExpCommand {
	private static final String name = "sell";
	private static final String usage = "sell { <exp> or <level>L }";
	private static final String permission = "nepian.exp.sell";
	private static final String description = "ŒoŒ±’l‚ð”„‹p‚·‚é";

	private final Main plugin;

	public ExpSell(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;
		if (!plugin.getExp().checkEqualArgsLength(2, args, sender, label, usage)) return true;

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
				return true;
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

		Integer oldLevel = ExpManager.getLevel(player);
		Integer oldExp = ExpManager.getTotalExp(player);

		ExpManager.addExp(player, -sellAmount);

		Integer newLevel = ExpManager.getLevel(player);
		Integer newExp = ExpManager.getTotalExp(player);

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
