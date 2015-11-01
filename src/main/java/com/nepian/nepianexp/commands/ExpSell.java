package com.nepian.nepianexp.commands;

import static com.nepian.breeze.utils.PlayerUtil.*;
import static com.nepian.breeze.utils.XpUtil.*;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Configs;
import com.nepian.nepianexp.configuration.Lang;

public class ExpSell extends ExpCommand {

	public ExpSell() {
		super(CommandData.SELL);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(2, sender, label, args)) return false;

		NepianEXP plugin = NepianEXP.getInstance();
		Player player = (Player) sender;
		String quantity = args[1];
		Integer exp = 0;

		if (isXp(quantity)) {
			exp = Math.abs(getExpXpToXp(player, getXpFromString(quantity) * -1));
		} else if (isExp(quantity)) {
			exp = Integer.parseInt(quantity);
		} else {
			plugin.sendMessage(sender, Lang.ERROR_NOT_VALID_NUMBER.get());
			return false;
		}

		if (exp <= 0) {
			plugin.sendMessage(sender, Lang.ERROR_NUMBER_NOT_POSITIVE.get());
			return true;
		}

		if (exp > getExp(player)) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ENOUGH_EXP.get());
			return true;
		}

		int money = (int) Math.floor(exp * Configs.EXP_SELL_RATE.getDouble());
		EconomyResponse response = plugin.getEconomy().depositPlayer(player, money);
		if (!response.transactionSuccess()) {
			String msg = Lang.ERROR_ECONOMY_FAULT.get()
					.replace("{error}", response.errorMessage);
			plugin.sendMessage(sender, msg);
			return true;
		}

		Integer playerOldExp = getExp(player);
		Integer playerOldXp = getXp(player);

		addExp(player, -exp);

		Integer playerNewExp = getExp(player);
		Integer playerNewXp = getXp(player);

		plugin.sendMessage(sender, Lang.EXP_SELL.get()
				.replace("{amount}", exp.toString()));
		plugin.sendMessage(sender, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", playerOldExp.toString())
				.replace("{oldLevel}", playerOldXp.toString())
				.replace("{newExp}", playerNewExp.toString())
				.replace("{newLevel}", playerNewXp.toString()));

		return true;
	}

}
