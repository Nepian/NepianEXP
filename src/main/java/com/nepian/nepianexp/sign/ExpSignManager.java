package com.nepian.nepianexp.sign;

import static com.nepian.breeze.utils.BlockUtil.*;
import static com.nepian.breeze.utils.PlayerUtil.*;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.nepian.breeze.utils.NumberUtil;
import com.nepian.nepianexp.NepianEXP;
import com.nepian.nepianexp.configuration.Configs;
import com.nepian.nepianexp.configuration.Lang;

public class ExpSignManager implements Listener {
	private NepianEXP plugin;

	public ExpSignManager(NepianEXP instance) {
		this.plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void createSign(SignChangeEvent event) {
		String name = event.getLine(0);

		if (!name.equalsIgnoreCase("[EXP]")) return;

		Player player = event.getPlayer();

		if (!player.hasPermission("nepian.exp.sign.create")) {
			plugin.sendMessage(event.getPlayer(), "Not EXP sign permission");
			dropSignAndCancelEvent(event);
			return;
		}

		if (!NumberUtil.isInteger(event.getLine(1))) {
			dropSignAndCancelEvent(event);
			return;
		}

		if (Integer.parseInt(event.getLine(1)) <= 0) {
			dropSignAndCancelEvent(event);
			return;
		}

		event.setLine(0, "[EXP]");
		event.setLine(2, "");
		event.setLine(3, "adminshop");
	}

	private void dropSignAndCancelEvent(SignChangeEvent event) {
        event.getBlock().breakNaturally();
        event.setCancelled(true);
    }

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void clickSign(PlayerInteractEvent event) {
		if (!event.hasBlock()) return;
		if (!isSign(event.getClickedBlock())) return;

		Sign sign = (Sign) event.getClickedBlock().getState();
		String name = sign.getLine(0);

		if (!name.equalsIgnoreCase("[EXP]")) return;
		if (!sign.getLine(3).equalsIgnoreCase("adminshop")) return;

		if (!NumberUtil.isInteger(sign.getLine(1))) {
			return;
		}

		int quantity = Integer.parseInt(sign.getLine(1));
		Action action = event.getAction();
		Player player = event.getPlayer();
		double money = 0;

		if (action == Action.LEFT_CLICK_BLOCK) {
			money = quantity * Configs.EXP_BUY_RATE.getDouble();
			if (money > plugin.getEconomy().getBalance(player)) {
				plugin.sendMessage(player, Lang.ERROR_NOT_ENOUGH_MONEY.get());
				return;
			}
			EconomyResponse response = plugin.getEconomy().withdrawPlayer(player, money);
			if (!response.transactionSuccess()) {
				String msg = Lang.ERROR_ECONOMY_FAULT.get()
						.replace("{error}", response.errorMessage);
				plugin.sendMessage(player, msg);
				return;
			}
			Integer oldExp = getExp(player);
			Integer oldLevel = getXp(player);

			addExp(player, quantity);

			Integer newExp = getExp(player);
			Integer newLevel = getXp(player);

			plugin.sendMessage(player, Lang.EXP_BUY.get()
					.replace("{amount}", sign.getLine(1)));
			plugin.sendMessage(player, Lang.EXP_CHANGE.get()
					.replace("{oldExp}", oldExp.toString())
					.replace("{oldLevel}", oldLevel.toString())
					.replace("{newExp}", newExp.toString())
					.replace("{newLevel}", newLevel.toString()));

		} else if (action == Action.RIGHT_CLICK_BLOCK) {
			if (quantity > getExp(player)) {
				plugin.sendMessage(player, Lang.ERROR_NOT_ENOUGH_EXP.get());
				return;
			}
			money = quantity * Configs.EXP_SELL_RATE.getDouble();
			EconomyResponse response = plugin.getEconomy().depositPlayer(player, money);
			if (!response.transactionSuccess()) {
				String msg = Lang.ERROR_ECONOMY_FAULT.get()
						.replace("{error}", response.errorMessage);
				plugin.sendMessage(player, msg);
				return;
			}

			Integer oldExp = getExp(player);
			Integer oldLevel = getXp(player);

			addExp(player, -quantity);

			Integer newExp = getExp(player);
			Integer newLevel = getXp(player);

			plugin.sendMessage(player, Lang.EXP_SELL.get()
					.replace("{amount}", sign.getLine(1)));
			plugin.sendMessage(player, Lang.EXP_CHANGE.get()
					.replace("{oldExp}", oldExp.toString())
					.replace("{oldLevel}", oldLevel.toString())
					.replace("{newExp}", newExp.toString())
					.replace("{newLevel}", newLevel.toString()));

			return;
		}
	}
}
