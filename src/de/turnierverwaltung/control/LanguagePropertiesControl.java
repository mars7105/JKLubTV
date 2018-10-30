package de.turnierverwaltung.control;

import java.awt.Toolkit;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import de.turnierverwaltung.model.Language;

/**
 * 
 * @author mars
 *
 */
public class LanguagePropertiesControl {
	public static final String LANGUAGE = "language";
	public static final String ENGLISH = "english";
	public static final String GERMAN = "german";
	private MainControl mainControl;
	private PropertiesControl propertiesControl;
	private Language englishLanguage;
	private Language germanLanguage;
	private Language defaultLanguage;
	private ImageIcon icon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-notice.png")));

	/**
	 * 
	 * @param mainControl
	 */
	public LanguagePropertiesControl(MainControl mainControl) {
		this.mainControl = mainControl;
		propertiesControl = this.mainControl.getPropertiesControl();
		germanLanguage = new Language(GERMAN, new Locale("de", "DE"));
		englishLanguage = new Language(ENGLISH, new Locale("en", "US"));
	}

	private void abfrage() {
		Object[] possibilities = { "English", "German" };
		String s = (String) JOptionPane.showInputDialog(mainControl, "Which Language?\n", "Language Support",
				JOptionPane.PLAIN_MESSAGE, icon, possibilities, "ham");

		defaultLanguage = englishLanguage;
		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			if (s.equals("English")) {
				defaultLanguage = englishLanguage;

			}
			if (s.equals("German")) {
				defaultLanguage = germanLanguage;

			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public Boolean checkLanguage() {
		Boolean ok = false;
		String language = propertiesControl.getLanguage();
		if (language == null) {
			setLanguageToDefault();
			ok = true;
		}
		if (language.equals(GERMAN)) {
			setLanguageToGerman();
			ok = true;
		}
		if (language.equals(ENGLISH)) {
			setLanguageToEnglish();
			ok = true;
		}
		if (ok == false) {
			abfrage();
			setLanguageToDefault();
		}
		return ok;
	}

	/**
	 * 
	 */
	public void setLanguageToDefault() {
		propertiesControl.setLanguage(defaultLanguage.getLanguage());
		defaultLanguage.enableLocale();
	}

	/**
	 * 
	 */
	public void setLanguageToEnglish() {
		propertiesControl.setLanguage(englishLanguage.getLanguage());
		englishLanguage.enableLocale();

	}

	/**
	 * 
	 */
	public void setLanguageToGerman() {
		propertiesControl.setLanguage(germanLanguage.getLanguage());
		germanLanguage.enableLocale();

	}
}