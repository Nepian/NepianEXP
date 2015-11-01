package com.nepian.nepianexp.commands;

import org.bukkit.command.CommandSender;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Configs;
import com.nepian.nepianexp.configuration.Lang;
import com.nepian.nepianexp.configuration.Logger;

public class ExpReload extends ExpCommand {

	public ExpReload() {
		super(CommandData.RELOAD);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(1, sender, label, args)) return false;

		Configs.reload();
		Lang.reload();

		String msg = Lang.EXP_RELOAD.get()
				.replace("{name}", NepianEXP.getInstance().getName());
		NepianEXP.getInstance().sendMessage(sender, msg);
		Logger.log(msg);

		return true;
	}

}
