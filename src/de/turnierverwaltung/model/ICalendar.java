package de.turnierverwaltung.model;

import java.io.FileOutputStream;
import java.io.IOException;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
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

	public void addEvent(String datum, String event) {
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		TimeZone timezone = registry.getTimeZone("Australia/Melbourne");
		java.util.Calendar cal = java.util.Calendar.getInstance(timezone);
		cal.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
		cal.set(java.util.Calendar.DAY_OF_MONTH, 25);
		DateTime dt = new DateTime(cal.getTime());
		VEvent ev = new VEvent(dt, event);
		// initialise as an all-day event..
		ev.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE);
	}

	public void saveICalender(String filename) throws IOException, ValidationException {
		FileOutputStream fout = new FileOutputStream(filename);

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.output(calendar, fout);
	}
}
