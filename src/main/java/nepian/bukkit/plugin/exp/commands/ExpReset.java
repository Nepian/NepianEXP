package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.commands.player.ExpResetPlayer;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpReset extends ExpCommand {
	private static final String name = "reset";
	private static final String usage = "reset [player]...";
	private static final String permission = "nepian.exp.reset";
	private static final String description = "経験値をリセットする";

	private final Main plugin;

	public ExpReset(Main instance) {
		super(name, usage, permission, description);
		plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;

		if (args.length > 1) {
			new ExpResetPlayer(plugin).useCommand(sender, label, args);
			return true;
		}

		Player player = (Player) sender;

		ExpManager.resetExp(player);

		plugin.sendMessage(sender, Lang.EXP_RESET_SENDER_MESSAGE.get()
				.replace("{target}", player.getName()));

		return true;
	}
}
