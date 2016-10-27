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
			// TimeZoneRegistry registry =
			// TimeZoneRegistryFactory.getInstance().createRegistry();
			// TimeZone timezone = registry.getTimeZone("Australia/Melbourne");
			java.util.Calendar cal = java.util.Calendar.getInstance();
			// int year = Integer.parseInt(datum.substring(0, 1));

			int day = Integer.parseInt(datum.substring(0, 2));
			int month = Integer.parseInt(datum.substring(3, 5)) - 1;
			int year = Integer.parseInt(datum.substring(6, 10));
			cal.set(java.util.Calendar.YEAR, year);
			cal.set(java.util.Calendar.MONTH, month);
			cal.set(java.util.Calendar.DAY_OF_MONTH, day);
			// DateTime dt = new DateTime(cal.getTime());
			VEvent ev = new VEvent(new Date(cal.getTime()), event);
			// initialise as an all-day event..
//			ev.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE);
			calendar.getComponents().add(ev);
		}
	}

	public void saveICalender(String filename) throws IOException, ValidationException {

		FileOutputStream fout = new FileOutputStream(filename);

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.setValidating(false);
		outputter.output(calendar, fout);
	}
}
