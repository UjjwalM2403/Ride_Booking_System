package com.abes.util;

import java.util.regex.Pattern;

public class ValidationUtil {
	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	private static final String PHONE_PATTERN = "^[0-9]{10}$";

	private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
	private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

	public static boolean isValidEmail(String email) {
		return email != null && emailPattern.matcher(email).matches();
	}

	public static boolean isValidPhone(String phone) {
		return phone != null && phonePattern.matcher(phone).matches();
	}

	public static boolean isValidPassword(String password) {
		return password != null && password.length() >= Constants.MIN_PASSWORD_LENGTH;
	}

	public static boolean isNotEmpty(String str) {
		return str != null && !str.trim().isEmpty();
	}

	public static boolean isValidRating(int rating) {
		return rating >= 1 && rating <= 5;
	}
}
