package de.turnierverwaltung.controller;

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
		int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();
		String filename = JOptionPane.showInputDialog(null, "Dateiname : ", "Eine Eingabeaufforderung",
				JOptionPane.PLAIN_MESSAGE);
		JFileChooser savefile = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("PDF", "pdf");
		savefile.addChoosableFileFilter(filter);
		savefile.setFileFilter(filter);
		savefile.setDialogType(JFileChooser.SAVE_DIALOG);
		int sf = savefile.showSaveDialog(null);
		if (sf == JFileChooser.APPROVE_OPTION) {
			savefile.setSelectedFile(new File(filename));

			for (int i = 0; i < anzahlGruppen; i++) {

				int spalte = this.mainControl.getSimpleTableView()[i].getTable().getModel().getColumnCount();
				int zeile = this.mainControl.getSimpleTableView()[i].getTable().getModel().getRowCount();
				for (int x = 0; x < spalte; x++) {
					for (int y = 0; y < zeile; y++) {

						this.mainControl.getTurnierTabelle()[i].getTabellenMatrix()[x][y
								+ 1] = (String) this.mainControl.getSimpleTableView()[i].getTable().getValueAt(y, x);

					}
				}

				PDFTabellenAusgabe mftKreuz = new PDFTabellenAusgabe();
				PDFTabellenAusgabe mftTermin = new PDFTabellenAusgabe();

				if (filename != null) {
					// filename += ".pdf";

					File filename1 = new File(savefile.getCurrentDirectory() + "/" + filename + "_Kreuztabelle_"
							+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".pdf");
					File filename2 = new File(savefile.getCurrentDirectory() + "/" + filename + "_Termintabelle_"
							+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".pdf");
					mainControl.getTurnierTabelle()[i].createMatrix();
					String titel = "Kreuztabelle " + mainControl.getTurnier().getTurnierName() + " - "
							+ mainControl.getTurnier().getGruppe()[i].getGruppenName();
					String pathName = filename1.getAbsolutePath();
					mftKreuz.createTurnierPdf(titel, pathName, mainControl.getTurnierTabelle()[i].getTabellenMatrix());
					// JOptionPane.showMessageDialog(null, "Datei
					// gespeichert.");

					mainControl.getTerminTabelle()[i].createTerminTabelle();
					titel = "Termintabelle " + mainControl.getTurnier().getTurnierName() + " - "
							+ mainControl.getTurnier().getGruppe()[i].getGruppenName();
					pathName = filename2.getAbsolutePath();
					mftTermin.createTerminPdf(titel, pathName, mainControl.getTerminTabelle()[i].getTabellenMatrix());

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
}
