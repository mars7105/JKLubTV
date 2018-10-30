package de.turnierverwaltung.model;

import java.io.File;
import java.io.IOException;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

public class ICal {
	private ICalendar calendar;

	public ICal() {
		calendar = new ICalendar();

	}

	public void addEvent(String datum, String event) throws NumberFormatException {
		try {
			if (datum.length() > 0) {
				VEvent ev = checkDate(datum, event);

				calendar.addEvent(ev);
			}
		} catch (NumberFormatException e) {

		}
	}

	private VEvent checkDate(String datum, String event) throws NumberFormatException {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		VEvent ev = new VEvent();

		if (datum.contains("-")) {

			int day = Integer.parseInt(datum.substring(8, 10));
			int month = Integer.parseInt(datum.substring(5, 7)) - 1;
			int year = Integer.parseInt(datum.substring(0, 4));
			cal.set(java.util.Calendar.YEAR, year);
			cal.set(java.util.Calendar.MONTH, month);
			cal.set(java.util.Calendar.DAY_OF_MONTH, day);
			ev.setDateStart(cal.getTime(), false);
			cal.set(java.util.Calendar.DAY_OF_MONTH, day + 1);

			ev.setDateEnd(cal.getTime(), false);
			ev.setSummary(event);

		} else {

			int day = Integer.parseInt(datum.substring(0, 2));
			int month = Integer.parseInt(datum.substring(3, 5)) - 1;
			int year = Integer.parseInt(datum.substring(6, 10));
			cal.set(java.util.Calendar.YEAR, year);
			cal.set(java.util.Calendar.MONTH, month);
			cal.set(java.util.Calendar.DAY_OF_MONTH, day);
			ev.setDateStart(cal.getTime(), false);
			cal.set(java.util.Calendar.DAY_OF_MONTH, day + 1);
			ev.setDateEnd(cal.getTime(), false);
			ev.setSummary(event);

		}

		return ev;
	}

	public void saveICalender(String filename) {

		File file = new File(filename);
		try {
			Biweekly.write(calendar).go(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
