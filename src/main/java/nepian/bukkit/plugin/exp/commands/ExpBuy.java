package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpBuy extends ExpCommand {
	private static final String name = "buy";
	private static final String usage = "buy { <exp> or <level>L }";
	private static final String permission = "nepian.exp.buy";
	private static final String description = "åoå±ílÇçwì¸Ç∑ÇÈ";

	private final Main plugin;

	public ExpBuy(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;
		if (!plugin.getExp().checkEqualArgsLength(2, args, sender, label, usage)) return true;

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
				return true;
			}
		}

		if (buyAmount <= 0) {
			plugin.sendMessage(sender, Lang.ERROR_NUMBER_NOT_POSITIVE.get());
			return true;
		}

		int money = (int) Math.floor(buyAmount * Configs.EXP_BUY_RATE.getDouble());

		if (money > plugin.getEconomy().getBalance(player)) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ENOUGH_MONEY.get());
			return true;
		}

		EconomyResponse response = plugin.getEconomy().withdrawPlayer(player, money);
		if (!response.transactionSuccess()) {
			String msg = Lang.ERROR_ECONOMY_FAULT.get()
					.replace("{error}", response.errorMessage);
			plugin.sendMessage(sender, msg);
			return true;
		}

		Integer oldLevel = ExpManager.getLevel(player);
		Integer oldExp = ExpManager.getTotalExp(player);

		ExpManager.addExp(player, buyAmount);

		Integer newLevel = ExpManager.getLevel(player);
		Integer newExp = ExpManager.getTotalExp(player);

		plugin.sendMessage(sender, Lang.EXP_BUY.get()
				.replace("{amount}", buyAmount.toString()));
		plugin.sendMessage(sender, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", oldExp.toString())
				.replace("{oldLevel}", oldLevel.toString())
				.replace("{newExp}", newExp.toString())
				.replace("{newLevel}", newLevel.toString()));

		return true;
	}
}
