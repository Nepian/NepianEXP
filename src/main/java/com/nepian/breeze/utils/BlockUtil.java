package com.nepian.breeze.utils;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class BlockUtil {
	/**
	 * ブロックが看板かどうかを判定する
	 * @param block 判定するブロック
	 * @return ブロックが看板かどうか
	 */
	public static boolean isSign(Block block) {
		return block.getState() instanceof Sign;
	}
}
