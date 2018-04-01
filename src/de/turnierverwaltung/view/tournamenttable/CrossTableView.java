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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.table.CrossTableModel;
import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.TitleLabelView;

public class CrossTableView extends JPanel {

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
	private TitleLabelView statusLabel;

	public CrossTableView(CrossTableModel simpleTableModel, int abstand) {

		// setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));#
		setLayout(new BorderLayout());
		// setBackground(new Color(249, 222, 112));
		table = new JTable(simpleTableModel);

		comboBox = new JComboBox<String>();
		comboBox.addItem(TournamentConstants.KEIN_ERGEBNIS);
		comboBox.addItem(TournamentConstants.VERLUST);
		comboBox.addItem(TournamentConstants.REMIS);
		comboBox.addItem(TournamentConstants.GEWINN);
		comboBox.addItem(TournamentConstants.VERLUST_KAMPFLOS);
		comboBox.addItem(TournamentConstants.GEWINN_KAMPFLOS);
		comboBox.addItem(TournamentConstants.VERLUST_KAMPFLOS_BEIDE);

		setColumnWidth(abstand);
		Color titleColor = new Color((SystemColor.text).getRGB());
		Color titleTextColor = new Color((SystemColor.textText).getRGB());
		setBackground(titleColor);
		setForeground(titleTextColor);
//		this.setBackground(Color.white);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(30);
		JScrollPane sPane = new JScrollPane();
		sPane.setViewportView(table);
		sPane.setBackground(Color.white);

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

	public JLabel getStatusLabel() {
		return statusLabel.getTitleLabel();
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

	public void setStatusLabel(JLabel statusLabel) {

		this.statusLabel.setTitleLabel(statusLabel);
	}

	public void setTable(JTable table) {
		this.table = table;
	}

}