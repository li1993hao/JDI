package com.tiptimes.tp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;

public class SecurityUtil {
    private static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1',
        (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
        (byte) '7', (byte) '8', (byte) '9', (byte) 'A', (byte) 'B',
        (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F' };
	  /**
     * 验证是否为手机号码
     * 
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        // Pattern p =
        // Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1,2,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^([0-9]{3})\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证输入是否为邮箱
     * 
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 验证输入是否6位数字
     * 
     * @param strEmail
     * @return
     */
    public static boolean isCode(String strCode) {
        String strPattern = "^[0-9]{6}";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strCode);
        return m.matches();
    }

    /**
     * 验证输入是否6位字符包含数字和字母，不包含特殊字符
     * 
     * @param strEmail
     * @return
     */
    public static boolean isCheckCode(String strCode) {
        String strPattern = "^[0-9a-zA-Z]{6}";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strCode);
        return m.matches();
    }

    /**
     * 验证输入是否6到12位上字符包含数字和字母，包含特殊字符
     * 
     * @param strEmail
     * @return
     */
    public static boolean isPassCode(String strCode) {
        String strPattern = "^[0-9a-zA-Z@*%#()><!_~]{6,12}";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strCode);
        return m.matches();
    }

    /**
     * 过滤url
     * 
     * @param str
     * @return
     */
    public static boolean isLegalUrlParameters(String str) {
        String strPattern = "[&=\\s]+";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 验证ip
     * 
     * @param text
     * @return
     */
    public static boolean isIp(String text) {
        if (text != null && text != "") {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * md5加密
     * 
     * @param s
     * @return
     * @throws Exception
     */
    public final static String MD5(String s) throws Exception {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };

        byte[] strTemp = s.getBytes();
        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
        mdTemp.update(strTemp);
        byte[] md = mdTemp.digest();
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

    /**
     * 加密
     * 
     * @param sSrc
     *            原文
     * @param sKey
     *            密码是16位
     * @return
     * @throws Exception
     */
    @SuppressLint({ "TrulyRandom", "DefaultLocale" })
	public static String AESEncrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key无效");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return byte2hex(encrypted).toLowerCase();
    }

    /**
     * 解密
     * 
     * @param sSrc
     * @param sKey密码是16位
     * @return
     * @throws Exception
     */
    public static String AESDecrypt(String sSrc, String sKey) throws Exception {
        // 判断Key是否正确
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key无效");
            return null;
        }
        byte[] raw = sKey.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = hex2byte(sSrc);
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;
    }
    /**
     * 十六进制无符号整数形式
     * 
     * @param raw
     * @param len
     * @return
     */
    public static String getHex(byte[] raw, int len) {
        byte[] hex = new byte[2 * len];
        int index = 0;
        int pos = 0;

        for (byte b : raw) {
            if (pos >= len)
                break;

            pos++;
            int v = b & 0xFF;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
        }
        return new String(hex);
    }

    /**
     * 转换为10进制无符号字符串
     * 
     * @param bytes
     * @return
     */
    public static long getDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    /**
     * 二进制转换为16进制
     * 
     * @param b
     * @return
     */
    @SuppressLint("DefaultLocale")
	public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 16进制转换为二进制
     * 
     * @param strhex
     * @return
     */
    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    /**
     * 16进制字符转换为int
     * 
     * @param c
     * @return
     */
    public static int reverseToInt(char c) {
        if (c == 'A')
            return 10;
        else if (c == 'B')
            return 11;
        else if (c == 'C')
            return 12;
        else if (c == 'D')
            return 13;
        else if (c == 'E')
            return 14;
        else if (c == 'F')
            return 15;
        else
            return Integer.parseInt(String.valueOf(c));
    }

    /**
     * 16进制加法
     * 
     * @param src1
     * @param src2
     * @return
     */
	public static String hexAdd(String src1, String src2) {
        int length1 = src1.toUpperCase().length();
        int length2 = src2.toUpperCase().length();
        String extend = "";
        char sum[] = null;
        if (length1 > length2) {// 如果src1的长度大于src2
            int num = length1 - length2;
            sum = new char[length1];
            String zero = "";
            for (int i = 0; i < num; i++) {
                zero += "0";
            }
            src2 = zero + src2;
            // 遍历数组，然后相加
            int add = 0, rest = 0;
            for (int i = length1 - 1; i >= 0; i--) {
                int a = reverseToInt(src1.toUpperCase().charAt(i));
                int b = reverseToInt(src2.toUpperCase().charAt(i));
                if (a + b + add >= 16) {
                    int temp = add;
                    add = (a + b + add) / 16;
                    rest = (a + b + temp) % 16;
                    sum[i] = reverseToChar(rest);
                } else {
                    sum[i] = reverseToChar(a + b + add);
                    add = 0;
                }
            }
        } else if (length1 < length2) {// src1的长度小于src2
            int num = length2 - length1;
            sum = new char[length2];
            String zero = "";
            for (int i = 0; i < num; i++) {
                zero += "0";
            }
            src1 = zero + src1;
            // 遍历数组，然后相加
            int add = 0, rest = 0;
            for (int i = length2 - 1; i >= 0; i--) {
                int a = reverseToInt(src1.toUpperCase().charAt(i));
                int b = reverseToInt(src2.toUpperCase().charAt(i));
                if (a + b + add >= 16) {
                    int temp = add;
                    add = (a + b + add) / 16;
                    rest = (a + b + temp) % 16;
                    sum[i] = reverseToChar(rest);
                } else {
                    sum[i] = reverseToChar(a + b + add);
                    add = 0;
                }
            }
        } else {// 如果相等
            // 遍历数组，然后相加
            sum = new char[length2];
            int add = 0, rest = 0;
            for (int i = length2 - 1; i >= 0; i--) {
                int a = reverseToInt(src1.toUpperCase().charAt(i));
                int b = reverseToInt(src2.toUpperCase().charAt(i));
                if (a + b + add >= 16) {
                    int temp = add;
                    add = (a + b + add) / 16;
                    rest = (a + b + temp) % 16;
                    sum[i] = reverseToChar(rest);
                    if (i == 0) {// 如果i==0
                        extend = String.valueOf(add);
                    }
                } else {
                    sum[i] = reverseToChar(a + b + add);
                    add = 0;
                }
            }
        }
        return extend + String.valueOf(sum);
    }

    /**
     * 整形转为16进制表示的char
     * 
     * @param num
     * @return
     */
    public static char reverseToChar(int num) {
        /*
         * if (num == 10) return 'A'; else if (num == 11) return 'B'; else if
         * (num == 12) return 'C'; else if (num == 13) return 'D'; else if (num
         * == 14) return 'E'; else if (num == 15) return 'F'; else return
         * String.valueOf(num).charAt(0);
         */
        return Integer.toHexString(num).charAt(0);
    }

    /**
     * 16进制字符按位取反
     * 
     * @param num
     * @return
     */
    public static String revers(String num) {
        num = num.toUpperCase();
        char array[] = num.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 'A')
                array[i] = reverseToChar(15 - 10);
            else if (array[i] == 'B')
                array[i] = reverseToChar(15 - 11);
            else if (array[i] == 'C')
                array[i] = reverseToChar(15 - 12);
            else if (array[i] == 'D')
                array[i] = reverseToChar(15 - 13);
            else if (array[i] == 'E')
                array[i] = reverseToChar(15 - 14);
            else if (array[i] == 'F')
                array[i] = reverseToChar(15 - 15);
            else
                array[i] = reverseToChar(15 - Integer.parseInt(String
                        .valueOf(array[i])));
        }
        //
        return String.valueOf(array);
    }

    /**
     * 输入流转换为byte数组
     * 
     * @param is
     * @return
     * @throws java.io.IOException
     */
    public static byte[] streamToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }

    /**
     * 将输入流转为字符串
     * 
     * @param is
     *            输入流
     * @param encode
     *            编码方式
     * @return
     * @throws Exception
     */
    public static String streamToString(InputStream is, String encode)
            throws Exception {

        byte[] data = streamToBytes(is);
        return new String(data, encode);
    }

    /**
     * 将String转换成InputStream
     * 
     * @param in
     * @return
     * @throws java.io.UnsupportedEncodingException
     * @throws Exception
     */
    public static InputStream StringTOInputStream(String in, String encode)
            throws IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes(encode));
        return is;
    }

    /**
     * 将byte数组转换为输入流
     * 
     * @param data
     * @return
     */
    public static InputStream ByteToStream(byte[] data) {
        ByteArrayInputStream os = new ByteArrayInputStream(data);
        return os;
    }
}
