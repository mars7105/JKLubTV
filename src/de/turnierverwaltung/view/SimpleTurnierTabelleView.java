package de.turnierverwaltung.view;
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

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import de.turnierverwaltung.model.SimpleTurnierTabelle;
import de.turnierverwaltung.model.TurnierKonstanten;

public class SimpleTurnierTabelleView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int small = 50;
	private JButton okButton;
	private JButton saveButton;
	private JButton htmlButton;
	private JTable table;
	private JComboBox<String> comboBox;

	public SimpleTurnierTabelleView(SimpleTurnierTabelle simpleTableModel, int abstand) {

		// setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));#
		setLayout(new BorderLayout());
		// setBackground(new Color(249, 222, 112));
		table = new JTable(simpleTableModel);
		// table.setPreferredSize(new Dimension(300, 300));
		// table.setMinimumSize(new Dimension(200, 200));
		// Font fnt = new Font("Arial", Font.PLAIN, 16);
		// table.setFont(fnt);

		comboBox = new JComboBox<String>();
		comboBox.addItem(" "); //$NON-NLS-1$
		comboBox.addItem("0"); //$NON-NLS-1$
		comboBox.addItem(TurnierKonstanten.REMIS);
		comboBox.addItem("1"); //$NON-NLS-1$
		comboBox.addItem("-"); //$NON-NLS-1$
		comboBox.addItem("+"); //$NON-NLS-1$
		setColumnWidth(abstand);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(30);
		JScrollPane sPane = new JScrollPane();
		sPane.setViewportView(table);
		
		JPanel tabelPanel = new JPanel();
		tabelPanel.setLayout(new BorderLayout());
		tabelPanel.add(sPane, BorderLayout.NORTH);
		JPanel hinweis = new JPanel();
		hinweis.add(new JLabel(Messages.getString("SimpleTerminTabelleView.13") //$NON-NLS-1$
				+ Messages.getString("SimpleTerminTabelleView.14"))); //$NON-NLS-1$
		tabelPanel.add(hinweis, BorderLayout.SOUTH);
		JScrollPane allScrollPane = new JScrollPane();
		allScrollPane.setViewportView(tabelPanel);

		add(allScrollPane, BorderLayout.NORTH);

		this.setVisible(true);

	}

	public JComboBox<String> getComboBox() {
		return comboBox;
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

	public JTable getTable() {
		return table;
	}

	private void setColumnWidth(int abstand) {
		int columnCount = table.getColumnCount();
		int punkte = columnCount - 3;
		for (int i = 0; i < columnCount; i++) {
			TableColumn c = table.getColumnModel().getColumn(i);
			if (i < 1) {
				// c.setPreferredWidth(big);
			}
			if (i >= 1 && i < 4) {
				// c.setPreferredWidth(medium);
			}
			// if (i == 1) {
			// c.setMinWidth(0);
			// c.setMaxWidth(0);
			// c.setPreferredWidth(0);
			// }
			if (i >= abstand && i < punkte) {
				c.setCellEditor(new DefaultCellEditor(comboBox));
				c.setPreferredWidth(small);
			}
			if (i >= punkte) {
				// c.setPreferredWidth(medium);
			}

		}

	}

	public void setComboBox(JComboBox<String> comboBox) {
		this.comboBox = comboBox;
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

	public void setTable(JTable table) {
		this.table = table;
	}

}