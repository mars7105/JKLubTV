package de.turnierverwaltung.controller;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.model.SimpleTerminTabelle;
import de.turnierverwaltung.model.TerminTabelle;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.view.HTMLTabelleView;
import de.turnierverwaltung.view.SimpleTerminTabelleView;
import de.turnierverwaltung.view.SimpleTurnierTabelleView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class TerminTabelleControl implements ActionListener {

	private MainControl mainControl;
	private TerminTabelle[] terminTabelle;
	private TabAnzeigeView[] tabAnzeigeView2;
	private SimpleTerminTabelleView[] simpleTableView;
	private SimpleTurnierTabelleView[] simpleTurnierTabelleView;
	private JPanel hauptPanel;
	private Turnier turnier;
	private MyTableModelListener tml[];
	private HTMLTabelleView htmlTabelleView;

	private SaveTurnierControl saveTurnierControl;
	private int aktiveGruppe;

	public TerminTabelleControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.turnier = mainControl.getTurnier();
		this.tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		int anzahlGruppen = mainControl.getTurnier().getAnzahlGruppen();
		simpleTableView = new SimpleTerminTabelleView[anzahlGruppen];
		this.terminTabelle = new TerminTabelle[anzahlGruppen];
		hauptPanel = mainControl.getHauptPanel();
		tml = new MyTableModelListener[anzahlGruppen];
		htmlTabelleView = new HTMLTabelleView();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (int i = 0; i < turnier.getAnzahlGruppen(); i++) {
			if (simpleTableView[i] != null) {
				if (arg0.getSource() == simpleTableView[i].getOkButton()) {
					mainControl.getTurnierTabelleControl().okAction(i);
				}
				if (arg0.getSource() == simpleTableView[i].getHtmlButton()) {
					aktiveGruppe = i;
					int spalte = simpleTableView[i].getTable().getModel()
							.getColumnCount();
					int zeile = simpleTableView[i].getTable().getModel()
							.getRowCount();
					for (int x = 0; x < spalte; x++) {
						for (int y = 0; y < zeile; y++) {

							terminTabelle[i].getTabellenMatrix()[x][y + 1] = (String) simpleTableView[i]
									.getTable().getValueAt(y, x);

						}
					}

					htmlTabelleView.makeHTMLFrame(
							terminTabelle[i].getHTMLTable(),
							"Termintabelle Gruppe "
									+ turnier.getGruppe()[i].getGruppenName());
					htmlTabelleView.getCloseButton().addActionListener(this);
					htmlTabelleView.getSaveButton().addActionListener(this);
				}
				if (arg0.getSource() == simpleTableView[i].getSaveButton()) {
					saveTurnierControl = mainControl.getSaveTurnierControl();
					saveTurnierControl.saveTurnier(i);
				}

			}
		}
		if (arg0.getSource() == htmlTabelleView.getCloseButton()) {
			htmlTabelleView.closeWindow();
		}
		if (arg0.getSource() == htmlTabelleView.getSaveButton()) {

			String filename = JOptionPane.showInputDialog(null, "Dateiname : ",
					"Eine Eingabeaufforderung", JOptionPane.PLAIN_MESSAGE);
			if (filename != null) {
				filename += ".html";
				FileFilter filter = new FileNameExtensionFilter("HTML", "html");
				JFileChooser savefile = new JFileChooser();
				savefile.addChoosableFileFilter(filter);
				savefile.setFileFilter(filter);
				savefile.setDialogType(JFileChooser.SAVE_DIALOG);
				savefile.setSelectedFile(new File(filename));
				BufferedWriter writer;
				int sf = savefile.showSaveDialog(null);
				if (sf == JFileChooser.APPROVE_OPTION) {
					try {
						writer = new BufferedWriter(new FileWriter(
								savefile.getSelectedFile()));
						writer.write(terminTabelle[aktiveGruppe].getHTMLTable());
						writer.close();
						JOptionPane.showMessageDialog(null,
								"Datei wurde gespeichert.", "File Saved",
								JOptionPane.INFORMATION_MESSAGE);

					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (sf == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
				}

			}

		}

	}

	public void makeSimpleTableView(int gruppenNummer) {

		this.terminTabelle[gruppenNummer] = new TerminTabelle(turnier,
				mainControl.getTurnier().getGruppe()[gruppenNummer]);
		this.terminTabelle[gruppenNummer].createMatrix();
		simpleTableView[gruppenNummer] = new SimpleTerminTabelleView(
				new SimpleTerminTabelle(this.terminTabelle[gruppenNummer]));
		simpleTurnierTabelleView = mainControl.getSimpleTableView();
		mainControl.getTurnierTabelle();
		if (tml[gruppenNummer] == null) {
			tml[gruppenNummer] = new MyTableModelListener(gruppenNummer);
		}

		simpleTableView[gruppenNummer].getTable().getModel()
				.addTableModelListener(tml[gruppenNummer]);
		simpleTableView[gruppenNummer].getOkButton().addActionListener(this);
		simpleTableView[gruppenNummer].getSaveButton().addActionListener(this);
		simpleTableView[gruppenNummer].getHtmlButton().addActionListener(this);

		if (tabAnzeigeView2[gruppenNummer].getTabCount() == 1) {
			tabAnzeigeView2[gruppenNummer].insertTab("Termintabelle", null,
					simpleTableView[gruppenNummer], null, 1);
		} else {

			tabAnzeigeView2[gruppenNummer].setComponentAt(1,
					simpleTableView[gruppenNummer]);
		}

		hauptPanel.updateUI();
	}

	class MyTableModelListener implements TableModelListener {
		private int gruppenNummer;

		public MyTableModelListener(int gruppenNummer) {
			this.gruppenNummer = gruppenNummer;
		}

		@Override
		public void tableChanged(TableModelEvent e) {

			int row = e.getFirstRow();
			int col = e.getColumn();
			if (col == 3) {
				String ergebniss = (String) simpleTableView[gruppenNummer]
						.getTable().getModel().getValueAt(row, col);
				String spielerWeiss = (String) simpleTableView[gruppenNummer]
						.getTable().getModel().getValueAt(row, 1);
				String spielerSchwarz = (String) simpleTableView[gruppenNummer]
						.getTable().getModel().getValueAt(row, 2);

				String ergebnissSet = TurnierKonstanten.KEIN_ERGEBNIS;

				if (ergebniss == TurnierKonstanten.KEIN_ERGEBNIS) {
					ergebnissSet = TurnierKonstanten.KEIN_ERGEBNIS;
				}
				if (ergebniss == TurnierKonstanten.PARTIE_GEWINN_SCHWARZ) {
					ergebnissSet = TurnierKonstanten.VERLUST;
				}
				if (ergebniss == TurnierKonstanten.PARTIE_REMIS) {
					ergebnissSet = TurnierKonstanten.REMIS;
				}
				if (ergebniss == TurnierKonstanten.PARTIE_GEWINN_WEISS) {
					ergebnissSet = TurnierKonstanten.GEWINN;
				}
				if (ergebniss == TurnierKonstanten.PARTIE_GEWINN_KAMPFLOS_SCHWARZ) {
					ergebnissSet = TurnierKonstanten.VERLUST_KAMPFLOS;
				}
				if (ergebniss == TurnierKonstanten.PARTIE_GEWINN_KAMPFLOS_WEISS) {
					ergebnissSet = TurnierKonstanten.GEWINN_KAMPFLOS;
				}
				for (int i = 0; i < simpleTurnierTabelleView[gruppenNummer]
						.getTable().getModel().getRowCount(); i++) {
					if (simpleTurnierTabelleView[gruppenNummer].getTable()
							.getModel().getValueAt(i, 0) == spielerWeiss) {
						for (int y = 0; y < simpleTurnierTabelleView[gruppenNummer]
								.getTable().getModel().getRowCount(); y++) {
							if (simpleTurnierTabelleView[gruppenNummer]
									.getTable().getModel().getValueAt(y, 0) == spielerSchwarz) {
								simpleTurnierTabelleView[gruppenNummer]
										.getTable().getModel()
										.setValueAt(ergebnissSet, i, y + 4);
							}
						}

					}
				}

			}
		}

	}
}
