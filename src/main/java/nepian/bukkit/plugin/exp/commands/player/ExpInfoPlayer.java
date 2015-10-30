package nepian.bukkit.plugin.exp.commands.player;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpInfoPlayer extends ExpCommand {
	private static final String name = "";
	private static final String usage = "info <player> [player]...";
	private static final String permission = "nepian.exp.info.player";
	private static final String description = "指定プレイヤーの経験値を表示";

	private final Main plugin;

	public ExpInfoPlayer(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;

		for (int i = 1; i < args.length; i++) {
			Player player = plugin.getPlayerMan().getPlayer(args[i]);
			if (player == null) {
				plugin.sendMessage(sender, Lang.ERROR_NOT_ONLINE_PLAYER.get()
						.replace("{player}", args[i]));
				continue;
			}

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
		}

		return true;
	}

}
