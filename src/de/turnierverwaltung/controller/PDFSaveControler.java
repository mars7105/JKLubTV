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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PDFSaveControler {
	private MainControl mainControl;

	public PDFSaveControler(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
	}

	public void savePDFFile() {
		Boolean ready = mainControl.getRundenEingabeFormularControl().checkNewTurnier();

		if (ready) {
			int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();
			String filename = JOptionPane.showInputDialog(null, "Anfangsname der Dateien : ",
					"Eine Eingabeaufforderung", JOptionPane.PLAIN_MESSAGE);
			if (filename != null) {
				JFileChooser savefile = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("PDF", "pdf");
				savefile.addChoosableFileFilter(filter);
				savefile.setFileFilter(filter);
				savefile.setSelectedFile(new File(filename));
				savefile.setDialogType(JFileChooser.SAVE_DIALOG);
				int sf = savefile.showSaveDialog(null);
				if (sf == JFileChooser.APPROVE_OPTION) {

					for (int i = 0; i < anzahlGruppen; i++) {

						int spalte = this.mainControl.getSimpleTableView()[i].getTable().getModel().getColumnCount();
						int zeile = this.mainControl.getSimpleTableView()[i].getTable().getModel().getRowCount();
						for (int x = 0; x < spalte; x++) {
							for (int y = 0; y < zeile; y++) {

								this.mainControl.getTurnierTabelle()[i].getTabellenMatrix()[x][y
										+ 1] = (String) this.mainControl.getSimpleTableView()[i].getTable()
												.getValueAt(y, x);

							}
						}

						PDFTabellenAusgabe mftKreuz = new PDFTabellenAusgabe();
						PDFTabellenAusgabe mftTermin = new PDFTabellenAusgabe();

						if (filename != null) {
							// filename += ".pdf";

							File filename1 = new File(savefile.getCurrentDirectory() + "/" + filename + "_Kreuztabelle_"
									+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".pdf");
							File filename2 = new File(
									savefile.getCurrentDirectory() + "/" + filename + "_Termintabelle_"
											+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".pdf");
							mainControl.getTurnierTabelle()[i].createMatrix();
							String titel = "Kreuztabelle " + mainControl.getTurnier().getTurnierName() + " - "
									+ mainControl.getTurnier().getGruppe()[i].getGruppenName();
							String pathName = filename1.getAbsolutePath();
							mftKreuz.createTurnierPdf(titel, pathName,
									mainControl.getTurnierTabelle()[i].getTabellenMatrix());
							// JOptionPane.showMessageDialog(null, "Datei
							// gespeichert.");

							mainControl.getTerminTabelle()[i].createTerminTabelle();
							titel = "Termintabelle " + mainControl.getTurnier().getTurnierName() + " - "
									+ mainControl.getTurnier().getGruppe()[i].getGruppenName();
							pathName = filename2.getAbsolutePath();
							mftTermin.createTerminPdf(titel, pathName,
									mainControl.getTerminTabelle()[i].getTabellenMatrix());

						}
					}
					JOptionPane.showMessageDialog(null, "Dateien gespeichert.");
					// first check if Desktop is supported by
					// Platform or not
					if (!Desktop.isDesktopSupported()) {
						JOptionPane.showMessageDialog(null, "Desktop is not supported.", "File not opened",
								JOptionPane.INFORMATION_MESSAGE);
					} else {

						Desktop desktop = Desktop.getDesktop();

						try {
							desktop.open(savefile.getCurrentDirectory());
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Desktop is not supported.", "File not opened",
									JOptionPane.INFORMATION_MESSAGE);
						}

					}
				}

			}

		} else {
			JOptionPane.showMessageDialog(null, "Erst nach der Eingabe aller Gruppen\n" + "möglich.");

		}

	}
}
