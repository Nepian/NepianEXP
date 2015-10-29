package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Logger;

import org.bukkit.command.CommandSender;

public class ExpConfigReload extends ExpCommand {
	private static final String name = "reload";
	private static final String usage = "config reload";
	private static final String permission = "nepian.exp.config.reload";
	private static final String description = "config.ymlÇçƒì«Ç›çûÇ›";

	private final Main plugin;

	public ExpConfigReload(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}
	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;
		if (!plugin.getExp().checkEqualArgsLength(2, args, sender, label, usage)) return true;

		Configs.reload();
		plugin.sendMessage(sender, Lang.EXP_CONFIG_RELOAD_MESSAGE.get());
		Logger.log(Lang.EXP_CONFIG_RELOAD_LOG.get());

		return true;
	}

}
