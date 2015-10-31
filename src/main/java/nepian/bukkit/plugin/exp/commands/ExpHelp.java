package nepian.bukkit.plugin.exp.commands;

import java.util.ArrayList;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;

public class ExpHelp extends ExpCommand {

	public ExpHelp() {
		super(CommandData.HELP);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(1, sender, label, args)) return false;

		ArrayList<ExpCommand> commands = Main.getInstance().getExp().getCommands();
		for (ExpCommand command : commands) {
			if (sender.hasPermission(command.getPermission())) {
				Main.getInstance().sendMessage(sender, Lang.EXP_HELP.get()
						.replace("{label}", label)
						.replace("{usage}", command.getUsage())
						.replace("{description}", command.getDescription()));
			}
		}
		return true;
	}
}
