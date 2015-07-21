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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
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
	private final int small = 60;
	private final int big = 130;
	private JButton okButton;
	private JButton saveButton;
	private JButton htmlButton;
	private JTable table;
	private JComboBox<String> comboBox;

	public SimpleTurnierTabelleView(SimpleTurnierTabelle simpleTableModel) {
		int windowWidth = TurnierKonstanten.WINDOW_WIDTH;
		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 200;
		setPreferredSize(new Dimension(windowWidth, windowHeight));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(249, 222, 112));
		table = new JTable(simpleTableModel);
		Font fnt = new Font("Arial", Font.PLAIN, 16);
		table.setFont(fnt);

		comboBox = new JComboBox<String>();
		comboBox.addItem(" ");
		comboBox.addItem("0");
		comboBox.addItem("0,5");
		comboBox.addItem("1");
		comboBox.addItem("-");
		comboBox.addItem("+");
		setColumnWidth();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane sPane = new JScrollPane();
		sPane.setViewportView(table);
		JPanel haupt = new JPanel();
		haupt.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel tabelPanel = new JPanel();
		tabelPanel.setPreferredSize(new Dimension(windowWidth - 100, windowHeight - 100));
		tabelPanel.setLayout(new BoxLayout(tabelPanel, BoxLayout.PAGE_AXIS));
		tabelPanel.add(sPane);
		JPanel hinweis = new JPanel();
		hinweis.add(new JLabel(
				"Geben Sie die Ergebnisse direkt in die Zellen ein " + "und klicken Sie dann auf \"Aktualisieren\"."));
		tabelPanel.add(hinweis);
		haupt.add(tabelPanel);
		add(haupt);
		JPanel buttonLeiste = new JPanel();
		buttonLeiste.setLayout(new FlowLayout(FlowLayout.LEFT));
		okButton = new JButton("Aktualisieren");
		buttonLeiste.add(okButton);
		saveButton = new JButton("Speichern");
		buttonLeiste.add(saveButton);
		htmlButton = new JButton("HTML Ausgabe");
		buttonLeiste.add(htmlButton);
		add(buttonLeiste);

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

	private void setCellEditor() {
		int columnCount = table.getColumnCount();
		int rowCount = table.getRowCount();
		int punkte = columnCount - 3;

		Object temp;
		for (int x = 0; x < columnCount; x++) {
			for (int y = 0; y < rowCount; y++) {
				TableColumn c = table.getColumnModel().getColumn(x);
				if (x >= 4 && x < punkte ) {
					temp = table.getModel().getValueAt(y, x);

					c.setCellEditor(new DefaultCellEditor(comboBox));

				}
			}
		}
	}

	private void setColumnWidth() {
		int columnCount = table.getColumnCount();
		int rowCount = table.getRowCount();
		int punkte = columnCount - 3;
		int soberg = columnCount - 2;
		int platz = columnCount - 1;
		for (int i = 0; i < columnCount; i++) {
			TableColumn c = table.getColumnModel().getColumn(i);
			if (i < 4) {
				c.setPreferredWidth(big);
			}
			if (i >= 4 && i < punkte) {
				c.setCellEditor(new DefaultCellEditor(comboBox));
				// c.setPreferredWidth(small);
			}
			if (i >= punkte) {
				// c.setPreferredWidth(big);
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