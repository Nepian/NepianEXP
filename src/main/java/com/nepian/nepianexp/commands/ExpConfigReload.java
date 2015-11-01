package com.nepian.nepianexp.commands;

import org.bukkit.command.CommandSender;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Configs;
import com.nepian.nepianexp.configuration.Lang;
import com.nepian.nepianexp.configuration.Logger;

public class ExpConfigReload extends ExpCommand {

	public ExpConfigReload() {
		super(CommandData.CONFIG_RELOAD);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(2, sender, label, args)) return false;

		Configs.reload();
		NepianEXP.getInstance().sendMessage(sender, Lang.EXP_CONFIG_RELOAD_MESSAGE.get());
		Logger.log(Lang.EXP_CONFIG_RELOAD_LOG.get());

		return true;
	}

}
