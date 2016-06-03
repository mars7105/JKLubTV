package de.turnierverwaltung.controller;

import java.util.Locale;

import de.turnierverwaltung.model.Language;

public class LanguagePropertiesControl {
	private MainControl mainControl;
	private PropertiesControl propertiesControl;
	public static final String LANGUAGE = "language";
	public static final String ENGLISH = "english";
	public static final String GERMAN = "german";
	private Language englishLanguage;
	private Language germanLanguage;

	public LanguagePropertiesControl(MainControl mainControl) {
		this.mainControl = mainControl;
		propertiesControl = this.mainControl.getPropertiesControl();
		germanLanguage = new Language(GERMAN, new Locale("de", "DE"));
		englishLanguage = new Language(ENGLISH, new Locale("en", "US"));
		setLanguageToDefault();
	}

	public void setLanguageToEnglish() {
		propertiesControl.setLanguage(englishLanguage.getLanguage());
		englishLanguage.setLocaleToDefault();

	}

	public void setLanguageToGerman() {
		propertiesControl.setLanguage(germanLanguage.getLanguage());
		germanLanguage.setLocaleToDefault();

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