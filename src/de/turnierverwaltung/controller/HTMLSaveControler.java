package de.turnierverwaltung.controller;

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

public class HTMLSaveControler {

	private MainControl mainControl;

	public HTMLSaveControler(MainControl mainControl) {
		this.mainControl = mainControl;
	}

	public void saveHTMLFile() {
		Boolean ready = mainControl.getRundenEingabeFormularControl().checkNewTurnier();
		if (ready) {
			int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();
			String filename = JOptionPane.showInputDialog(null, "Anfangsname der Dateien : ",
					"Eine Eingabeaufforderung", JOptionPane.PLAIN_MESSAGE);
			if (filename != null) {
				JFileChooser savefile = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("HTML", "html");
				savefile.addChoosableFileFilter(filter);
				savefile.setFileFilter(filter);
				savefile.setSelectedFile(new File(filename));
				savefile.setDialogType(JFileChooser.SAVE_DIALOG);

				int sf = savefile.showSaveDialog(null);
				if (sf == JFileChooser.APPROVE_OPTION) {

					for (int i = 0; i < anzahlGruppen; i++) {
						this.mainControl.getTurnierTabelle()[i].createMatrix();
						int spalte = this.mainControl.getSimpleTableView()[i].getTable().getModel().getColumnCount();
						int zeile = this.mainControl.getSimpleTableView()[i].getTable().getModel().getRowCount();
						for (int x = 0; x < spalte; x++) {
							for (int y = 0; y < zeile; y++) {

								this.mainControl.getTurnierTabelle()[i].getTabellenMatrix()[x][y
										+ 1] = (String) this.mainControl.getSimpleTableView()[i].getTable()
												.getValueAt(y, x);

							}
						}
						if (filename != null) {
							File filename1 = new File(savefile.getCurrentDirectory() + "/" + filename + "_Kreuztabelle_"
									+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".html");
							File filename2 = new File(
									savefile.getCurrentDirectory() + "/" + filename + "_Termintabelle_"
											+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".html");

							// BufferedWriter writer;
							Writer writer1;
							Writer writer2;

							try {
								// Construct a writer for a specific encoding
								writer1 = new OutputStreamWriter(new FileOutputStream(filename1), "UTF8");
								writer1.write(this.mainControl.getTurnierTabelle()[i].getHTMLTable());
								writer1.flush();
								writer1.close();
								writer2 = new OutputStreamWriter(new FileOutputStream(filename2), "UTF8");
								writer2.write(this.mainControl.getTerminTabelleControl().getTerminTabelle()[i]
										.getHTMLTable());
								writer2.flush();
								writer2.close();
								try {
									InputStreamReader isReader = new InputStreamReader(
											this.getClass().getResourceAsStream("/files/style.css"));
									BufferedReader br = new BufferedReader(isReader);

									PrintWriter writer3 = new PrintWriter(
											new File(savefile.getCurrentDirectory() + "/style.css"));

									String Bs;
									while ((Bs = br.readLine()) != null) {
										writer3.println(Bs);
									}

									writer3.close();
									br.close();

								} catch (FileNotFoundException fnfe) {
									JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
								} catch (IOException ioe) {
									JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
								}

							} catch (IOException e) {
								JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
							}

						} else if (sf == JFileChooser.CANCEL_OPTION) {
							JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
						}

					}
					JOptionPane.showMessageDialog(null, "Dateien gespeichert.");
					// File file = savefile.getSelectedFile();
					// first check if Desktop is supported by
					// Platform or not
					if (!Desktop.isDesktopSupported())

					{
						JOptionPane.showMessageDialog(null, "Desktop is not supported.", "Folder not opened",
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
			}
		} else {
			JOptionPane.showMessageDialog(null, "Erst nach der Eingabe aller Gruppen\n" + "möglich.");

		}
	}

}
