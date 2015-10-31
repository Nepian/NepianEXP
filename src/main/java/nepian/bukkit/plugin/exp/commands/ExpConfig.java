package nepian.bukkit.plugin.exp.commands;

import java.util.ArrayList;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Configs;

import org.bukkit.command.CommandSender;

public class ExpConfig extends ExpCommand {
	private final ExpCommand[] commands = {
			new ExpConfigReload(),
			new ExpConfigSet()
	};

	public ExpConfig() {
		super(CommandData.CONFIG);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;

		if (args.length > 1) {
			for (ExpCommand cmd : commands) {
				if (args[1].equalsIgnoreCase(cmd.getName())) {
					cmd.useCommand(sender, label, args);
					return true;
				}
			}
		}

		if (!super.checkEqualLength(1, sender, label, args)) return false;

		showAllConfigData(sender);

		return true;
	}

	private void showAllConfigData(CommandSender sender) {
		ArrayList<String> params = Configs.getAllParam();
		for (String param : params) {
			Main.getInstance().sendMessage(sender, param);
		}
	}
}
