package com.nepian.breeze.utils;

import java.util.Arrays;
import java.util.regex.Pattern;



public class XpUtil {
	private static int[] totalExpToReachLevel;	/* レベル毎の経験値総量を記録しておく */

	static { initExpTables(40); }

	/**
	 * 経験値テーブルを初期化する
	 * @param maxLevel 経験値テーブルの上限
	 */
	private static void initExpTables(int maxLevel) {
		totalExpToReachLevel = new int[maxLevel];
		for (int i = 0; i < totalExpToReachLevel.length; i++) {
			totalExpToReachLevel[i] = convertXpToExp(i);
		}
	}

	/**
	 * XPをEXPに変換する
	 * @param XP EXPに変換するXP
	 * @return XPが変換されたEXP
	 */
	public static int convertXpToExp(int xp) {
		if (xp >= 30) return (int) (3.5 * xp * xp - 151.5 * xp + 2220);
		if (xp >= 16) return (int) (1.5 * xp * xp - 29.5 * xp + 360);
		if (xp >= 0)  return 17 * xp;
		return 0;
	}

	/**
	 * EXPをXPに変換する
	 * 変換後に余ったEXPは切り捨てます
	 * @param exp XPに変換するEXP
	 * @return EXPが変換されたXP
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
	 * EXPゲージをEXPに変換する
	 * @param xp 現在のXP
	 * @param expPercentage EXPゲージの値
	 * @return EXPゲージ分のEXP
	 */
	public static int convertExpPercentageToExp(int xp, float expPercentage) {
		return (int) (Math.round(getExpNeededToLevelUp(xp)) * expPercentage);
	}

	/**
	 * 次のレベルまで必要なEXPを取得する
	 * @param xp 現在のXP
	 * @return 次のレベルまでに必要なEXP
	 */
	public static int getExpNeededToLevelUp(int xp) {
		if (xp >= 30) return 62 + (xp - 30) * 7;
		if (xp >= 15) return 17 + (xp - 15) * 3;
		if (xp >= 0)  return 17;
		return 0;
	}

	/**
	 * 2つのXPのEXP差を絶対値で取得する
	 * @param xp1 差を求めるXPの一つ目
	 * @param xp2 差を求めるXPの二つ目
	 * @return 2つのXPのEXP差の絶対値
	 */
	public static int getExpXpToXp(int xp1, int xp2) {
		return convertXpToExp(xp1) - convertXpToExp(xp2);
	}

	/**
	 * 文字列のXPとして正当かどうか判定する
	 * @param string XPか判定する文字列
	 * @return 文字列がXPかどうか
	 */
	public static boolean isXp(String string) {
		return Pattern.compile("^-?[0-9]+L$").matcher(string).matches();
	}

	/**
	 * 文字列がEXPとして正当かどうか判定する
	 * @param string EXPか判定する文字列
	 * @return 文字列がEXPかどうか
	 */
	public static boolean isExp(String string) {
		return Pattern.compile("^-?[0-9]+$").matcher(string).matches();
	}

	/**
	 * 最後がLの文字列からXPに変換する
	 * @param string 最後がLの文字列
	 * @return 文字列から変換したXP、変換失敗は-1を返す
	 */
	public static int getXpFromString(String string) {
		return Integer.parseInt(string.substring(0, string.length() - 1));
	}

	/**
	 * 文字列からEXPに変換する
	 * @param string EXPに変換する文字列
	 * @return 文字列から変換したEXP、変換失敗は-1を返す
	 */
	public static int getExpFromString(String string) {
		return Integer.parseInt(string);
	}
}
