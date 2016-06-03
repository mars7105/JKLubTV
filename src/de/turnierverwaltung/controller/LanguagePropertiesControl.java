package de.turnierverwaltung.controller;

import java.util.Locale;

public class LanguagePropertiesControl {
	private MainControl mainControl;
	private PropertiesControl propertiesControl;
	public static final String LANGUAGE = "language";
	public static final String ENGLISH = "english";
	public static final String GERMAN = "german";

	public LanguagePropertiesControl(MainControl mainControl) {
		this.mainControl = mainControl;
		propertiesControl = this.mainControl.getPropertiesControl();
	}

	public void setLanguageToEnglish() {
		propertiesControl.setLanguage(ENGLISH);
		de.turnierverwaltung.view.Messages.setLocale(new Locale("en", "US"));
		de.turnierverwaltung.controller.Messages.setLocale(new Locale("en",
				"US"));
		de.turnierverwaltung.model.Messages.setLocale(new Locale("en", "US"));

	}

	public void setLanguageToGerman() {
		propertiesControl.setLanguage(GERMAN);
		de.turnierverwaltung.view.Messages.setLocale(new Locale("de", "DE"));
		de.turnierverwaltung.controller.Messages.setLocale(new Locale("de",
				"DE"));
		de.turnierverwaltung.model.Messages.setLocale(new Locale("de", "DE"));

	}

	public void setLanguageToDefault() {
		setLanguageToEnglish();
	}

	public Boolean checkLanguage() {
		Boolean ok = false;
		String language = propertiesControl.getLanguage();
		if (language.equals(GERMAN)) {
			setLanguageToGerman();
			ok = true;
		}
		if (language.equals(ENGLISH)) {
			setLanguageToEnglish();
			ok = true;
		}
		if (ok == false) {
			setLanguageToDefault();
		}
		return ok;
	}
}