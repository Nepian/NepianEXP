package nepian.bukkit.plugin.exp;

import java.util.Arrays;

import org.bukkit.entity.Player;

public class ExpManager {
	private static final int maxExp = 2100000000;
	private static int[] totalExpToReachLevel;	/* レベル毎の経験値総量を記録しておく */

	static {
		initExpTables(40);
	}

	/**
	 * 経験値をリセットする
	 * レベル・経験値・経験値総量を全て０に設定
	 * @param player 対象のプレイヤー
	 * @return 経験値をリセットした対象プレイヤー
	 */
	public static Player resetExp(Player player) {
		player.setLevel(0);
		player.setExp(0);
		player.setTotalExperience(0);
		return player;
	}

	/**
	 * 経験値をレベルに変換する
	 * 余りの経験値は切り捨て
	 * @param exp レベルに変換する経験値
	 * @return 変換後のレベル
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
	 * レベルを経験値に変換する
	 * @param level 経験値に変換するレベル
	 * @return 変換後の経験値
	 */
	private static int convertLevelToExp(int level) {
		int exp =
				(level >= 30) ? (int)(3.5 * level * level - 151.5 * level + 2220) :
					(level >= 16) ? (int)(1.5 * level * level - 29.5 * level + 360) :
						17 * level;
		return exp;
	}

	/**
	 * 経験値テーブルを初期化する
	 * @param maxLevel 経験値テーブルの上限
	 */
	private static void initExpTables(int maxLevel) {
		totalExpToReachLevel = new int[maxLevel];

		for (int i = 0; i < totalExpToReachLevel.length; i++) {
			totalExpToReachLevel[i] = convertLevelToExp(i);
		}
	}

	/**
	 * 経験値を追加する
	 * @param player 対象のプレイヤー
	 * @param amount 追加する量
	 * @return 経験値変化後の対象プレイヤー
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
	 * 経験値の総量を取得する
	 * @param player 対象のプレイヤー
	 * @return 対象プレイヤーの経験値の総量
	 */
	public static int getTotalExp(Player player) {
		return player.getTotalExperience();
	}

	/**
	 * レベルを取得する
	 * @param player 対象のプレイヤー
	 * @return 対象プレイヤーのレベル
	 */
	public static int getLevel(Player player) {
		return player.getLevel();
	}

	/**
	 * 経験値を取得する
	 * 現在のレベルから次のレベルまでに必要な経験値のうち、
	 * 自分が所持する経験値の割合を取得する
	 * （ゲーム画面の経験値メータ）
	 * @param player 対象のプレイヤー
	 * @return 取得した経験値
	 */
	public static float getExp(Player player) {
		return player.getExp();
	}

	/**
	 * 現在の経験値量から次のレベルまでに必要な経験値を計算する
	 * @param player 対象のプレイヤー
	 * @return 対象のプレイヤーがレベルアップまでに必要な経験値
	 */
	public static int getRestExpNeededToLevelUp(Player player) {
		int level = getLevel(player);
		int total = getTotalExp(player);
		return convertLevelToExp(level + 1) - total;
	}

	/**
	 * 現在のレベルから次のレベルまでに必要な経験値を計算する
	 * @param level 現在のレベル
	 * @return 必要な経験値
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
