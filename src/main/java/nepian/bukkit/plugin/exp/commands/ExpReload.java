package nepian.bukkit.plugin.exp.commands;

import nepian.bukkit.plugin.exp.ExpCommand;
import nepian.bukkit.plugin.exp.Main;
import nepian.bukkit.plugin.exp.configration.CommandData;
import nepian.bukkit.plugin.exp.configration.Configs;
import nepian.bukkit.plugin.exp.configration.Lang;
import nepian.bukkit.plugin.exp.configration.Logger;

import org.bukkit.command.CommandSender;

public class ExpReload extends ExpCommand {

	public ExpReload() {
		super(CommandData.RELOAD);
	}

	@Override
	public boolean useCommand(CommandSender sender, String label, String[] args) {
		if (!super.checkPermission(sender, label)) return false;
		if (!super.checkEqualLength(1, sender, label, args)) return false;

		Configs.reload();
		Lang.reload();

		String msg = Lang.EXP_RELOAD.get()
				.replace("{name}", Main.getInstance().getName());
		Main.getInstance().sendMessage(sender, msg);
		Logger.log(msg);

		return true;
	}

}
