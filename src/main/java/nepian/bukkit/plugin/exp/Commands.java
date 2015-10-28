package nepian.bukkit.plugin.exp;

import java.lang.reflect.Method;

import nepian.bukkit.plugin.exp.commands.Add;
import nepian.bukkit.plugin.exp.commands.Buy;
import nepian.bukkit.plugin.exp.commands.Config;
import nepian.bukkit.plugin.exp.commands.Info;
import nepian.bukkit.plugin.exp.commands.Reload;
import nepian.bukkit.plugin.exp.commands.Reset;
import nepian.bukkit.plugin.exp.commands.Sell;
import nepian.bukkit.plugin.exp.configration.Lang;

import org.bukkit.command.CommandSender;

public enum Commands {
	INFO(Info.class, "/exp info"),
	BUY(Buy.class, "/exp buy {<exp> or <level>L}"),
	SELL(Sell.class, "/exp sell {<exp> or <level>L}"),

	ADD(Add.class, "/exp add {<exp> or <level>L}"),
	RESET(Reset.class, "/exp reset <player>"),
	RELOAD(Reload.class, "/exp reload"),
	CONFIG(Config.class, "/exp config (reload)");


	private static final Main plugin = Main.getInstance();
	private Class<?> clazz;
	private String usage;

	private Commands(Class<?> clazz, String usage) {
		this.clazz = clazz;
		this.usage = usage;
	}

	public void useCommand(CommandSender sender, String label, String[] args) {
		try {
			Method method = clazz.getMethod("useCommand",
					CommandSender.class, String.class, String[].class);
			method.invoke(clazz, sender, label, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Commands getCommand(String name) {
		for (Commands command : values()) {
			if (command.name().equalsIgnoreCase(name)) {
				return command;
			}
		}
		return null;
	}

	public String getUsage() {
		return usage;
	}

	public String getPermission() {
		return "nepian.exp." + name().toLowerCase().replace('_', '.');
	}

	public boolean hasPermission(CommandSender sender, String label, String[] args) {

		if (!sender.hasPermission(this.getPermission())) {
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
