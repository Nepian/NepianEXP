package com.nepian.nepianexp.commands;

import static com.nepian.breeze.utils.PlayerUtil.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.commands.player.ExpInfoPlayer;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Lang;

public class ExpInfo extends ExpCommand {

	public ExpInfo() {
		super(CommandData.INFO);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		NepianEXP plugin = NepianEXP.getInstance();

		if (args.length > 1) {
			new ExpInfoPlayer().useCommand(sender, label, args);
			return true;
		}

		if (!super.checkEqualLength(1, sender, label, args)) return false;

		Player player = (Player) sender;
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

		return true;
	}

}
