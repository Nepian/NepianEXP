package com.nepian.breeze.utils;

import java.util.Arrays;
import java.util.regex.Pattern;



public class XpUtil {
	private static int[] totalExpToReachLevel;	/* ���x�����̌o���l���ʂ��L�^���Ă��� */

	static { initExpTables(40); }

	/**
	 * �o���l�e�[�u��������������
	 * @param maxLevel �o���l�e�[�u���̏��
	 */
	private static void initExpTables(int maxLevel) {
		totalExpToReachLevel = new int[maxLevel];
		for (int i = 0; i < totalExpToReachLevel.length; i++) {
			totalExpToReachLevel[i] = convertXpToExp(i);
		}
	}

	/**
	 * XP��EXP�ɕϊ�����
	 * @param XP EXP�ɕϊ�����XP
	 * @return XP���ϊ����ꂽEXP
	 */
	public static int convertXpToExp(int xp) {
		if (xp >= 30) return (int) (3.5 * xp * xp - 151.5 * xp + 2220);
		if (xp >= 16) return (int) (1.5 * xp * xp - 29.5 * xp + 360);
		if (xp >= 0)  return 17 * xp;
		return 0;
	}

	/**
	 * EXP��XP�ɕϊ�����
	 * �ϊ���ɗ]����EXP�͐؂�̂Ă܂�
	 * @param exp XP�ɕϊ�����EXP
	 * @return EXP���ϊ����ꂽXP
	 */
	public static int convertExpToXp(int exp) {
		if (!(0 < exp)) return 0;

		while (exp > totalExpToReachLevel[totalExpToReachLevel.length - 1]) {
			int newMax = totalExpToReachLevel.length + 40;
			initExpTables(newMax);
		}

		int pos = Arrays.binarySearch(totalExpToReachLevel, exp);
		int xp = (pos < 0) ? -pos - 2 : pos;

		return xp;
	}

	/**
	 * EXP�Q�[�W��EXP�ɕϊ�����
	 * @param xp ���݂�XP
	 * @param expPercentage EXP�Q�[�W�̒l
	 * @return EXP�Q�[�W����EXP
	 */
	public static int convertExpPercentageToExp(int xp, float expPercentage) {
		return (int) (Math.round(getExpNeededToLevelUp(xp)) * expPercentage);
	}

	/**
	 * ���̃��x���܂ŕK�v��EXP���擾����
	 * @param xp ���݂�XP
	 * @return ���̃��x���܂łɕK�v��EXP
	 */
	public static int getExpNeededToLevelUp(int xp) {
		if (xp >= 30) return 62 + (xp - 30) * 7;
		if (xp >= 15) return 17 + (xp - 15) * 3;
		if (xp >= 0)  return 17;
		return 0;
	}

	/**
	 * 2��XP��EXP�����Βl�Ŏ擾����
	 * @param xp1 �������߂�XP�̈��
	 * @param xp2 �������߂�XP�̓��
	 * @return 2��XP��EXP���̐�Βl
	 */
	public static int getExpXpToXp(int xp1, int xp2) {
		return convertXpToExp(xp1) - convertXpToExp(xp2);
	}

	/**
	 * �������XP�Ƃ��Đ������ǂ������肷��
	 * @param string XP�����肷�镶����
	 * @return ������XP���ǂ���
	 */
	public static boolean isXp(String string) {
		return Pattern.compile("^-?[0-9]+L$").matcher(string).matches();
	}

	/**
	 * ������EXP�Ƃ��Đ������ǂ������肷��
	 * @param string EXP�����肷�镶����
	 * @return ������EXP���ǂ���
	 */
	public static boolean isExp(String string) {
		return Pattern.compile("^-?[0-9]+$").matcher(string).matches();
	}

	/**
	 * �ŌオL�̕����񂩂�XP�ɕϊ�����
	 * @param string �ŌオL�̕�����
	 * @return �����񂩂�ϊ�����XP�A�ϊ����s��-1��Ԃ�
	 */
	public static int getXpFromString(String string) {
		return Integer.parseInt(string.substring(0, string.length() - 1));
	}

	/**
	 * �����񂩂�EXP�ɕϊ�����
	 * @param string EXP�ɕϊ����镶����
	 * @return �����񂩂�ϊ�����EXP�A�ϊ����s��-1��Ԃ�
	 */
	public static int getExpFromString(String string) {
		return Integer.parseInt(string);
	}
}
