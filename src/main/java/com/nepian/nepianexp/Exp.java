package com.nepian.nepianexp;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.nepianexp.commands.ExpAdd;
import com.nepian.nepianexp.commands.ExpBuy;
import com.nepian.nepianexp.commands.ExpConfig;
import com.nepian.nepianexp.commands.ExpHelp;
import com.nepian.nepianexp.commands.ExpInfo;
import com.nepian.nepianexp.commands.ExpReload;
import com.nepian.nepianexp.commands.ExpReset;
import com.nepian.nepianexp.commands.ExpSell;
import com.nepian.nepianexp.commands.ExpSend;
import com.nepian.nepianexp.commands.ExpSet;
import com.nepian.nepianexp.configuration.Lang;

/**
 * EXP関連のコマンドを管理するコマンド
 * @author Nepian
 *
 */
public class Exp implements CommandExecutor {
	private final NepianEXP plugin;
	private ArrayList<ExpCommand> commands;

	public Exp(NepianEXP instance) {
		this.plugin = instance;
		this.commands = new ArrayList<ExpCommand>();
		this.commands.add(new ExpHelp());
		this.commands.add(new ExpInfo());
		this.commands.add(new ExpSend());
		this.commands.add(new ExpAdd());
		this.commands.add(new ExpSet());
		this.commands.add(new ExpReset());
		this.commands.add(new ExpReload());
		this.commands.add(new ExpConfig());
		if (plugin.getEconomy() != null) {
			this.commands.add(new ExpBuy());
			this.commands.add(new ExpSell());
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			plugin.sendMessage(sender, Lang.ERROR_PLAYER_COMMAND.get());
			return true;
		}

		if (!sender.hasPermission("nepian.exp")) {
			plugin.sendMessage(sender, Lang.ERROR_COMMAND_NO_PERMISSION.get()
					.replace("{label}", label)
					.replace("{usage}", ""));
			return true;
		}

		if (args.length >= 1) {
			String expCommand = args[0];
			for (ExpCommand command : commands) {
				if (expCommand.equalsIgnoreCase(command.getName())) {
					command.useCommand(sender, label, args);
					return true;
				}
			}
		}

		plugin.sendMessage(sender, Lang.EXP_VERSION.get()
				.replace("{name}", plugin.getName())
				.replace("{version}", plugin.getDescription().getVersion()));
		plugin.sendMessage(sender, Lang.EXP_MESSAGE.get());

		return true;
	}

	public ArrayList<ExpCommand> getCommands() {
		return commands;
	}

	public NepianEXP getPlugin() {
		return plugin;
	}
}
