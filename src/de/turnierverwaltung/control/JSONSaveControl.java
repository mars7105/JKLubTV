package de.turnierverwaltung.control;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.model.CrossTable;
import de.turnierverwaltung.model.JSON;

public class JSONSaveControl {
	private MainControl mainControl;

	public JSONSaveControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
	}

	public void saveJSONFile() {
		Boolean ready = mainControl.getRundenEingabeFormularControl().checkNewTurnier();

		if (ready) {
			int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();
			String filename = mainControl.getTurnier().getTurnierName();
			File path = new File(mainControl.getPropertiesControl().getDefaultPath());

			JFileChooser savefile = new JFileChooser(path);
			FileFilter filter = new FileNameExtensionFilter("TXT", "txt"); //$NON-NLS-1$ //$NON-NLS-2$
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

					JSON mftKreuz = new JSON();
					JSON mftTermin = new JSON();

					if (filename != null) {

						File filename1 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
								+ filename + Messages.getString("PDFSaveControler.2") //$NON-NLS-1$
								+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".txt"); //$NON-NLS-1$
						File filename2 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
								+ filename + Messages.getString("PDFSaveControler.8") //$NON-NLS-1$
								+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".txt"); //$NON-NLS-1$

						Messages.getString("PDFSaveControler.10") //$NON-NLS-1$
						;
						mainControl.getTurnier().getTurnierName();
						mainControl.getTurnier().getGruppe()[i].getGruppenName();
						String pathName = filename1.getAbsolutePath();
						try {
							mftKreuz.createJSON(pathName, turnierTabelle.getTabellenMatrix(), "Kreuztabelle", i);
						} catch (IOException e) {
							// TODO Automatisch generierter Erfassungsblock
							e.printStackTrace();
						}

						pathName = filename2.getAbsolutePath();
						try {
							mftTermin.createJSON(pathName, mainControl.getTerminTabelle()[i].getTabellenMatrix(),
									"Termintabelle", i);
						} catch (IOException e) {
							// TODO Automatisch generierter Erfassungsblock
							e.printStackTrace();
						}

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
