package de.turnierverwaltung.view.tournamenttable;
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

import java.util.Properties;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;

import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.table.MeetingTableModel;
import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.TitleLabelView;

public class MeetingTableView<M> extends JPanel {
//	public class DateLabelFormatter extends AbstractFormatter {
//
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//		private String datePattern = "dd.MM.yyy"; //$NON-NLS-1$
//		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
//
//		@Override
//		public Object stringToValue(String text) throws ParseException {
//			return dateFormatter.parseObject(text);
//		}
//
//		@Override
//		public String valueToString(Object value) throws ParseException {
//			if (value != null) {
//				Calendar cal = (Calendar) value;
//				return dateFormatter.format(cal.getTime());
//			}
//
//			return "";
//		}
//
//	}

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
//	private JDateChooser dateChooser;

	public MeetingTableView(MeetingTableModel simpleTerminTabelle) {

		this.simpleTerminTabelle = simpleTerminTabelle;
		setLayout(new BorderLayout());

		table = new JTable();

		table.setModel(this.simpleTerminTabelle);
		
		table.setLocale(getDefaultLocale());
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

		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.setRowHeight(30);
		setColumnWidth();
//		table.getModel().addTableModelListener(new TableListener());

		JScrollPane sPane = new JScrollPane();
		sPane.setViewportView(table);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());

		JPanel hinweis = new JPanel();
		hinweis.setLayout(new FlowLayout(FlowLayout.LEFT));
		hinweis.add(new JLabel(Messages.getString("SimpleTerminTabelleView.13") //$NON-NLS-1$
				+ Messages.getString("SimpleTerminTabelleView.14"))); //$NON-NLS-1$

		southPanel.add(hinweis, BorderLayout.CENTER);
//		JTextField tf = new JTextField();
//		tf.setVisible(false);
		JPanel status = new JPanel();
		status.setLayout(new FlowLayout(FlowLayout.LEFT));
		status.add(new JLabel(Messages.getString("SimpleTerminTabelleView.15"))); //$NON-NLS-1$
		statusLabel = new TitleLabelView("0");
		statusLabel.setFlowLayoutLeft();
		statusLabel.setOpaque(true);

		JLabel changesLabel = new JLabel(Messages.getString("SimpleTerminTabelleView.16"));
//		status.add(tf);
		status.add(statusLabel);
		status.add(changesLabel);
		southPanel.add(status, BorderLayout.SOUTH);

		add(sPane, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

		this.setVisible(true);

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
		

		for (int i = 0; i < columnCount; i++) {

			TableColumn c = table.getColumnModel().getColumn(i);
			if (i == 3) {
				c.setCellEditor(new DefaultCellEditor(comboBox));
			}
			if (i == 4) {
////				JDateChooserRenderer datePanel = new JDateChooserRenderer();
////
////				JDateChooserCellEditor cellEditor = new JDateChooserCellEditor();
////				
////				c.setCellRenderer(datePanel);
////				c.setCellEditor(cellEditor);
//				
//				c.setLocale(getDefaultLocale());
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

//	public JDateChooser getDateChooser() {
//		return dateChooser;
//	}
//
//	public void setDateChooser(JDateChooser dateChooser) {
//		this.dateChooser = dateChooser;
//	}
	/**
	 * A CellEditor for tables, using a JDateChooser.
	 * 
	 * @author Kai Toedter
	 * @version $LastChangedRevision: 100 $
	 * @version $LastChangedDate: 2006-06-04 14:36:06 +0200 (So, 04 Jun 2006) $
	 */
//	public class JDateChooserCellEditor extends AbstractCellEditor implements TableCellEditor {
//
//		private static final long serialVersionUID = 917881575221755609L;
//
//		private JDateChooser dateChooser;
//
//		public JDateChooserCellEditor(JDateChooser dateChooser) {
//			super();
//			this.dateChooser = dateChooser;
//			dateChooser.setLocale(Locale.getDefault());
//		}
//
//		public JDateChooserCellEditor() {
//			dateChooser = new JDateChooser();
//			dateChooser.setLocale(Locale.getDefault());
//			
//		}
//
//		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
//				int column) {
//
//			Date date = null;
//			if (value instanceof Date)
//				date = (Date) value;
//
//			dateChooser.setDate(date);
//			
//			return dateChooser;
//		}
//
//		public Object getCellEditorValue() {
//			return dateChooser.getDate();
//		}
//	}
//
//	class JDateChooserRenderer implements TableCellRenderer {
//
//		private JDateChooser dateChooser;
//
//		public JDateChooserRenderer(JDateChooser dateChooser) {
//			super();
//			this.dateChooser = dateChooser;
//		}
//
//		public JDateChooserRenderer() {
//			dateChooser = new JDateChooser();
//			dateChooser.setLocale(Locale.getDefault());
//		}
//
//		public boolean isCellEditable(int rowIndex, int columnIndex) {
//			return true;
//		}
//
//		@Override
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
//				int row, int column) {
//
//			dateChooser.setDate((Date) value);
//			
//			try {
//
//				Robot robot = new Robot();
//				robot.keyPress(KeyEvent.VK_F2);
//				robot.keyRelease(KeyEvent.VK_F2);
//			} catch (AWTException e1) {
//				e1.printStackTrace();
//			}
//
//			return dateChooser;
//		}
//
////		@Override
////		public Object getCellEditorValue() {
////			return dateChooser.getDate();
////		}
//
//	}

	
}