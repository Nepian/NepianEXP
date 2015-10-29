package nepian.bukkit.plugin.exp;

import org.bukkit.command.CommandSender;

public abstract class ExpCommand {
	private final String name;
	private final String usage;
	private final String permission;
	private final String description;

	public ExpCommand(String name, String usage, String permission, String description) {
		this.name = name;
		this.usage = usage;
		this.permission = permission;
		this.description = description;
	}

	public abstract boolean useCommand(CommandSender sender, String label, String[] args);

	public String getUsage() {
		return usage;
	}

	public String getPermission() {
		return permission;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
