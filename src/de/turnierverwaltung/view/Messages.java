package de.turnierverwaltung.view;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "de.turnierverwaltung.view.messages"; //$NON-NLS-1$

	private static Locale LOCALE = new Locale("en", "US");

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, LOCALE);

	public static Locale getLocale() {
		return RESOURCE_BUNDLE.getLocale();
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static void setLocale(Locale locale) {
		LOCALE = locale;
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, LOCALE);
	}

	private Messages() {
	}
}
