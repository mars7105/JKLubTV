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

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.table.CrossTable;
import de.turnierverwaltung.model.table.NewsArticle;

/**
 *
 * @author mars
 *
 */
public class HTMLSaveControl {

	private final MainControl mainControl;

	/**
	 *
	 * @param mainControl
	 */
	public HTMLSaveControl(final MainControl mainControl) {
		this.mainControl = mainControl;
	}

	/**
	 *
	 */
	public void saveHTMLFile() {
		Boolean fileExist = false;

		final Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		if (ready) {
			final int anzahlGruppen = mainControl.getTournament().getAnzahlGruppen();
			final String filename = mainControl.getTournament().getTurnierName();

			final File path = new File(mainControl.getPropertiesControl().getDefaultPath());
			final String webserverPath = mainControl.getPropertiesControl().getWebserverPath();

			final JFileChooser savefile = new JFileChooser(path);
			final FileFilter filter = new FileNameExtensionFilter("HTML", "html"); //$NON-NLS-1$ //$NON-NLS-2$
			savefile.addChoosableFileFilter(filter);
			savefile.setFileFilter(filter);
			final File file = new File(filename);
			savefile.setSelectedFile(file);
			savefile.setDialogType(JFileChooser.SAVE_DIALOG);

			final int sf = savefile.showSaveDialog(null);
			if (sf == JFileChooser.APPROVE_OPTION) {

				for (int i = 0; i < anzahlGruppen; i++) {

					String wfn = mainControl.getTournament().getTurnierName();
					wfn += Messages.getString("PDFSaveControler.2")
							+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".pdf";
					final String webfilename1 = wfn.replaceAll(" ", "");

					String wfn2 = mainControl.getTournament().getTurnierName();
					wfn2 += Messages.getString("PDFSaveControler.8")
							+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".pdf";
					final String webfilename2 = wfn2.replaceAll(" ", "");

					final String ical = mainControl.getTournament().getTurnierName()
							+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".ics";
					final String webfilename3 = ical.replaceAll(" ", "");

					if (mainControl.getCrossTable()[i] == null) {
						mainControl.getCrossTableControl().makeSimpleTableView(i);

						mainControl.getMeetingTableControl().makeSimpleTableView(i);

					}

					final CrossTable turnierTabelle = mainControl.getCrossTable()[i];

					final int spalte = mainControl.getCrossTableView()[i].getTable().getModel().getColumnCount();
					final int zeile = mainControl.getCrossTableView()[i].getTable().getModel().getRowCount();
					for (int x = 0; x < spalte; x++) {
						for (int y = 0; y < zeile; y++) {

							turnierTabelle.getTabellenMatrix()[x][y + 1] = (String) mainControl.getCrossTableView()[i]
									.getTable().getValueAt(y, x);

						}
					}
					final File filename1 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
							+ filename + Messages.getString("HTMLSaveControler.5") //$NON-NLS-1$
							+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".html"); //$NON-NLS-1$
					final File filename2 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
							+ filename + Messages.getString("HTMLSaveControler.8") //$NON-NLS-1$
							+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".html"); //$NON-NLS-1$

					final File filename3 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
							+ filename + "news-article" //$NON-NLS-1$
							+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".html");
					int n1 = 0;
					if (filename1.exists() && fileExist == false) {
						fileExist = true;
						final Object[] options = { Messages.getString("SaveDialog.2"),
								Messages.getString("SaveDialog.3") };
						n1 = JOptionPane.showOptionDialog(null,
								Messages.getString("SaveDialog.0") + filename1.getAbsolutePath()
										+ Messages.getString("SaveDialog.1"),
								"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								options, options[1]);

					}
					int n2 = 0;
					if (filename2.exists() && fileExist == false) {
						fileExist = true;
						final Object[] options = { Messages.getString("SaveDialog.2"),
								Messages.getString("SaveDialog.3") };
						n2 = JOptionPane.showOptionDialog(null,
								Messages.getString("SaveDialog.0") + filename2.getAbsolutePath()
										+ Messages.getString("SaveDialog.1"),
								"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								options, options[1]);

					}
					int n3 = 0;
					if (filename3.exists() && fileExist == false) {
						fileExist = true;
						final Object[] options = { Messages.getString("SaveDialog.2"),
								Messages.getString("SaveDialog.3") };
						n3 = JOptionPane.showOptionDialog(null,
								Messages.getString("SaveDialog.0") + filename1.getAbsolutePath()
										+ Messages.getString("SaveDialog.1"),
								"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								options, options[1]);

					}
					// news article
					final String cssTable = mainControl.getPropertiesControl().getCSSTable();

					NewsArticle htmlArticle = new NewsArticle(
							mainControl.getMeetingTableControl().getTerminTabelle()[i].getTabellenMatrix(),
							mainControl.getTournament().getTurnierName() + " "
									+ mainControl.getTournament().getGruppe()[i].getGruppenName(),
							cssTable);

					Writer writer1;
					Writer writer2;
					Writer writer3;
					final Boolean ohneHeaderundFooter = mainControl.getPropertiesControl().getOnlyTables();
					final Boolean showLink = mainControl.getPropertiesControl().getPDFLinks();
//					final String cssTable = mainControl.getPropertiesControl().getCSSTable();

					try {
						if (n1 == 0) {
							// Construct a writer for a specific encoding
							writer1 = new OutputStreamWriter(new FileOutputStream(filename1), "UTF8"); //$NON-NLS-1$

							writer1.write(mainControl.getCrossTable()[i].getHTMLTable(ohneHeaderundFooter,
									webserverPath, webfilename1, showLink, cssTable));
							writer1.flush();
							writer1.close();
						}
						if (n2 == 0) {
							writer2 = new OutputStreamWriter(new FileOutputStream(filename2), "UTF8"); //$NON-NLS-1$
							writer2.write(mainControl.getMeetingTableControl().getTerminTabelle()[i].getHTMLTable(
									ohneHeaderundFooter, webserverPath, webfilename2, webfilename3, showLink,
									cssTable));
							writer2.flush();
							writer2.close();
						}
						if (n3 == 0) {
							writer3 = new OutputStreamWriter(new FileOutputStream(filename3), "UTF8"); //$NON-NLS-1$
							writer3.write(htmlArticle.getHtmlContent(ohneHeaderundFooter));
							writer3.flush();
							writer3.close();
						}
						try {
							final InputStreamReader isReader = new InputStreamReader(
									this.getClass().getResourceAsStream("/files/style.css")); //$NON-NLS-1$
							final BufferedReader br = new BufferedReader(isReader);

							final PrintWriter writer4 = new PrintWriter(
									new File(savefile.getCurrentDirectory() + "/style.css")); //$NON-NLS-1$

							String Bs;
							while ((Bs = br.readLine()) != null) {
								writer4.println(Bs);
							}

							writer4.close();
							br.close();

						} catch (final FileNotFoundException fnfe) {
							JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.14")); //$NON-NLS-1$
						} catch (final IOException ioe) {
							JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.15")); //$NON-NLS-1$
						}

					} catch (final IOException e) {
						JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.16")); //$NON-NLS-1$
					}

				}

				if (!Desktop.isDesktopSupported())

				{
					JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.19"), //$NON-NLS-1$
							Messages.getString("HTMLSaveControler.20"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
				} else

				{
					final Desktop desktop = Desktop.getDesktop();

					try {
						desktop.open(savefile.getCurrentDirectory());
					} catch (final IOException e) {
						// TODO Auto-generated catch block

					}
				}
			} else if (sf == JFileChooser.CANCEL_OPTION) {
				JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.17")); //$NON-NLS-1$
			}

		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

		}
	}

}
