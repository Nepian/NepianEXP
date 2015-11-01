package com.nepian.nepianexp.commands;

import static com.nepian.breeze.utils.PlayerUtil.*;
import static com.nepian.breeze.utils.XpUtil.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Lang;

public class ExpSend extends ExpCommand {

	public ExpSend() {
		super(CommandData.SEND);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(3, sender, label, args)) return false;

		NepianEXP plugin = NepianEXP.getInstance();
		Player player = plugin.getPlayerMan().getPlayer(args[2]);

		if (player == null) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ONLINE_PLAYER.get()
					.replace("{player}", args[2]));
			return false;
		}

		Player pSender = (Player) sender;
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

		if (exp > getExp(pSender)) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ENOUGH_EXP.get());
			return true;
		}

		Integer senderOldExp = getExp(pSender);
		Integer senderOldXp = getXp(pSender);

		addExp(pSender, -exp);

		Integer playerOldExp = getExp(player);
		Integer playerOldXp = getXp(player);

		Integer senderNewExp = getExp(pSender);
		Integer senderNewXp = getXp(pSender);

		addExp(player, exp);

		Integer playerNewExp = getExp(player);
		Integer playerNewXp = getXp(player);

		plugin.sendMessage(pSender, Lang.EXP_SEND_SENDER.get()
				.replace("{player}", player.getName())
				.replace("{amount}", exp.toString()));
		plugin.sendMessage(pSender, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", senderOldExp.toString())
				.replace("{oldLevel}", senderOldXp.toString())
				.replace("{newExp}", senderNewExp.toString())
				.replace("{newLevel}", senderNewXp.toString()));
		plugin.sendMessage(player, Lang.EXP_SEND_TARGET.get()
				.replace("{player}", sender.getName())
				.replace("{amount}", exp.toString()));
		plugin.sendMessage(player, Lang.EXP_CHANGE.get()
				.replace("{oldExp}", playerOldExp.toString())
				.replace("{oldLevel}", playerOldXp.toString())
				.replace("{newExp}", playerNewExp.toString())
				.replace("{newLevel}", playerNewXp.toString()));

		return true;
	}

}
