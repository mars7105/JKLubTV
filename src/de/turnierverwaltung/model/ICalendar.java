package de.turnierverwaltung.model;

import java.io.FileOutputStream;
import java.io.IOException;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

public class ICalendar {
	private Calendar calendar;

	public ICalendar() {
		calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Tournamentmanagement//JKlubTV//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
	}

	public void addEvent(String datum, String event) throws NumberFormatException {
		if (datum.length() > 0) {
			VEvent ev = checkDate(datum, event);
			calendar.getComponents().add(ev);
		}
	}

	public void saveICalender(String filename) throws IOException, ValidationException {

		FileOutputStream fout = new FileOutputStream(filename);

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.setValidating(false);
		outputter.output(calendar, fout);
	}

	private VEvent checkDate(String datum, String event) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		VEvent ev = null;
		Date stStart = null;
		Date stEnd = null;
		if (datum.contains("-")) {

			int day = Integer.parseInt(datum.substring(8, 10));
			int month = Integer.parseInt(datum.substring(5, 7)) - 1;
			int year = Integer.parseInt(datum.substring(0, 4));
			cal.set(java.util.Calendar.YEAR, year);
			cal.set(java.util.Calendar.MONTH, month);
			cal.set(java.util.Calendar.DAY_OF_MONTH, day);
			stStart = new Date(cal.getTime());
			cal.set(java.util.Calendar.DAY_OF_MONTH, day + 1);
			stEnd = new Date(cal.getTime());

		} else {

			int day = Integer.parseInt(datum.substring(0, 2));
			int month = Integer.parseInt(datum.substring(3, 5)) - 1;
			int year = Integer.parseInt(datum.substring(6, 10));
			cal.set(java.util.Calendar.YEAR, year);
			cal.set(java.util.Calendar.MONTH, month);
			cal.set(java.util.Calendar.DAY_OF_MONTH, day);
			stStart = new Date(cal.getTime());
			cal.set(java.util.Calendar.DAY_OF_MONTH, day + 1);
			stEnd = new Date(cal.getTime());
		}
		ev = new VEvent(stStart, stEnd, event);
		return ev;
	}
}
