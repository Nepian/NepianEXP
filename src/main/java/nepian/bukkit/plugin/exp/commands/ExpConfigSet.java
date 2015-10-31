package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;

public class ExpConfigSet extends ExpCommand {

	public ExpConfigSet() {
		super(CommandData.CONFIG_SET);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(4, sender, label, args)) return false;

		Main plugin = Main.getInstance();
		String key = args[2];
		String value = args[3];
		Configs conf = Configs.getEnum(key);

		if (conf == null) {
			plugin.sendMessage(sender, Lang.EXP_CONFIG_SET_NOT_KEY.get()
					.replace("{key}", key));
			return true;
		}

		String oldValue = conf.getString();

		conf.set(value);
		plugin.saveConfig();

		String newValue = conf.getString();

		plugin.sendMessage(sender, Lang.EXP_CONFIG_SET_CHANGE.get()
				.replace("{key}", key)
				.replace("{oldValue}", oldValue)
				.replace("{newValue}", newValue)
				);
		plugin.sendMessage(sender, Lang.EXP_CONFIG_SET_END.get());

		return true;
	}

}
