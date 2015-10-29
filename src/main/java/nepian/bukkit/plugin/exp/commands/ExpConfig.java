package nepian.bukkit.plugin.exp.commands;

import java.util.ArrayList;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Configs;

import org.bukkit.command.CommandSender;

public class ExpConfig extends ExpCommand {
	private static final String name = "config";
	private static final String usage = "config (reload)";
	private static final String permission = "nepian.exp.config";
	private static final String description = "config.yml‚Ì“à—e‚ð•\Ž¦";

	private final Main plugin;

	public ExpConfig(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;

		if (args.length > 1) {
			new ExpConfigReload(plugin).useCommand(sender, label, args);
			return true;
		}

		showAllConfigData(sender);

		return true;
	}

	private void showAllConfigData(CommandSender sender) {
		ArrayList<String> params = Configs.getAllParam();
		for (String param : params) {
			plugin.sendMessage(sender, param);
		}
	}
}
