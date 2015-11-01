package com.nepian.nepianexp.commands.player;

import java.util.LinkedList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.nepian.nepianexp.NepianEXP;

public class PlayerManager implements Listener {
	private NepianEXP plugin;
	private LinkedList<Player> players;

	public PlayerManager(NepianEXP instance) {
		plugin = instance;
		players = new LinkedList<Player>();

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void playerLogin(PlayerLoginEvent event) {
		players.add(event.getPlayer());
	}

	@EventHandler
	public void playerLogout(PlayerQuitEvent event) {
		players.remove(event.getPlayer());
	}

	public Player getPlayer(String name) {
		for (Player player : players) {
			if (player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}
		return null;
	}

	public LinkedList<Player> getPlayers() {
		return players;
	}
}
