package com.nepian.nepianexp;

import org.bukkit.command.CommandSender;

import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Lang;

public abstract class ExpCommand {
	private final CommandData commandData;

	public ExpCommand(CommandData commandData) {
		this.commandData = commandData;
	}

	public abstract boolean useCommand(CommandSender sender, String label, String[] args);

	public String getUsage() {
		return commandData.getUsage();
	}

	public String getPermission() {
		return commandData.getPermission();
	}

	public String getName() {
		return commandData.getName();
	}

	public String getDescription() {
		return commandData.getDescription();
	}

	public boolean checkPermission(CommandSender sender, String label) {
		if (sender.hasPermission(getPermission())) return true;
		NepianEXP.getInstance().sendMessage(sender, Lang.ERROR_COMMAND_NO_PERMISSION.get()
				.replace("{label}", label)
				.replace("{usage}", getUsage()));
		return false;
	}

	public boolean checkEqualLength(int length, CommandSender sender, String label, String[] args) {
		if (args.length == length) return true;
		NepianEXP.getInstance().sendMessage(sender, Lang.ERROR_COMMAND.get());
		NepianEXP.getInstance().sendMessage(sender, Lang.ERROR_COMMAND_USAGE.get()
				.replace("{label}", label)
				.replace("{usage}", getUsage()));
		return false;
	}
}
