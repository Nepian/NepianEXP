package nepian.bukkit.plugin.exp.commands;

import java.util.ArrayList;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;

public class ExpHelp extends ExpCommand {
	private static final String name = "help";
	private static final String usage = "help";
	private static final String permission = "nepian.exp.help";
	private static final String description = "使用可能なコマンドの一覧を表示";

	private final Main plugin;
	private ArrayList<ExpCommand> commands;

	public ExpHelp(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;
		if (!plugin.getExp().checkEqualArgsLength(1, args, sender, label, usage)) return true;

		commands = plugin.getExp().getCommands();
		for (ExpCommand command : commands) {
			if (sender.hasPermission(command.getPermission())) {
				plugin.sendMessage(sender, Lang.EXP_HELP.get()
						.replace("{label}", label)
						.replace("{usage}", command.getUsage())
						.replace("{description}", command.getDescription()));
			}
		}
		return true;
	}
}
