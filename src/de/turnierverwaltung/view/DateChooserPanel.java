/*
 *  DateChooserPanel.java  - A panel to demo several JDateChooser configurations.
 *  Copyright (C) 2006 Kai Toedter
 *  kai@toedter.com
 *  www.toedter.com
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package de.turnierverwaltung.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

/**
 * A demonstration panel including several JDateChoosers.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 153 $
 * @version $LastChangedDate: 2011-06-09 16:49:22 +0200 (Do, 09 Jun 2011) $
 */
public class DateChooserPanel extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = -1282280858252793253L;
	private final JDateChooser components;

	public DateChooserPanel() {
		setName("JDateChooser");

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		setLayout(gridbag);

		components = new JDateChooser();

		components.getJCalendar().setTodayButtonVisible(true);
		components.getJCalendar().setNullDateButtonVisible(true);
		add(components);

	}

	/**
	 * Returns the date. If the JDateChooser is started with an empty date and no
	 * date is set by the user, null is returned.
	 * 
	 * @return the current date
	 */
	public Date getDate() {
		return components.getDate();
	}

	/**
	 * Gets the date format string.
	 * 
	 * @return Returns the dateFormatString.
	 */
	public String getDateFormatString() {
		return components.getDateFormatString();
	}

	public JDateChooser getJDateChooser() {
		return components;
	}

	/**
	 * Returns the locale of the first JDateChooser.
	 */
	@Override
	public Locale getLocale() {
		return components.getLocale();
	}

	public Date getMaxSelectableDate() {
		return components.getMaxSelectableDate();
	}

	public Date getMinSelectableDate() {
		return components.getMinSelectableDate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return components.isEnabled();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("date")) {
			setDate((Date) evt.getNewValue());
		}
	}

	/**
	 * Sets the date. Fires the property change "date" if date != null.
	 * 
	 * @param date
	 *            the new date.
	 */
	public void setDate(Date date) {
		components.setDate(date);

	}

	/**
	 * Sets the date format string. E.g "MMMMM d, yyyy" will result in "July 21,
	 * 2004" if this is the selected date and locale is English.
	 * 
	 * @param dfString
	 *            The dateFormatString to set.
	 */
	public void setDateFormatString(String dfString) {
		components.setDateFormatString(dfString);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		components.setEnabled(enabled);

	}

	/**
	 * Sets the locale of the first 4 JDateChoosers.
	 */
	@Override
	public void setLocale(Locale locale) {

		components.setLocale(locale);

	}

	public void setMaxSelectableDate(Date date) {
		components.setMaxSelectableDate(date);

	}

	public void setMinSelectableDate(Date date) {
		components.setMinSelectableDate(date);

	}

}
