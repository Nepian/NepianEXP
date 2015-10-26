package nepian.bukkit.plugin.exp.configration;

import nepian.bukkit.plugin.exp.Main;

import org.bukkit.command.CommandSender;

public enum Permissions {
	INFO,
	BUY,
	SELL,
	ADD,
	RELOAD,
	RESET,

	CONFIG,
	CONFIG_RELOAD,

	EXP {
		public String get() {
			return "nepian.exp";
		}
	};

	private static final Main plugin = Main.getInstance();

	public String get() {
		return "nepian.exp." + name().toLowerCase().replace('_', '.');
	}

	public boolean hasPermission(CommandSender sender, String label, String[] args) {

		if (!sender.hasPermission(get())) {
			StringBuffer names = new StringBuffer("/" + label + " ");
			for (String name : args) {
				names.append(name + " ");
			}
			String msg = Lang.ERROR_COMMAND_NO_PERMISSION.get()
					.replace("{command}", names);
			plugin.sendMessage(sender, msg);
			return false;
		}

		return true;
	}
}
