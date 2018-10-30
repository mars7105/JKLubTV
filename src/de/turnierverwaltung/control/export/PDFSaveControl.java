package de.turnierverwaltung.control.export;

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

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.table.CrossTable;

public class PDFSaveControl {
	private MainControl mainControl;
	private PDFOutputControl pdfoutput;

	public PDFSaveControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		 pdfoutput = new PDFOutputControl();
	}

	public void savePDFFile() {
		Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		
		if (ready) {
			int anzahlGruppen = this.mainControl.getTournament().getAnzahlGruppen();

			String filename = mainControl.getTournament().getTurnierName().replaceAll(" ", "");
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
					if (this.mainControl.getCrossTable()[i] == null) {
						this.mainControl.getCrossTableControl().makeSimpleTableView(i);

						this.mainControl.getMeetingTableControl().makeSimpleTableView(i);

					}

					CrossTable turnierTabelle = mainControl.getCrossTable()[i];
					int spalte = this.mainControl.getCrossTableView()[i].getTable().getModel().getColumnCount();
					int zeile = this.mainControl.getCrossTableView()[i].getTable().getModel().getRowCount();
					for (int x = 0; x < spalte; x++) {
						for (int y = 0; y < zeile; y++) {

							turnierTabelle.getTabellenMatrix()[x][y
									+ 1] = (String) this.mainControl.getCrossTableView()[i].getTable().getValueAt(y,
											x);
						}
					}

					

					if (filename != null) {

						File filename1 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
								+ filename + Messages.getString("PDFSaveControler.2") //$NON-NLS-1$
								+ mainControl.getTournament().getGruppe()[i].getGruppenName().replaceAll(" ", "") //$NON-NLS-1$
								+ ".pdf");
						File filename2 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
								+ filename + Messages.getString("PDFSaveControler.8") //$NON-NLS-1$
								+ mainControl.getTournament().getGruppe()[i].getGruppenName().replaceAll(" ", "") //$NON-NLS-1$
								+ ".pdf");

						String titel = Messages.getString("PDFSaveControler.10") //$NON-NLS-1$
								+ mainControl.getTournament().getTurnierName() + " - " //$NON-NLS-1$
								+ mainControl.getTournament().getGruppe()[i].getGruppenName();
						String pathName = filename1.getAbsolutePath();
						pdfoutput.createTurnierPdf(mainControl.getTournament(), titel, pathName,
								turnierTabelle.getTabellenMatrix());

						titel = Messages.getString("PDFSaveControler.12") //$NON-NLS-1$
								+ mainControl.getTournament().getTurnierName() + " - " //$NON-NLS-1$
								+ mainControl.getTournament().getGruppe()[i].getGruppenName();
						pathName = filename2.getAbsolutePath();
						pdfoutput.createTerminPdf(titel, pathName,
								mainControl.getMeetingTable()[i].getTabellenMatrix());

					}
				}
//				JOptionPane.showMessageDialog(mainControl, Messages.getString("PDFSaveControler.14")); //$NON-NLS-1$
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
