package de.turnierverwaltung.view.tournamenttable;
//JKlubTV - Ein Programm zum verwalten von Schach Turnieren

import java.awt.AWTException;

//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;
//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
//import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDateChooserCellEditor;

import de.turnierverwaltung.model.EventDate;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.table.MeetingTableModel;
import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.TitleLabelView;

public class MeetingTableView<M> extends JPanel {
	public class DateLabelFormatter extends AbstractFormatter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String datePattern = "dd.MM.yyy"; //$NON-NLS-1$
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton okButton;
	private JButton saveButton;
	private JButton htmlButton;
	private JTable table;
	private JComboBox<String> comboBox;
	private MeetingTableModel simpleTerminTabelle;
	private Properties property;
	private TitleLabelView statusLabel;
	private JDateChooser dateChooser;

	public MeetingTableView(MeetingTableModel simpleTerminTabelle) {

		this.simpleTerminTabelle = simpleTerminTabelle;
		setLayout(new BorderLayout());

		table = new JTable();

		table.setModel(this.simpleTerminTabelle);
		// table.getModel().re // table.setMinimumSize(new Dimension(500,500));
		// Font fnt = new Font("Arial", Font.PLAIN, 16);
		// table.setFont(fnt);
		comboBox = new JComboBox<String>();
		comboBox.addItem(TournamentConstants.KEIN_ERGEBNIS);
		comboBox.addItem(TournamentConstants.PARTIE_GEWINN_OPPONENT);
		comboBox.addItem(TournamentConstants.PARTIE_REMIS);
		comboBox.addItem(TournamentConstants.PARTIE_GEWINN_PLAYER);
		comboBox.addItem(TournamentConstants.PARTIE_GEWINN_KAMPFLOS_OPPONENT);
		comboBox.addItem(TournamentConstants.PARTIE_GEWINN_KAMPFLOS_PLAYER);
		comboBox.addItem(TournamentConstants.PARTIE_VERLUST_KAMPFLOS_BEIDE);
		property = new Properties();
		property.put("text.today", Messages.getString("SimpleTerminTabelleView.8")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.month", Messages.getString("SimpleTerminTabelleView.10")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.year", Messages.getString("SimpleTerminTabelleView.12")); //$NON-NLS-1$ //$NON-NLS-2$

		setColumnWidth();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.setRowHeight(30);
		JScrollPane sPane = new JScrollPane();
		sPane.setViewportView(table);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());

		JPanel hinweis = new JPanel();
		hinweis.setLayout(new FlowLayout(FlowLayout.LEFT));
		hinweis.add(new JLabel(Messages.getString("SimpleTerminTabelleView.13") //$NON-NLS-1$
				+ Messages.getString("SimpleTerminTabelleView.14"))); //$NON-NLS-1$

		southPanel.add(hinweis, BorderLayout.CENTER);

		JPanel status = new JPanel();
		status.setLayout(new FlowLayout(FlowLayout.LEFT));
		status.add(new JLabel(Messages.getString("SimpleTerminTabelleView.15"))); //$NON-NLS-1$
		statusLabel = new TitleLabelView("0");
		statusLabel.setFlowLayoutLeft();
		statusLabel.setOpaque(true);

		JLabel changesLabel = new JLabel(Messages.getString("SimpleTerminTabelleView.16"));
		status.add(statusLabel);
		status.add(changesLabel);
		southPanel.add(status, BorderLayout.SOUTH);

		add(sPane, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

		this.setVisible(true);
		// System.out.println(table.getX());

	}

	@SuppressWarnings("unused")
	private int[] dateStringToInt(String datum) {
		int[] dateInt = new int[3];

		int index = 0;
		for (String zahlenWerte : datum.split("\\.")) { //$NON-NLS-1$

			dateInt[index] = Integer.parseInt(zahlenWerte);

			index++;

		}
		dateInt[1] = dateInt[1] - 1;
		return dateInt;

	}

	public JButton getHtmlButton() {
		return htmlButton;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JLabel getStatusLabel() {
		return statusLabel.getTitleLabel();
	}

	public JTable getTable() {
		return table;
	}

	public void setColumnWidth() {

		int columnCount = table.getColumnCount();
		dateChooser = new JDateChooser();

		JDateChooserRenderer datePanel = new JDateChooserRenderer(dateChooser);
		datePanel.setLocale(Locale.getDefault());

		JDateChooserCellEditor cellEditor = new JDateChooserCellEditor();

		for (int i = 0; i < columnCount; i++) {
			TableColumn c = table.getColumnModel().getColumn(i);
			if (i == 3) {
				c.setCellEditor(new DefaultCellEditor(comboBox));
			}
			if (i == 4) {

				c.setCellRenderer(datePanel);
				c.setCellEditor(cellEditor);

			}

		}

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		sorter.setRowFilter(RowFilter.regexFilter("\\d", 0));

		table.setRowSorter(sorter);

		this.updateUI();
	}

	public void setHtmlButton(JButton htmlButton) {
		this.htmlButton = htmlButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel.setTitleLabel(statusLabel);

	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JDateChooser getDateChooser() {
		return dateChooser;
	}

	public void setDateChooser(JDateChooser dateChooser) {
		this.dateChooser = dateChooser;
	}

	class JDateChooserRenderer extends JDateChooser implements TableCellRenderer {

		/**
		*
		*/
		private static final long serialVersionUID = 1L;
		// private MeetingTableView<M>.KeyThread keyThread;

		public JDateChooserRenderer(JDateChooser dateChooser) {
			if (dateChooser != null) {
				this.setDate(dateChooser.getDate());
			}
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			if (value instanceof Date) {
				EventDate event = new EventDate();
				event.setDate((Date) value);
				if (this.getDate() == null) {
					this.setDate((Date) value);
				}
				EventDate eventDate = new EventDate();
				eventDate.setDate(this.getDate());
				if (!eventDate.getDateString().equals(event.getDateString())) {

					if (row >= 0 && column == 4) {
						this.setDate((Date) value);
						// final AbstractTableModel model = (AbstractTableModel) table.getModel();
						// TableModelEvent e = new TableModelEvent(model, row, row, column);
						// model.fireTableChanged(e);
						if (table.getSelectedRow() >= 0 && table.getSelectedColumn() == 4) {
							try {
								Robot robot = new Robot();
								// robot.mousePress(dateChooser.getCalendarButton());
								// robot.setAutoWaitForIdle(true);
								robot.keyPress(KeyEvent.VK_F2);
								robot.keyRelease(KeyEvent.VK_F2);
								// robot.waitForIdle();
//								System.out.println("Robot " + row + " " + column + " " + event.getDateString());
							} catch (AWTException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						// model.fireTableRowsUpdated(row, row);
					}
				}
			} else if (value instanceof String) {
				// EventDate event = new EventDate();
				// event.setDate((String) value);
				// this.setDate((Date) event.getDate());
				// if (row >= 0 && column == 4) {
				// final AbstractTableModel model = (AbstractTableModel) table.getModel();
				//
				// TableModelEvent e = new TableModelEvent(model, row, row, column);
				// model.fireTableChanged(e);
				// System.out.println("value instanceof String " + row + " " + column + " " +
				// event.getDateString());
				// }
			}

			return this;
		}

	}

}
