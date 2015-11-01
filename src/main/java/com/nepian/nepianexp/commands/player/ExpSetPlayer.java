package com.nepian.nepianexp.commands.player;

import static com.nepian.breeze.utils.PlayerUtil.*;
import static com.nepian.breeze.utils.XpUtil.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Lang;

public class ExpSetPlayer extends ExpCommand {

	public ExpSetPlayer(NepianEXP instance) {
		super(CommandData.SET_PLAYER);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		NepianEXP plugin = NepianEXP.getInstance();
		for (int i = 2; i < args.length; i++) {
			Player player = plugin.getPlayerMan().getPlayer(args[i]);
			if (player == null) {
				plugin.sendMessage(sender, Lang.ERROR_NOT_ONLINE_PLAYER.get()
						.replace("{player}", args[i]));
				continue;
			}

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

			Integer playerOldExp = getExp(player);
			Integer playerOldXp = getXp(player);

			setExp(player, exp);

			Integer playerNewExp = getExp(player);
			Integer playerNewXp = getXp(player);

			plugin.sendMessage(sender, Lang.EXP_SET_SENDER.get()
					.replace("{player}", player.getName())
					.replace("{amount}", exp.toString()));
			plugin.sendMessage(player, Lang.EXP_SET_TARGET.get()
					.replace("{player}", sender.getName())
					.replace("{amount}", exp.toString()));
			plugin.sendMessage(player, Lang.EXP_CHANGE.get()
					.replace("{oldExp}", playerOldExp.toString())
					.replace("{oldLevel}", playerOldXp.toString())
					.replace("{newExp}", playerNewExp.toString())
					.replace("{newLevel}", playerNewXp.toString()));
		}

		return true;
	}

}
