package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.commands.player.ExpInfoPlayer;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpInfo extends ExpCommand {
	private static final String name = "info";
	private static final String usage = "info [player]...";
	private static final String permission = "nepian.exp.info";
	private static final String description = "ŒoŒ±’l‚Ìî•ñ‚ð•\Ž¦‚·‚é";

	private final Main plugin;

	public ExpInfo(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;

		if (args.length > 1) {
			new ExpInfoPlayer(plugin).useCommand(sender, label, args);
			return true;
		}

		Player player = (Player) sender;
		int totalExp = ExpManager.getTotalExp(player);
		int level = ExpManager.getLevel(player);
		int next = ExpManager.getRestExpNeededToLevelUp(player);

		plugin.sendMessage(sender, Lang.EXP_INFO.get()
				.replace("{player}", player.getName()));
		plugin.sendMessage(sender, Lang.EXP_INFO_TOTALEXP.get()
				.replace("{totalExp}", String.valueOf(totalExp))
				.replace("{level}", String.valueOf(level)));
		plugin.sendMessage(sender, Lang.EXP_INFO_NEXT.get()
				.replace("{next}", String.valueOf(next)));

		return true;
	}

}
