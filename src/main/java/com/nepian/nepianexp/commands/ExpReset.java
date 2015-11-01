package com.nepian.nepianexp.commands;

import static com.nepian.breeze.utils.PlayerUtil.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.commands.player.ExpResetPlayer;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Lang;

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

		if (!super.checkEqualLength(1, sender, label, args)) return false;

		Player player = (Player) sender;

		Integer playerOldExp = getExp(player);
		Integer playerOldXp = getXp(player);

		clearExp(player);

		Integer playerNewExp = getExp(player);
		Integer playerNewXp = getXp(player);

		NepianEXP.getInstance().sendMessage(player, Lang.EXP_RESET_SENDER_MESSAGE.get()
				.replace("{target}", player.getName()));
		NepianEXP.getInstance().sendMessage(player, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", playerOldExp.toString())
				.replace("{oldLevel}", playerOldXp.toString())
				.replace("{newExp}", playerNewExp.toString())
				.replace("{newLevel}", playerNewXp.toString()));

		return true;
	}
}
