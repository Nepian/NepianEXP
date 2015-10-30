package nepian.bukkit.plugin.exp.commands.player;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpSetPlayer extends ExpCommand {
	private static final String name = "";
	private static final String usage = "set { <exp> or <level>L } <player> [player]...";
	private static final String permission = "nepian.exp.set.player";
	private static final String description = "指定プレイヤーの経験値を設定する";

	private final Main plugin;

	public ExpSetPlayer(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;

		for (int i = 2; i < args.length; i++) {
			Player player = plugin.getPlayerMan().getPlayer(args[i]);
			if (player == null) {
				plugin.sendMessage(sender, Lang.ERROR_NOT_ONLINE_PLAYER.get()
						.replace("{player}", args[i]));
				continue;
			}

			Integer amount = 0;
			try {
				if (args[1].endsWith("L")) {
					int endIndex = args[1].lastIndexOf('L');
					int levelAmount = Integer.valueOf(args[1].substring(0, endIndex));
					amount = ExpManager.convertLevelToExp(levelAmount);
				} else {
					amount = Integer.valueOf(args[1]);
				}
			} catch (Exception e) {
				plugin.sendMessage(sender, Lang.ERROR_NOT_VALID_NUMBER.get());
				return true;
			}

			ExpManager.resetExp(player);
			ExpManager.addExp(player, amount);

			plugin.sendMessage(sender, Lang.EXP_SET_SENDER.get()
					.replace("{player}", player.getName())
					.replace("{amount}", amount.toString()));
			plugin.sendMessage(player, Lang.EXP_SET_TARGET.get()
					.replace("{player}", sender.getName())
					.replace("{amount}", amount.toString()));
		}

		return true;
	}

}
