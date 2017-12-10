package com.onlinebanking.common;

import java.util.Random;

public class TokenGenerator {
	public static String getPassword(int length) {
		String chars = "qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();

		while (salt.length() < length) { // length of the random string.
			int index = (int) (rnd.nextFloat() * chars.length());
			salt.append(chars.charAt(index));
		}

		return salt.toString();
	}
}
