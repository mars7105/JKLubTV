package de.turnierverwaltung.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventDate {
	private Date date;
	private SimpleDateFormat englishdateFormatter;
	private SimpleDateFormat germandateFormatter;
	private String englishFormat;
	private String germanFormat;

	public EventDate() {
		super();
		date = null;
		englishFormat = "yyyy/MM/dd";
		germanFormat = "dd.MM.yyyy";
		englishdateFormatter = new SimpleDateFormat(englishFormat, Locale.ENGLISH);
		germandateFormatter = new SimpleDateFormat(germanFormat, Locale.GERMAN);
	}

	public EventDate(final Date date) {
		super();
		this.date = date;
		englishFormat = "yyyy/MM/dd";
		germanFormat = "dd.MM.yyyy";
		englishdateFormatter = new SimpleDateFormat(englishFormat, Locale.ENGLISH);
		germandateFormatter = new SimpleDateFormat(germanFormat, Locale.GERMAN);

	}

	public EventDate(String dateString) {
		super();
		if (dateString == null) {
			dateString = "";
		}
		englishFormat = "yyyy/MM/dd";
		germanFormat = "dd.MM.yyyy";
		englishdateFormatter = new SimpleDateFormat(englishFormat, Locale.ENGLISH);
		germandateFormatter = new SimpleDateFormat(germanFormat);
		if (dateString.contains("/")) {

			try {
				date = englishdateFormatter.parse(dateString);
			} catch (final ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				date = null;
			}

		}
		if (dateString.contains(".")) {

			try {
				date = germandateFormatter.parse(dateString);
			} catch (final ParseException e) {
				e.printStackTrace();
				date = null;
			}
		}

	}

	public Date getDate() {
		return date;
	}

	public String getDateString() {
		if (date != null) {
			if (Locale.getDefault().equals(Locale.US)) {
				final String englishFormat = englishdateFormatter.format(date);
				return englishFormat;
			}
			if (Locale.getDefault().equals(Locale.GERMANY)) {
				final String germanFormat = germandateFormatter.format(date);
				return germanFormat;
			}
		}
		return "";
	}

	public SimpleDateFormat getEnglishdateFormatter() {
		return englishdateFormatter;
	}

	public String getEnglishFormat() {
		return englishFormat;
	}

	public SimpleDateFormat getGermandateFormatter() {
		return germandateFormatter;
	}

	public String getGermanFormat() {
		return germanFormat;
	}

	public java.sql.Date getSQLDate() {
		final java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;

	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public void setDate(final String dateString) {
		Boolean defaultLanguage = false;
		if (dateString.contains("/")) {
			try {
				date = englishdateFormatter.parse(dateString);
				defaultLanguage = true;
			} catch (final ParseException e) {
				defaultLanguage = false;
				e.printStackTrace();
			}
		}
		if (dateString.contains(".")) {
			try {
				date = germandateFormatter.parse(dateString);
				defaultLanguage = true;
			} catch (final ParseException e) {
				defaultLanguage = false;
				e.printStackTrace();
			}
		}
		if (defaultLanguage == false) {

			date = null;

		}

	}

	public void setEnglishdateFormatter(final SimpleDateFormat englishdateFormatter) {
		this.englishdateFormatter = englishdateFormatter;
	}

	public void setEnglishFormat(final String englishFormat) {
		this.englishFormat = englishFormat;
	}

	public void setGermandateFormatter(final SimpleDateFormat germandateFormatter) {
		this.germandateFormatter = germandateFormatter;
	}

	public void setGermanFormat(final String germanFormat) {
		this.germanFormat = germanFormat;
	}
}
