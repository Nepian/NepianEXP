package com.nepian.nepianexp.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Lang;

public class ExpHelp extends ExpCommand {

	public ExpHelp() {
		super(CommandData.HELP);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(1, sender, label, args)) return false;

		ArrayList<ExpCommand> commands = NepianEXP.getInstance().getExp().getCommands();
		for (ExpCommand command : commands) {
			if (sender.hasPermission(command.getPermission())) {
				NepianEXP.getInstance().sendMessage(sender, Lang.EXP_HELP.get()
						.replace("{label}", label)
						.replace("{usage}", command.getUsage())
						.replace("{description}", command.getDescription()));
			}
		}
		return true;
	}
}
