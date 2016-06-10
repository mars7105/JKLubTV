package de.turnierverwaltung.model;

import java.util.Locale;

public class Language {
	
	private String language;
	private Locale locale;

	public Language(String language, Locale locale) {
		super();
		this.language = language;
		this.locale = locale;
	}

	public void enableLocale() {
		de.turnierverwaltung.view.Messages.setLocale(locale);
		de.turnierverwaltung.control.Messages.setLocale(locale);
		de.turnierverwaltung.model.Messages.setLocale(locale);
		TurnierKonstanten.setConstantLanguage();

	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
