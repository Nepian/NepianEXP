package nepian.bukkit.plugin.exp.commands.player;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpResetPlayer extends ExpCommand {
	private static final String name = "";
	private static final String usage = "reset <player> [player]...";
	private static final String permission = "nepian.exp.reset.player";
	private static final String description = "指定プレイヤーの経験値をリセット";

	private final Main plugin;

	public ExpResetPlayer(Main instance) {
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

			ExpManager.resetExp(player);

			plugin.sendMessage(sender, Lang.EXP_RESET_SENDER_MESSAGE.get()
					.replace("{target}", player.getName()));
			plugin.sendMessage(player, Lang.EXP_RESET_TARGET_MESSAGE.get()
					.replace("{sender}", sender.getName()));
		}

		return true;
	}
}
