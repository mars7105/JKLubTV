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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.model.CrossTable;

/**
 * 
 * @author mars
 *
 */
public class HTMLSaveControl {

	private MainControl mainControl;

	/**
	 * 
	 * @param mainControl
	 */
	public HTMLSaveControl(MainControl mainControl) {
		this.mainControl = mainControl;
	}

	/**
	 * 
	 */
	public void saveHTMLFile() {
		Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		if (ready) {
			int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();
			String filename = mainControl.getTurnier().getTurnierName();
			// String filename = JOptionPane.showInputDialog(mainControl,
			// Messages.getString("HTMLSaveControler.0"), //$NON-NLS-1$
			// Messages.getString("HTMLSaveControler.1"), //$NON-NLS-1$
			// JOptionPane.PLAIN_MESSAGE);
			File path = new File(mainControl.getPropertiesControl().getDefaultPath());

			JFileChooser savefile = new JFileChooser(path);
			FileFilter filter = new FileNameExtensionFilter("HTML", "html"); //$NON-NLS-1$ //$NON-NLS-2$
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
					if (filename != null) {
						File filename1 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
								+ filename + Messages.getString("HTMLSaveControler.5") //$NON-NLS-1$
								+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".html"); //$NON-NLS-1$
						File filename2 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
								+ filename + Messages.getString("HTMLSaveControler.8") //$NON-NLS-1$
								+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".html"); //$NON-NLS-1$
						int n1 = 0;
						if (filename1.exists()) {
							Object[] options = { Messages.getString("SaveDialog.2"),
									Messages.getString("SaveDialog.3") };
							n1 = JOptionPane.showOptionDialog(null,
									Messages.getString("SaveDialog.0") + filename1.getAbsolutePath()
											+ Messages.getString("SaveDialog.1"),
									"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
									options, options[1]);

						}
						int n2 = 0;
						if (filename2.exists()) {
							Object[] options = { Messages.getString("SaveDialog.2"),
									Messages.getString("SaveDialog.3") };
							n2 = JOptionPane.showOptionDialog(null,
									Messages.getString("SaveDialog.0") + filename2.getAbsolutePath()
											+ Messages.getString("SaveDialog.1"),
									"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
									options, options[1]);

						}
						// BufferedWriter writer;
						Writer writer1;
						Writer writer2;
						Boolean ohneHeaderundFooter = mainControl.getPropertiesControl().getOnlyTables();

						try {
							if (n1 == 0) {
								// Construct a writer for a specific encoding
								writer1 = new OutputStreamWriter(new FileOutputStream(filename1), "UTF8"); //$NON-NLS-1$

								writer1.write(
										this.mainControl.getTurnierTabelle()[i].getHTMLTable(ohneHeaderundFooter));
								writer1.flush();
								writer1.close();
							}
							if (n2 == 0) {
								writer2 = new OutputStreamWriter(new FileOutputStream(filename2), "UTF8"); //$NON-NLS-1$
								writer2.write(this.mainControl.getTerminTabelleControl().getTerminTabelle()[i]
										.getHTMLTable(ohneHeaderundFooter));
								writer2.flush();
								writer2.close();
							}
							try {
								InputStreamReader isReader = new InputStreamReader(
										this.getClass().getResourceAsStream("/files/style.css")); //$NON-NLS-1$
								BufferedReader br = new BufferedReader(isReader);

								PrintWriter writer3 = new PrintWriter(
										new File(savefile.getCurrentDirectory() + "/style.css")); //$NON-NLS-1$

								String Bs;
								while ((Bs = br.readLine()) != null) {
									writer3.println(Bs);
								}

								writer3.close();
								br.close();

							} catch (FileNotFoundException fnfe) {
								JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.14")); //$NON-NLS-1$
							} catch (IOException ioe) {
								JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.15")); //$NON-NLS-1$
							}

						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.16")); //$NON-NLS-1$
						}

					} else if (sf == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.17")); //$NON-NLS-1$
					}

				}
				JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.18")); //$NON-NLS-1$
				// File file = savefile.getSelectedFile();
				// first check if Desktop is supported by
				// Platform or not
				if (!Desktop.isDesktopSupported())

				{
					JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.19"), //$NON-NLS-1$
							Messages.getString("HTMLSaveControler.20"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
				} else

				{
					Desktop desktop = Desktop.getDesktop();

					try {
						desktop.open(savefile.getCurrentDirectory());
					} catch (IOException e) {
						// TODO Auto-generated catch block

					}
				}
			}

		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

		}
	}

}
