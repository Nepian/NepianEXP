package nepian.bukkit.plugin.exp;

import java.io.IOException;

import nepian.bukkit.plugin.exp.commands.player.PlayerManager;
import nepian.bukkit.plugin.exp.configration.Logger;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class Main extends JavaPlugin {
	private static Main instance;
	private Economy economy = null;
	private PlayerManager playerMan;
	private Exp exp;

	public static Main getInstance() {
		return instance;
	}

	public void onEnable() {
		instance = this;

		getConfig().options().copyDefaults(true);
		saveDefaultConfig();

		if (!setupEconomy()) {
			Logger.log("&4Vault依存関係プラグインが見つかりません");
		}

		playerMan = new PlayerManager(this);
		getCommand("exp").setExecutor(exp = new Exp(this));

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			Logger.log("&4Metricsをスタートできませんでした: &e" + e.getMessage());
		}

		Logger.log("&d" + getName() + " v" + getDescription().getVersion() + " enabled!");
	}

	public void onDisable() {
		Logger.log("&d" + getName() + " disabled!");
	}

	public void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider
				= getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return economy != null;
	}

	public Economy getEconomy() {
		return economy;
	}

	public PlayerManager getPlayerMan() {
		return playerMan;
	}

	public Exp getExp() {
		return exp;
	}
}
