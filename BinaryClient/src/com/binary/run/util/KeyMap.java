package com.binary.run.util;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyMap {
	public static Map<String, Integer> keyMap;
	static {
		keyMap = new HashMap<String, Integer>();
		// 设置map的值
		// 数字
		keyMap.put("1", KeyEvent.VK_1);
		keyMap.put("2", KeyEvent.VK_2);
		keyMap.put("3", KeyEvent.VK_3);
		keyMap.put("4", KeyEvent.VK_4);
		keyMap.put("5", KeyEvent.VK_5);
		keyMap.put("6", KeyEvent.VK_6);
		keyMap.put("7", KeyEvent.VK_7);
		keyMap.put("8", KeyEvent.VK_8);
		keyMap.put("9", KeyEvent.VK_9);
		keyMap.put("0", KeyEvent.VK_0);

		// 运算符
		keyMap.put("/", KeyEvent.VK_SLASH);
		keyMap.put("+", KeyEvent.VK_PLUS);
		keyMap.put("-", KeyEvent.VK_MINUS);
		keyMap.put(" ", KeyEvent.VK_SPACE);
		keyMap.put("=", KeyEvent.VK_EQUALS);

		// 特殊字符
		keyMap.put(":", KeyEvent.VK_COLON);
		keyMap.put(",", KeyEvent.VK_COMMA);
		keyMap.put(".", KeyEvent.VK_PERIOD);

		// 字母
		keyMap.put("A", KeyEvent.VK_A);
		keyMap.put("B", KeyEvent.VK_B);
		keyMap.put("C", KeyEvent.VK_C);
		keyMap.put("D", KeyEvent.VK_D);
		keyMap.put("E", KeyEvent.VK_E);
		keyMap.put("F", KeyEvent.VK_F);
		keyMap.put("G", KeyEvent.VK_G);
		keyMap.put("H", KeyEvent.VK_H);
		keyMap.put("I", KeyEvent.VK_I);
		keyMap.put("J", KeyEvent.VK_J);
		keyMap.put("K", KeyEvent.VK_K);
		keyMap.put("L", KeyEvent.VK_L);
		keyMap.put("M", KeyEvent.VK_M);
		keyMap.put("N", KeyEvent.VK_N);
		keyMap.put("O", KeyEvent.VK_O);
		keyMap.put("P", KeyEvent.VK_P);
		keyMap.put("Q", KeyEvent.VK_Q);
		keyMap.put("R", KeyEvent.VK_R);
		keyMap.put("S", KeyEvent.VK_S);
		keyMap.put("T", KeyEvent.VK_T);
		keyMap.put("U", KeyEvent.VK_U);
		keyMap.put("V", KeyEvent.VK_V);
		keyMap.put("W", KeyEvent.VK_W);
		keyMap.put("X", KeyEvent.VK_X);
		keyMap.put("Y", KeyEvent.VK_Y);
		keyMap.put("Z", KeyEvent.VK_Z);
		//
	}
}