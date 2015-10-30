package nepian.bukkit.plugin.exp;

import java.util.ArrayList;

import nepian.bukkit.plugin.exp.commands.ExpAdd;
import nepian.bukkit.plugin.exp.commands.ExpBuy;
import nepian.bukkit.plugin.exp.commands.ExpConfig;
import nepian.bukkit.plugin.exp.commands.ExpHelp;
import nepian.bukkit.plugin.exp.commands.ExpInfo;
import nepian.bukkit.plugin.exp.commands.ExpReload;
import nepian.bukkit.plugin.exp.commands.ExpReset;
import nepian.bukkit.plugin.exp.commands.ExpSell;
import nepian.bukkit.plugin.exp.commands.ExpSend;
import nepian.bukkit.plugin.exp.commands.ExpSet;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * EXP関連のコマンドを管理するコマンド
 * @author Nepian
 *
 */
public class Exp implements CommandExecutor {
	private static final String name = "";
	private static final String usage = "";
	private static final String permission = "nepian.exp";
	private static final String description = "プラグインのメインコマンド";

	private final Main plugin;
	private ArrayList<ExpCommand> commands;

	public Exp(Main instance) {
		this.plugin = instance;
		this.commands = new ArrayList<ExpCommand>();
		this.commands.add(new ExpHelp(plugin));
		this.commands.add(new ExpInfo(plugin));
		this.commands.add(new ExpBuy(plugin));
		this.commands.add(new ExpSell(plugin));
		this.commands.add(new ExpSend(plugin));
		this.commands.add(new ExpAdd(plugin));
		this.commands.add(new ExpSet(plugin));
		this.commands.add(new ExpReset(plugin));
		this.commands.add(new ExpReload(plugin));
		this.commands.add(new ExpConfig(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!this.checkPlayer(sender)) return true;
		if (!this.checkPermission(sender, permission, label, usage)) return true;

		if (args.length >= 1) {
			String expCommand = args[0];
			for (ExpCommand eCommand : commands) {
				if (expCommand.equalsIgnoreCase(eCommand.getName())) {
					eCommand.useCommand(sender, label, args);
					return true;
				}
			}
		}

		plugin.sendMessage(sender, Lang.EXP_VERSION.get()
				.replace("{name}", plugin.getName())
				.replace("{version}", plugin.getDescription().getVersion()));
		plugin.sendMessage(sender, Lang.EXP_MESSAGE.get());

		return true;
	}

	public ArrayList<ExpCommand> getCommands() {
		return commands;
	}

	public boolean checkPermission(CommandSender player,
			String permission, String label, String usage) {
		if (player.hasPermission(permission)) return true;

		plugin.sendMessage(player, Lang.ERROR_COMMAND_NO_PERMISSION.get()
				.replace("{label}", label)
				.replace("{usage}", usage));

		return false;
	}

	public boolean checkEqualArgsLength(int length, String[] args,
			CommandSender sender, String label, String usage) {
		if (args.length == length) return true;
		plugin.sendMessage(sender, Lang.ERROR_COMMAND.get());
		plugin.sendMessage(sender, Lang.ERROR_COMMAND_USAGE.get()
				.replace("{label}", label)
				.replace("{usage}", usage));

		return false;
	}

	public boolean checkPlayer(CommandSender sender) {
		if (sender instanceof Player) return true;
		plugin.sendMessage(sender, Lang.ERROR_PLAYER_COMMAND.get());
		return false;
	}
}
