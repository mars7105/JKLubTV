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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.view.MenueView;

public class MenueControl implements ActionListener {
	private MainControl mainControl;
	private MenueView turnierMenue;
	private Turnier turnier;
	private Boolean warnHinweis;
	private String fileName = " ";
	public MenueControl(MainControl mainControl) {
		warnHinweis = false;
		this.mainControl = mainControl;
		turnier = this.mainControl.getTurnier();
		turnierMenue = this.mainControl.getMenueView();
		turnierMenue.setVisible(true);
		turnierMenue.getMntmLaden().addActionListener(this);
		turnierMenue.getMntmBeenden().addActionListener(this);
		turnierMenue.getMntmSpeichern().addActionListener(this);
		turnierMenue.getMntmSpeichern().setEnabled(false);
		turnierMenue.getMntmSpielerLaden().addActionListener(this);
		turnierMenue.getMntmDBNeu().addActionListener(this);
		turnierMenue.getMntmDBLaden().addActionListener(this);
		this.mainControl.getStandardView();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == turnierMenue.getMntmSpielerLaden()) {
			int abfrage = warnHinweis(
					"Wollen Sie wirklich die Seite verlassen? \n" + "Alle eingegebenen Daten gehen verloren.");
			if (abfrage == 0) {
				mainControl.resetApp();
				mainControl.setSpielerEditierenControl(new SpielerLadenControl(mainControl));
				mainControl.getSpielerEditierenControl().makePanel();
			}
		}

		if (arg0.getSource() == turnierMenue.getMntmSpeichern()) {

			for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {

				mainControl.getSaveTurnierControl().saveTurnier(i);

			}

		}
		if (arg0.getSource() == turnierMenue.getMntmLaden()) {
			int abfrage = warnHinweis(
					"Wollen Sie wirklich die Seite verlassen? \n" + "Alle eingegebenen Daten gehen verloren.");
			if (abfrage == 0) {
				mainControl.resetApp();
				mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
				mainControl.getTurnierTableControl().loadTurnierListe();
				mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
				mainControl.getTurnierListeLadenControl().loadTurnier();

			}
		}
		if (arg0.getSource() == turnierMenue.getMntmBeenden()) {

			turnier = this.mainControl.getTurnier();
			if (turnier == null) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			} else {
				// Custom button text
				Object[] options = { "Ja", "Abbrechen" };
				int abfrage = JOptionPane.showOptionDialog(null,
						"Wollen Sie wirklich das Programm beenden? " + "Alle eingegebenen Daten gehen verloren.",
						"A Silly Question", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
						options, options[1]);
				if (abfrage == 0) {
					System.exit(JFrame.EXIT_ON_CLOSE);

				}
			}
		}
		if (arg0.getSource() == turnierMenue.getMntmDBLaden()) {
			int abfrage = warnHinweis(
					"Wollen Sie wirklich die Seite verlassen? \n" + "Alle eingegebenen Daten gehen verloren.");
			if (abfrage == 0) {
				mainControl.resetApp();
				mainControl.datenbankMenueView(false);
				// Create a file chooser
				JFileChooser fc = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("Turnier Datenbank", "ktv");
				fc.addChoosableFileFilter(filter);
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					fileName = file.getPath();
					// This is where a real application would open the file.
					SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
					mainControl.datenbankMenueView(true);
					if (mainControl.getTurnierTableControl() == null) {
						mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
						mainControl.getTurnierTableControl().loadTurnierListe();
						mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
						mainControl.getTurnierListeLadenControl().loadTurnier();
					} else {
						mainControl.resetApp();
						mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
						mainControl.getTurnierTableControl().loadTurnierListe();
						mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
						mainControl.getTurnierListeLadenControl().loadTurnier();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
				}

			}
		}
		if (arg0.getSource() == turnierMenue.getMntmDBNeu()) {
			int abfrage = warnHinweis(
					"Wollen Sie wirklich die Seite verlassen? \n" + "Alle eingegebenen Daten gehen verloren.");
			if (abfrage == 0) {
				mainControl.resetApp();
				mainControl.datenbankMenueView(false);
				String filename = JOptionPane.showInputDialog(null, "Dateiname : ", "Eine Eingabeaufforderung",
						JOptionPane.PLAIN_MESSAGE);
				if (filename != null) {
					filename += ".ktv";
					fileName = filename;

					JFileChooser savefile = new JFileChooser();
					FileFilter filter = new FileNameExtensionFilter("Turnier Datenbank", "ktv");
					savefile.addChoosableFileFilter(filter);
					savefile.setFileFilter(filter);
					savefile.setDialogType(JFileChooser.SAVE_DIALOG);
					savefile.setSelectedFile(new File(filename));
					BufferedWriter writer;
					int sf = savefile.showSaveDialog(null);
					if (sf == JFileChooser.APPROVE_OPTION) {
						try {
							File file = savefile.getSelectedFile();
							writer = new BufferedWriter(new FileWriter(savefile.getSelectedFile()));
							writer.write("");
							writer.close();

							// true for rewrite, false for override
							SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
							SQLiteControl sqlC = new SQLiteControl();
							sqlC.createAllTables();
							mainControl.datenbankMenueView(true);
							JOptionPane.showMessageDialog(null, "Datei wurde gespeichert.", "File Saved",
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

	}

	public void clickedMenueItemNeu() {
		turnier = this.mainControl.getTurnier();
		if (turnier == null) {
			mainControl.setTurnierControl(new TurnierControl(mainControl));
		} else {
			// Custom button text
			Object[] options = { "Ja", "Abbrechen" };
			int abfrage = JOptionPane.showOptionDialog(null,
					"Wollen Sie wirklich ein neues Turnier erstellen? " + "Alle eingegebenen Daten gehen verloren.",
					"A Silly Question", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
					options[1]);
			if (abfrage == 0) {
				mainControl.resetApp();
				mainControl.setTurnierControl(new TurnierControl(mainControl));
			}
		}

	}

	public Boolean getWarnHinweis() {
		return warnHinweis;
	}

	public void setDatenbankMenue(boolean enable) {
		turnierMenue.getMnEditMenu().setEnabled(enable);
	}

	public void setWarnHinweis(Boolean warnHinweis) {
		this.warnHinweis = warnHinweis;
	}

	private int warnHinweis(String hinweisText) {
		int abfrage = 0;
		if (warnHinweis) {
			abfrage = 1;
			// Custom button text
			Object[] options = { "Ja", "Abbrechen" };
			abfrage = JOptionPane.showOptionDialog(null, hinweisText, "Meldung", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		}

		return abfrage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
