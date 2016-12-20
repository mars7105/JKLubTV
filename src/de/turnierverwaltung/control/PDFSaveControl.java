package de.turnierverwaltung.control;

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

import de.turnierverwaltung.model.roundrobin.CrossTable;

public class PDFSaveControl {
	private MainControl mainControl;

	public PDFSaveControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
	}

	public void savePDFFile() {
		Boolean ready = mainControl.getRundenEingabeFormularControl().checkNewTurnier();

		if (ready) {
			int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();

			String filename = mainControl.getTurnier().getTurnierName();
			File path = new File(mainControl.getPropertiesControl().getDefaultPath());

			JFileChooser savefile = new JFileChooser(path);
			FileFilter filter = new FileNameExtensionFilter("PDF", "pdf"); //$NON-NLS-1$ //$NON-NLS-2$
			savefile.addChoosableFileFilter(filter);
			savefile.setFileFilter(filter);
			savefile.setSelectedFile(new File(filename));
			savefile.setDialogType(JFileChooser.SAVE_DIALOG);
			int sf = savefile.showSaveDialog(null);
			if (sf == JFileChooser.APPROVE_OPTION) {

				for (int i = 0; i < anzahlGruppen; i++) {
					if (this.mainControl.getTurnierTabelle()[i] == null) {
						this.mainControl.getTurnierTabelleControl().makeSimpleTableView(i);

						this.mainControl.getTerminTabelleControl().makeSimpleTableView(i);

					}

					CrossTable turnierTabelle = mainControl.getTurnierTabelle()[i];
					int spalte = this.mainControl.getSimpleTableView()[i].getTable().getModel().getColumnCount();
					int zeile = this.mainControl.getSimpleTableView()[i].getTable().getModel().getRowCount();
					for (int x = 0; x < spalte; x++) {
						for (int y = 0; y < zeile; y++) {

							turnierTabelle.getTabellenMatrix()[x][y
									+ 1] = (String) this.mainControl.getSimpleTableView()[i].getTable().getValueAt(y,
											x);
						}
					}

					PDFOutputControl mftKreuz = new PDFOutputControl();
					PDFOutputControl mftTermin = new PDFOutputControl();

					if (filename != null) {

						File filename1 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
								+ filename + Messages.getString("PDFSaveControler.2") //$NON-NLS-1$
								+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".pdf"); //$NON-NLS-1$
						File filename2 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
								+ filename + Messages.getString("PDFSaveControler.8") //$NON-NLS-1$
								+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".pdf"); //$NON-NLS-1$

						String titel = Messages.getString("PDFSaveControler.10") //$NON-NLS-1$
								+ mainControl.getTurnier().getTurnierName() + " - " //$NON-NLS-1$
								+ mainControl.getTurnier().getGruppe()[i].getGruppenName();
						String pathName = filename1.getAbsolutePath();
						mftKreuz.createTurnierPdf(mainControl.getTurnier(), titel, pathName,
								turnierTabelle.getTabellenMatrix());

						titel = Messages.getString("PDFSaveControler.12") //$NON-NLS-1$
								+ mainControl.getTurnier().getTurnierName() + " - " //$NON-NLS-1$
								+ mainControl.getTurnier().getGruppe()[i].getGruppenName();
						pathName = filename2.getAbsolutePath();
						mftTermin.createTerminPdf(titel, pathName,
								mainControl.getTerminTabelle()[i].getTabellenMatrix());

					}
				}
				JOptionPane.showMessageDialog(mainControl, Messages.getString("PDFSaveControler.14")); //$NON-NLS-1$
				// first check if Desktop is supported by
				// Platform or not
				if (!Desktop.isDesktopSupported()) {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("PDFSaveControler.15"), //$NON-NLS-1$
							Messages.getString("PDFSaveControler.16"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
				} else {

					Desktop desktop = Desktop.getDesktop();

					try {
						desktop.open(savefile.getCurrentDirectory());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(mainControl, Messages.getString("PDFSaveControler.17"), //$NON-NLS-1$
								Messages.getString("PDFSaveControler.18"), //$NON-NLS-1$
								JOptionPane.INFORMATION_MESSAGE);
					}

				}
			}

		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("PDFSaveControler.19") + Messages.getString("PDFSaveControler.20")); //$NON-NLS-1$ //$NON-NLS-2$

		}

	}
}
