package com.nepian.nepianexp.commands;

import static com.nepian.breeze.utils.PlayerUtil.*;
import static com.nepian.breeze.utils.XpUtil.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.commands.player.ExpSetPlayer;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Lang;

public class ExpSet extends ExpCommand {

	public ExpSet() {
		super(CommandData.SET);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		NepianEXP plugin = NepianEXP.getInstance();

		if (args.length > 2) {
			new ExpSetPlayer(plugin).useCommand(sender, label, args);
			return false;
		}

		if (!super.checkEqualLength(2, sender, label, args)) return false;

		Player player = (Player) sender;
		String quantity = args[1];
		Integer exp = 0;

		if (isXp(quantity)) {
			exp = convertXpToExp(getXpFromString(quantity));
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

		Integer playerOldExp = getExp(player);
		Integer playerOldXp = getXp(player);

		setExp(player, exp);

		Integer playerNewExp = getExp(player);
		Integer playerNewXp = getXp(player);

		plugin.sendMessage(sender, Lang.EXP_SET_SENDER.get()
				.replace("{player}", player.getName())
				.replace("{amount}", exp.toString()));
		plugin.sendMessage(player, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", playerOldExp.toString())
				.replace("{oldLevel}", playerOldXp.toString())
				.replace("{newExp}", playerNewExp.toString())
				.replace("{newLevel}", playerNewXp.toString()));

		return true;
	}
}