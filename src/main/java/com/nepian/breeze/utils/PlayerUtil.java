package com.nepian.breeze.utils;

import org.bukkit.entity.Player;

public class PlayerUtil {
	public static final int MAX_EXP = 2100000000;

	/**
	 * �v���C���[��XP���擾����
	 * @param player XP���擾����v���C���[
	 * @return �v���C���[����擾����XP
	 */
	public static int getXp(Player player) {
		return player.getLevel();
	}

	/**
	 * �v���C���[��EXP�Q�[�W�̒l���擾����
	 * @param player �l���擾����v���C���[
	 * @return �擾�����l
	 */
	public static float getExpPercentage(Player player) {
		return player.getExp();
	}

	/**
	 * �v���C���[��EXP���擾����
	 * @param player �擾�Ώۂ̃v���C���[
	 * @return �擾����EXP
	 */
	public static int getExp(Player player) {
		int xp = getXp(player);
		float percentage = getExpPercentage(player);
		return XpUtil.convertXpToExp(xp) + XpUtil.convertExpPercentageToExp(xp, percentage);
	}

	/**
	 * �v���C���[��XP��ݒ肷��
	 * @param player �ݒ�Ώۂ̃v���C���[
	 * @param xp �ݒ肷��XP��
	 * @return XP��ݒ肵���v���C���[
	 */
	public static Player setXp(Player player, int xp) {
		player.setLevel(xp);
		return player;
	}

	/**
	 * �v���C���[��EXP�Q�[�W�̒l��ݒ肷��
	 * @param player �ݒ�Ώۂ̃v���C���[
	 * @param percentage �ݒ肷��l
	 * @return EXP�Q�[�W�̒l��ݒ肵���v���C���[
	 */
	public static Player setExpPercentage(Player player, float percentage) {
		player.setExp(percentage);
		return player;
	}

	/**
	 * �v���C���[��EXP���ʂ�ݒ肷��
	 * @param player �ݒ�Ώۂ̃v���C���[
	 * @param exp �ݒ肷��EXP
	 * @return EXP���ʂ�ݒ肵���v���C���[
	 */
	public static Player setTotalExp(Player player, int exp) {
		player.setTotalExperience(exp);
		return player;
	}

	/**
	 * �v���C���[��EXP��ݒ肷��
	 * @param player �ݒ�Ώۂ̃v���C���[
	 * @param exp �ݒ肷��EXP��
	 * @return EXP��ݒ肵���v���C���[
	 */
	public static Player setExp(Player player, int quantity) {
		int exp = (quantity > MAX_EXP) ? MAX_EXP : (quantity < 0) ? 0 : quantity;
		int xp = XpUtil.convertExpToXp(exp);

		float expPercentage = (float) (exp - XpUtil.convertXpToExp(xp))
				/ (float) XpUtil.getExpNeededToLevelUp(xp);

		setTotalExp(player, exp);
		setXp(player, xp);
		setExpPercentage(player, expPercentage);

		return player;
	}

	/**
	 * �v���C���[�̎��̃��x���A�b�v�܂łɕK�v�Ȏc���EXP���擾����
	 * @param player �擾�Ώۂ̃v���C���[
	 * @return ���x���A�b�v�܂łɕK�v�Ȏc���EXP
	 */
	public static int getRestExpNeededToLevelUp(Player player) {
		int xp = getXp(player);
		int exp = getExp(player);
		return XpUtil.convertXpToExp(xp + 1) - exp;
	}

	/**
	 * �v���C���[�̎��̃��x���܂łɕK�v��EXP���擾����
	 * @param player �擾�Ώۂ̃v���C���[
	 * @return ���̃��x���܂łɕK�v��EXP
	 */
	public static int getExpNeededForNextLevel(Player player) {
		return XpUtil.getExpNeededToLevelUp(getXp(player));
	}

	/**
	 * �v���C���[�̎w�肵��XP������EXP���擾����
	 * @param player
	 * @param diff
	 * @return
	 */
	public static int getExpXpToXp(Player player, int diff) {
		int xp = getXp(player);
		return XpUtil.getExpXpToXp(xp + diff, xp);
	}

	/**
	 * �v���C���[��EXP��ǉ�����
	 * @param player �ǉ��Ώۂ̃v���C���[
	 * @param quantity �ǉ�����EXP��
	 * @return EXP��ύX�����v���C���[
	 */
	public static Player addExp(Player player, int quantity) {
		return setExp(player, getExp(player) + quantity);
	}

	/**
	 * �v���C���[��EXP��0�ɂ���
	 * @param player �Ώۂ̃v���C���[
	 * @return EXP��0�ɂ����v���C���[
	 */
	public static Player clearExp(Player player) {
		return  setExp(player, 0);
	}
}
