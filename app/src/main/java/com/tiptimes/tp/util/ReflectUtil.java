package com.tiptimes.tp.util;

public class ReflectUtil {
	public static String setMethodName(String name) {
		char[] ch = name.toCharArray();
		ch[0] = Character.toUpperCase(ch[0]);

		return "set" + String.valueOf(ch);
	}

	public static String getMethodName(String name) {
		char[] ch = name.toCharArray();
		ch[0] = Character.toUpperCase(ch[0]);

		return "get" + String.valueOf(ch);
	}
}
