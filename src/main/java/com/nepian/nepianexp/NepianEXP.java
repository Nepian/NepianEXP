package com.nepian.nepianexp;

import java.io.IOException;

import net.milkbowl.vault.Metrics;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.nepian.nepianexp.commands.player.PlayerManager;
import com.nepian.nepianexp.configuration.Logger;
import com.nepian.nepianexp.sign.ExpSignManager;

public class NepianEXP extends JavaPlugin {
	private static NepianEXP instance;
	private Economy economy = null;
	private PlayerManager playerMan;
	private Exp exp;
	public ExpSignManager signMan;

	public static NepianEXP getInstance() {
		return instance;
	}

	public void onEnable() {
		instance = this;

		saveDefaultConfig();

		if (!setupEconomy()) {
			Logger.log("&4Vault依存関係プラグインが見つかりません");
		}

		signMan = new ExpSignManager(this);
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
