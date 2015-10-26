package nepian.bukkit.plugin.exp;

import java.util.Arrays;

import org.bukkit.entity.Player;

public class ExpManager {
	private static final int maxExp = 2100000000;
	private static int[] totalExpToReachLevel;	/* ���x�����̌o���l���ʂ��L�^���Ă��� */

	static {
		initExpTables(40);
	}

	/**
	 * �o���l�����Z�b�g����
	 * ���x���E�o���l�E�o���l���ʂ�S�ĂO�ɐݒ�
	 * @param player �Ώۂ̃v���C���[
	 * @return �o���l�����Z�b�g�����Ώۃv���C���[
	 */
	public static Player resetExp(Player player) {
		player.setLevel(0);
		player.setExp(0);
		player.setTotalExperience(0);
		return player;
	}

	/**
	 * �o���l�����x���ɕϊ�����
	 * �]��̌o���l�͐؂�̂�
	 * @param exp ���x���ɕϊ�����o���l
	 * @return �ϊ���̃��x��
	 */
	private static int convertExpToLevel(int exp) {
		if (!(0 < exp)) return 0;

		while (exp > totalExpToReachLevel[totalExpToReachLevel.length - 1]) {
			int newMax = totalExpToReachLevel.length + 40;
			initExpTables(newMax);
		}

		int pos = Arrays.binarySearch(totalExpToReachLevel, exp);
		int level = (pos < 0) ? -pos - 2 : pos;

		return level;
	}

	/**
	 * ���x�����o���l�ɕϊ�����
	 * @param level �o���l�ɕϊ����郌�x��
	 * @return �ϊ���̌o���l
	 */
	private static int convertLevelToExp(int level) {
		int exp =
				(level >= 30) ? (int)(3.5 * level * level - 151.5 * level + 2220) :
					(level >= 16) ? (int)(1.5 * level * level - 29.5 * level + 360) :
						17 * level;
		return exp;
	}

	/**
	 * �o���l�e�[�u��������������
	 * @param maxLevel �o���l�e�[�u���̏��
	 */
	private static void initExpTables(int maxLevel) {
		totalExpToReachLevel = new int[maxLevel];

		for (int i = 0; i < totalExpToReachLevel.length; i++) {
			totalExpToReachLevel[i] = convertLevelToExp(i);
		}
	}

	/**
	 * �o���l��ǉ�����
	 * @param player �Ώۂ̃v���C���[
	 * @param amount �ǉ������
	 * @return �o���l�ω���̑Ώۃv���C���[
	 */
	public static Player addExp(Player player, int amount) {
		int crntTotalExp = getTotalExp(player);

		int newTotalExp = Math.max(crntTotalExp + amount, 0);
		if (newTotalExp > maxExp) { newTotalExp = maxExp; }
		int newLevel = convertExpToLevel(newTotalExp);
		int newLevelExp = convertLevelToExp(newLevel);
		int newExpNeededToLevelUp = getExpNeededToLevelUp(newLevel);
		float newExp = (float)(newTotalExp - newLevelExp) / (float)newExpNeededToLevelUp;

		player.setLevel(newLevel);
		player.setExp(newExp);
		player.setTotalExperience(newTotalExp);

		return player;
	}

	/**
	 * �o���l�̑��ʂ��擾����
	 * @param player �Ώۂ̃v���C���[
	 * @return �Ώۃv���C���[�̌o���l�̑���
	 */
	public static int getTotalExp(Player player) {
		return player.getTotalExperience();
	}

	/**
	 * ���x�����擾����
	 * @param player �Ώۂ̃v���C���[
	 * @return �Ώۃv���C���[�̃��x��
	 */
	public static int getLevel(Player player) {
		return player.getLevel();
	}

	/**
	 * �o���l���擾����
	 * ���݂̃��x�����玟�̃��x���܂łɕK�v�Ȍo���l�̂����A
	 * ��������������o���l�̊������擾����
	 * �i�Q�[����ʂ̌o���l���[�^�j
	 * @param player �Ώۂ̃v���C���[
	 * @return �擾�����o���l
	 */
	public static float getExp(Player player) {
		return player.getExp();
	}

	/**
	 * ���݂̌o���l�ʂ��玟�̃��x���܂łɕK�v�Ȍo���l���v�Z����
	 * @param player �Ώۂ̃v���C���[
	 * @return �Ώۂ̃v���C���[�����x���A�b�v�܂łɕK�v�Ȍo���l
	 */
	public static int getRestExpNeededToLevelUp(Player player) {
		int level = getLevel(player);
		int total = getTotalExp(player);
		return convertLevelToExp(level + 1) - total;
	}

	/**
	 * ���݂̃��x�����玟�̃��x���܂łɕK�v�Ȍo���l���v�Z����
	 * @param level ���݂̃��x��
	 * @return �K�v�Ȍo���l
	 */
	private static int getExpNeededToLevelUp(int level) {
		return (level >= 30) ? 62 + (level - 30) * 7:
				(level >= 15) ? 17 + (level - 15) * 3: 17;
	}

	public static int getExpLevelToLevel(Player player, int diff) {
		int crntLevel = getLevel(player);
		return convertLevelToExp(crntLevel + diff) - convertLevelToExp(crntLevel);
	}
}
