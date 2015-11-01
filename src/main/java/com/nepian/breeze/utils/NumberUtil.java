package com.nepian.breeze.utils;

public class NumberUtil {
	/**
	 * •¶Žš—ñ‚ªDoubleŒ^‚É•ÏŠ·‰Â”\‚©’²‚×‚é
	 * @param string •ÏŠ·‚·‚é•¶Žš—ñ
	 * @return •¶Žš—ñ‚ªDoubleŒ^‚É•ÏŠ·‰Â”\‚©
	 */
	public static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

	/**
	 * •¶Žš—ñ‚ªIntegerŒ^‚É•ÏŠ·‰Â”\‚©’²‚×‚é
	 * @param string •ÏŠ·‚·‚é•¶Žš—ñ
	 * @return •¶Žš—ñ‚ªIntegerŒ^‚É•ÏŠ·‰Â”\‚©
	 */
	public static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
