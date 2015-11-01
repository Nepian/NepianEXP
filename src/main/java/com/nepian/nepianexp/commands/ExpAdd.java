package com.nepian.nepianexp.commands;

import static com.nepian.breeze.utils.PlayerUtil.*;
import static com.nepian.breeze.utils.XpUtil.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.commands.player.ExpAddPlayer;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Lang;

public class ExpAdd extends ExpCommand {

	public ExpAdd() {
		super(CommandData.ADD);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		if (args.length > 2) {
			new ExpAddPlayer().useCommand(sender, label, args);
			return false;
		}

		if (!super.checkEqualLength(2, sender, label, args)) return false;

		NepianEXP plugin = NepianEXP.getInstance();
		Player player = (Player) sender;
		String quantity = args[1];
		Integer exp = 0;

		if (isXp(quantity)) {
			exp = getExpXpToXp(player, getXpFromString(quantity));
		} else if (isExp(quantity)) {
			exp = Integer.parseInt(quantity);
		} else {
			plugin.sendMessage(sender, Lang.ERROR_NOT_VALID_NUMBER.get());
			return false;
		}

		Integer playerOldExp = getExp(player);
		Integer playerOldXp = getXp(player);

		addExp(player, exp);

		Integer playerNewExp = getExp(player);
		Integer playerNewXp = getXp(player);

		plugin.sendMessage(sender, Lang.EXP_ADD.get()
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
