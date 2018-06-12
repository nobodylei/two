package com.rjwl.reginet.gaotuo.utils;

import android.util.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/4/27.
 */

public class StrUtil {

    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }

    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    public static List<Integer> getIndex(String str) {
        List<Integer> index = new ArrayList<>();
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '1') {
                //Log.d("index", i + "");
                index.add(i);
            }
        }
        return index;
    }

    public static int NumberOf1(String str) {
        int count = 0;
        char[] array = str.toCharArray();
        for (char c : array) {
            if (c == '1')
                count++;
        }
        return count;
    }

    public static String hexStrToBinaryStr(String hexString) {

        if (hexString == null || hexString.equals("")) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 将每一个十六进制字符分别转换成一个四位的二进制字符
        for (int i = 0; i < hexString.length(); i++) {
            String indexStr = hexString.substring(i, i + 1);
            String binaryStr = Integer.toBinaryString(Integer.parseInt(indexStr, 16));
            while (binaryStr.length() < 4) {
                binaryStr = "0" + binaryStr;
            }
            sb.append(binaryStr);
        }
        return sb.toString();
    }

    public static List<Integer> getPower(String str) {
        String binary = hexStrToBinaryStr(str).trim();
        List<Integer> list = getIndex(binary);
        List<Integer> power = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) <= 7) {
                power.add(8 - list.get(i));
            } else if (list.get(i) <= 15) {
                power.add(list.get(i) + (8 - (list.get(i) - 8) * 2));
            } else if (list.get(i) <= 23) {
                power.add(list.get(i) + (8 - (list.get(i) - 16) * 2));
            } else if (list.get(i) <= 31) {
                power.add(list.get(i) + (8 - (list.get(i) - 24) * 2));
            } else if (list.get(i) <= 39) {
                power.add(list.get(i) + (8 - (list.get(i) - 32) * 2));
            } else if (list.get(i) <= 47) {
                power.add(list.get(i) + (8 - (list.get(i) - 40) * 2));
            } else if (list.get(i) <= 55) {
                power.add(list.get(i) + (8 - (list.get(i) - 48) * 2));
            } else if (list.get(i) < 64) {
                power.add(list.get(i) + (8 - (list.get(i) - 56) * 2));
            }
        }
        return power;
    }

    /**
     * 二进制字符串转换为十六进制字符串
     * <p>
     * 二进制字符串位数必须满足是4的倍数
     *
     * @param binaryStr
     * @return
     */
    public static String binaryStrToHexStr(String binaryStr) {

        if (binaryStr == null || binaryStr.equals("") || binaryStr.length() % 4 != 0) {
            return null;
        }
        StringBuffer sbs = new StringBuffer();
        // 二进制字符串是4的倍数，所以四位二进制转换成一位十六进制
        for (int i = 0; i < binaryStr.length() / 4; i++) {
            String subStr = binaryStr.substring(i * 4, i * 4 + 4);
            String hexStr = Integer.toHexString(Integer.parseInt(subStr, 2));
            sbs.append(hexStr);
        }
        return sbs.toString();
    }

    public static String setPhone(String phone) {
        if (phone.length() != 11) {
            return phone;
        }
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }

    public static String reverse(String str) {
        String[] strs = str.split("");
        StringBuilder builder = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            builder.append(strs[i]);
        }
        for (int i = 15; i >= 8; i--) {
            builder.append(strs[i]);
        }
        for (int i = 23; i >= 16; i--) {
            builder.append(strs[i]);
        }
        for (int i = 31; i >= 24; i--) {
            builder.append(strs[i]);
        }
        for (int i = 39; i >= 32; i--) {
            builder.append(strs[i]);
        }
        for (int i = 47; i >= 40; i--) {
            builder.append(strs[i]);
        }
        for (int i = 55; i >= 48; i--) {
            builder.append(strs[i]);
        }
        for (int i = 63; i >= 56; i--) {
            builder.append(strs[i]);
        }
        return builder.toString();
    }
}
