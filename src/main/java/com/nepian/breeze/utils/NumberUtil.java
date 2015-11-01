package com.nepian.breeze.utils;

public class NumberUtil {
	/**
	 * ������Double�^�ɕϊ��\�����ׂ�
	 * @param string �ϊ����镶����
	 * @return ������Double�^�ɕϊ��\��
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
	 * ������Integer�^�ɕϊ��\�����ׂ�
	 * @param string �ϊ����镶����
	 * @return ������Integer�^�ɕϊ��\��
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
