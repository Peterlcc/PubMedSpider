package com.peter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static String getNumbersFromString(String str) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll("").trim();
	}
}
