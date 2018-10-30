package de.turnierverwaltung.control;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Version {
	private static final String BUNDLE_COMTROLER_NAME = "de.turnierverwaltung.control.version"; //$NON-NLS-1$

	private static Locale LOCALE = new Locale("en", "US");

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_COMTROLER_NAME, LOCALE);

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static void setLocale(Locale locale) {
		LOCALE = locale;
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_COMTROLER_NAME, LOCALE);
	}

	private Version() {
	}
}
