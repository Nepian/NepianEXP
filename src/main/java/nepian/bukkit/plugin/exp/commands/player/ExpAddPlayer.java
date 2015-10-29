package nepian.bukkit.plugin.exp.commands.player;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpAddPlayer extends ExpCommand {
	private static final String name = "";
	private static final String usage = "add { <exp> or <level>L } player [player]...";
	private static final String permission = "nepian.exp.add.player";
	private static final String description = "指定プレイヤーに経験値を追加する";

	private final Main plugin;

	public ExpAddPlayer(Main instance) {
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

			Integer changeAmount = 0;

			boolean isChangeLevel = args[1].endsWith("L");
			if (isChangeLevel) {
				int endIndex = args[1].lastIndexOf('L');
				int changeLevelAmount = Integer.valueOf(args[1].substring(0, endIndex));
				changeAmount = ExpManager.getExpLevelToLevel(player, changeLevelAmount);
			} else {
				try {
					changeAmount = Integer.valueOf(args[1]);
				} catch (NumberFormatException e) {
					plugin.sendMessage(sender, Lang.ERROR_NOT_VALID_NUMBER.get());
					return true;
				}
			}

			Integer oldLevel = ExpManager.getLevel(player);
			Integer oldExp = ExpManager.getTotalExp(player);

			ExpManager.addExp(player, changeAmount);

			Integer newLevel = ExpManager.getLevel(player);
			Integer newExp = ExpManager.getTotalExp(player);

			plugin.sendMessage(sender, Lang.EXP_ADD.get()
					.replace("{player}", player.getName())
					.replace("{amount}", changeAmount.toString()));
			plugin.sendMessage(sender, Lang.EXP_CHANGE.get()
					.replace("{oldExp}", oldExp.toString())
					.replace("{oldLevel}", oldLevel.toString())
					.replace("{newExp}", newExp.toString())
					.replace("{newLevel}", newLevel.toString()));
			plugin.sendMessage(player, Lang.EXP_ADD_TARGET.get()
					.replace("{player}", sender.getName())
					.replace("{amount}", changeAmount.toString()));
			plugin.sendMessage(player, Lang.EXP_CHANGE.get()
					.replace("{oldExp}", oldExp.toString())
					.replace("{oldLevel}", oldLevel.toString())
					.replace("{newExp}", newExp.toString())
					.replace("{newLevel}", newLevel.toString()));
		}

		return true;
	}

}
