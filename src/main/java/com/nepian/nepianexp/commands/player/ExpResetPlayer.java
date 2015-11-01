package com.nepian.nepianexp.commands.player;

import static com.nepian.breeze.utils.PlayerUtil.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Lang;

public class ExpResetPlayer extends ExpCommand {

	public ExpResetPlayer() {
		super(CommandData.RESET_PLAYER);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		NepianEXP plugin = NepianEXP.getInstance();

		for (int i = 1; i < args.length; i++) {
			Player player = plugin.getPlayerMan().getPlayer(args[i]);
			if (player == null) {
				plugin.sendMessage(sender, Lang.ERROR_NOT_ONLINE_PLAYER.get()
						.replace("{player}", args[i]));
				continue;
			}

			Integer playerOldExp = getExp(player);
			Integer playerOldXp = getXp(player);

			clearExp(player);

			Integer playerNewExp = getExp(player);
			Integer playerNewXp = getXp(player);

			plugin.sendMessage(sender, Lang.EXP_RESET_SENDER_MESSAGE.get()
					.replace("{target}", player.getName()));
			plugin.sendMessage(player, Lang.EXP_RESET_TARGET_MESSAGE.get()
					.replace("{sender}", sender.getName()));
			plugin.sendMessage(player, Lang.EXP_CHANGE.get()
					.replace("{oldExp}", playerOldExp.toString())
					.replace("{oldLevel}", playerOldXp.toString())
					.replace("{newExp}", playerNewExp.toString())
					.replace("{newLevel}", playerNewXp.toString()));
		}

		return true;
	}
}
