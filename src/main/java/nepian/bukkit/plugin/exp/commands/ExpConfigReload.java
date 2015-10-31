package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Logger;

import org.bukkit.command.CommandSender;

public class ExpConfigReload extends ExpCommand {

	public ExpConfigReload() {
		super(CommandData.CONFIG_RELOAD);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(2, sender, label, args)) return false;

		Configs.reload();
		Main.getInstance().sendMessage(sender, Lang.EXP_CONFIG_RELOAD_MESSAGE.get());
		Logger.log(Lang.EXP_CONFIG_RELOAD_LOG.get());

		return true;
	}

}
