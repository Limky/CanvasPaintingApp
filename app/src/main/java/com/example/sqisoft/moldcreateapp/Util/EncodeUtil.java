package com.example.sqisoft.moldcreateapp.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodeUtil {
	private static Pattern HANGLE_PATTERN = Pattern.compile("[\\x{ac00}-\\x{d7af}]");

	public static String changeURLString(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}

		Matcher matcher = HANGLE_PATTERN.matcher(input);
		while (matcher.find()) {
			String group = matcher.group();

			try {
//			    input = URLEncoder.encode(input, "UTF-8");
				input = input.replace(group, URLEncoder.encode(group, "UTF-8"));
			} catch (UnsupportedEncodingException ignore) {
			}
		}
		input = input.replaceAll(" ", "%20");

//		try {
//            input = URLEncoder.encode(input, "UTF-8");
//        } catch (UnsupportedEncodingException ignore) {
//        }
		
		return input;
	}
	
	public static String changeStringToBase64(String input) {
	    return new String(Base64.encode(input.getBytes(), Base64.DEFAULT));
	}
	public static String changeBase64ToString(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }
}
