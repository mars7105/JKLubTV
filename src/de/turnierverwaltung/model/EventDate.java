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

	public EventDate(Date date) {
		super();
		this.date = date;
		englishFormat = "yyyy/MM/dd";
		germanFormat = "dd.MM.yyyy";
		englishdateFormatter = new SimpleDateFormat(englishFormat, Locale.ENGLISH);
		germandateFormatter = new SimpleDateFormat(germanFormat, Locale.GERMAN);
		
		System.out.println(getDateString());
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
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					date = null;
				}
			
		}
		if (dateString.contains(".")) {

			try {
				date = germandateFormatter.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
				date = null;
			}
		}

	}

	public String getDateString() {
		if (date != null) {
			if (Locale.getDefault().equals(Locale.US)) {
				String englishFormat = englishdateFormatter.format(date);
				return englishFormat;
			}
			if (Locale.getDefault().equals(Locale.GERMANY)) {
				String germanFormat = germandateFormatter.format(date);
				return germanFormat;
			}
		}
		return "";
	}

	public java.sql.Date getSQLDate() {
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SimpleDateFormat getEnglishdateFormatter() {
		return englishdateFormatter;
	}

	public void setEnglishdateFormatter(SimpleDateFormat englishdateFormatter) {
		this.englishdateFormatter = englishdateFormatter;
	}

	public SimpleDateFormat getGermandateFormatter() {
		return germandateFormatter;
	}

	public void setGermandateFormatter(SimpleDateFormat germandateFormatter) {
		this.germandateFormatter = germandateFormatter;
	}

	public String getEnglishFormat() {
		return englishFormat;
	}

	public void setEnglishFormat(String englishFormat) {
		this.englishFormat = englishFormat;
	}

	public String getGermanFormat() {
		return germanFormat;
	}

	public void setGermanFormat(String germanFormat) {
		this.germanFormat = germanFormat;
	}

	public void setDate(String dateString) {
		Boolean defaultLanguage = false;
		if (dateString.contains("/")) {
			try {
				date = englishdateFormatter.parse(dateString);
				defaultLanguage = true;
			} catch (ParseException e) {
				defaultLanguage = false;
				e.printStackTrace();
			}
		}
		if (dateString.contains(".")) {
			try {
				date = germandateFormatter.parse(dateString);
				defaultLanguage = true;
			} catch (ParseException e) {
				defaultLanguage = false;
				e.printStackTrace();
			}
		}
		if (defaultLanguage == false) {
			
				date = null;
			
		}

	}
}
