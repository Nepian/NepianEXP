package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.ExpManager;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.commands.player.ExpAddPlayer;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpAdd extends ExpCommand {
	private static final String name = "add";
	private static final String usage = "add { <exp> or <level>L } [player]...";
	private static final String permission = "nepian.exp.add";
	private static final String description = "ŒoŒ±’l‚ð’Ç‰Á‚·‚é";

	private final Main plugin;

	public ExpAdd(Main instance) {
		super(name, usage, permission, description);
		this.plugin = instance;
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!plugin.getExp().checkPermission(sender, permission, label, usage)) return true;

		if (args.length > 2) {
			new ExpAddPlayer(plugin).useCommand(sender, label, args);
			return true;
		}

		Player player = (Player) sender;
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

		return true;
	}

}
