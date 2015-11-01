package com.nepian.nepianexp.commands;

import org.bukkit.command.CommandSender;

import com.nepian.nepianexp.ExpCommand;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.configuration.CommandData;
import com.nepian.nepianexp.configuration.Configs;
import com.nepian.nepianexp.configuration.Lang;

public class ExpConfigSet extends ExpCommand {

	public ExpConfigSet() {
		super(CommandData.CONFIG_SET);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(4, sender, label, args)) return false;

		NepianEXP plugin = NepianEXP.getInstance();
		String key = args[2];
		String value = args[3];
		Configs conf = Configs.getEnum(key);

		if (conf == null) {
			plugin.sendMessage(sender, Lang.EXP_CONFIG_SET_NOT_KEY.get()
					.replace("{key}", key));
			return true;
		}

		String oldValue = conf.getString();

		conf.set(value);
		plugin.saveConfig();

		String newValue = conf.getString();

		plugin.sendMessage(sender, Lang.EXP_CONFIG_SET_CHANGE.get()
				.replace("{key}", key)
				.replace("{oldValue}", oldValue)
				.replace("{newValue}", newValue)
				);
		plugin.sendMessage(sender, Lang.EXP_CONFIG_SET_END.get());

		return true;
	}

}
