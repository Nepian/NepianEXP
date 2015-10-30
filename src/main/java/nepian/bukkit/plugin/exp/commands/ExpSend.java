package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpSend extends ExpCommand {
	private static final String name = "send";
	private static final String usage = "send { <exp> or <level>L } <player>";
	private static final String permission = "nepian.exp.send";
	private static final String description = "指定プレイヤーに経験値を送信する";

	private final Main plugin;

	public ExpSend(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;
		if (!plugin.getExp().checkEqualArgsLength(3, args, sender, label, usage)) return true;

		Player player = plugin.getPlayerMan().getPlayer(args[2]);
		if (player == null) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ONLINE_PLAYER.get()
					.replace("{player}", args[2]));
			return true;
		}

		Integer amount = 0;
		try {
			if (args[1].endsWith("L")) {
				int endIndex = args[1].lastIndexOf('L');
				int levelAmount = Integer.valueOf(args[1].substring(0, endIndex));
				amount = -1 * ExpManager.getExpLevelToLevel((Player) sender, -levelAmount);
			} else {
				amount = Integer.valueOf(args[1]);
			}
		} catch (Exception e) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_VALID_NUMBER.get());
			return true;
		}

		if (amount <= 0) {
			plugin.sendMessage(sender, Lang.ERROR_NUMBER_NOT_POSITIVE.get());
			return true;
		}

		if (amount > ExpManager.getTotalExp((Player) sender)) {
			plugin.sendMessage(sender, Lang.ERROR_NOT_ENOUGH_EXP.get());
			return true;
		}

		ExpManager.addExp(player, amount);
		ExpManager.addExp((Player) sender, -amount);

		plugin.sendMessage(sender, Lang.EXP_SEND_SENDER.get()
				.replace("{player}", player.getName())
				.replace("{amount}", amount.toString()));
		plugin.sendMessage(player, Lang.EXP_SEND_TARGET.get()
				.replace("{player}", sender.getName())
				.replace("{amount}", amount.toString()));

		return true;
	}

}
