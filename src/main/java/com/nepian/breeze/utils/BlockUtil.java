package com.nepian.breeze.utils;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class BlockUtil {
	/**
	 * �u���b�N���Ŕ��ǂ����𔻒肷��
	 * @param block ���肷��u���b�N
	 * @return �u���b�N���Ŕ��ǂ���
	 */
	public static boolean isSign(Block block) {
		return block.getState() instanceof Sign;
	}
}
