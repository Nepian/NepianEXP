package com.nepian.nepianexp.commands.player;

import static com.nepian.breeze.utils.PlayerUtil.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Lang;

public class ExpInfoPlayer extends ExpCommand {

	public ExpInfoPlayer() {
		super(CommandData.INFO_PLAYER);
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

			Integer exp = getExp(player);
			Integer xp = getXp(player);
			Integer next = getRestExpNeededToLevelUp(player);

			plugin.sendMessage(sender, Lang.EXP_INFO.get()
					.replace("{player}", player.getName()));
			plugin.sendMessage(sender, Lang.EXP_INFO_TOTALEXP.get()
					.replace("{totalExp}", exp.toString())
					.replace("{level}", xp.toString()));
			plugin.sendMessage(sender, Lang.EXP_INFO_NEXT.get()
					.replace("{next}", next.toString()));
		}

		return true;
	}

}
