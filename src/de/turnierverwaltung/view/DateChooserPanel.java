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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.demo.TestDateEvaluator;

/**
 * A demonstration panel including several JDateChoosers.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 153 $
 * @version $LastChangedDate: 2011-06-09 16:49:22 +0200 (Do, 09 Jun 2011) $
 */
public class DateChooserPanel extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = -1282280858252793253L;
	private  final JDateChooser components;

	public DateChooserPanel() {
		setName("JDateChooser");

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		setLayout(gridbag);

		components = new JDateChooser();
		
		
		components.getJCalendar().getDayChooser()
				.addDateEvaluator(new TestDateEvaluator());
		components.getJCalendar().setTodayButtonVisible(
				true);
		components.getJCalendar().setNullDateButtonVisible(
				true);

		

		addEntry("Default", components, gridbag);
		
	}

	private void addEntry(String text, JComponent component, GridBagLayout grid) {
		JLabel label = new JLabel(text + ": ", null, JLabel.RIGHT);
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		grid.setConstraints(label, c);
		add(label);
		c.gridwidth = GridBagConstraints.REMAINDER;
		grid.setConstraints(component, c);
		add(component);
		JPanel blankLine = new JPanel() {
			private static final long serialVersionUID = 4514530330521503732L;

			public Dimension getPreferredSize() {
				return new Dimension(10, 3);
			}
		};
		grid.setConstraints(blankLine, c);
		add(blankLine);
	}

	/**
	 * Gets the date format string.
	 * 
	 * @return Returns the dateFormatString.
	 */
	public String getDateFormatString() {
		return  components.getDateFormatString();
	}

	/**
	 * Sets the date format string. E.g "MMMMM d, yyyy" will result in "July 21,
	 * 2004" if this is the selected date and locale is English.
	 * 
	 * @param dfString
	 *            The dateFormatString to set.
	 */
	public void setDateFormatString(String dfString) {
		for (int i = 0; i < 4; i++) {
			components.setDateFormatString(dfString);
		}
	}

	/**
	 * Returns the date. If the JDateChooser is started with an empty date and
	 * no date is set by the user, null is returned.
	 * 
	 * @return the current date
	 */
	public Date getDate() {
		return components.getDate();
	}

	/**
	 * Sets the date. Fires the property change "date" if date != null.
	 * 
	 * @param date
	 *            the new date.
	 */
	public void setDate(Date date) {
		for (int i = 0; i < 4; i++) {
			components.setDate(date);
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("date")) {
			setDate((Date) evt.getNewValue());
		}
	}

	/**
	 * Returns the locale of the first JDateChooser.
	 */
	public Locale getLocale() {
		return components.getLocale();
	}

	/**
	 * Sets the locale of the first 4 JDateChoosers.
	 */
	public void setLocale(Locale locale) {
		for (int i = 0; i < 5; i++) {
			components.setLocale(locale);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#isEnabled()
	 */
	public boolean isEnabled() {
		return components.isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		for (int i = 0; i < 5; i++) {
			components.setEnabled(enabled);
		}
	}

	public Date getMinSelectableDate() {
		return  components.getMinSelectableDate();
	}

	public void setMinSelectableDate(Date date) {
		for (int i = 0; i < 4; i++) {
			components.setMinSelectableDate(date);
		}
	}

	public Date getMaxSelectableDate() {
		return components.getMaxSelectableDate();
	}

	public void setMaxSelectableDate(Date date) {
		for (int i = 0; i < 4; i++) {
			components.setMaxSelectableDate(date);
		}
	}

	public JDateChooser getJDateChooser() {
		return  components;
	}
	
}