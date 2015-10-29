package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Logger;

import org.bukkit.command.CommandSender;

public class ExpReload extends ExpCommand {
	private static final String name = "reload";
	private static final String usage = "reload";
	private static final String permission = "nepian.exp.reload";
	private static final String description = "ƒvƒ‰ƒOƒCƒ“‚ğÄ“Ç‚İ‚İ";

	private final Main plugin;

	public ExpReload(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;
		if (!plugin.getExp().checkEqualArgsLength(1, args, sender, label, usage)) return true;

		Configs.reload();
		Lang.reload();

		String msg = Lang.EXP_RELOAD.get()
				.replace("{name}", plugin.getName());
		plugin.sendMessage(sender, msg);
		Logger.log(msg);

		return true;
	}

}
