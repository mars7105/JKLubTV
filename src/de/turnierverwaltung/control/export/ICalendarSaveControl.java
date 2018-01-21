package de.turnierverwaltung.control.export;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.ICal;
import de.turnierverwaltung.model.table.MeetingTable;

public class ICalendarSaveControl {

	private MainControl mainControl;
	private MeetingTable[] meetingTable;

	public ICalendarSaveControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.meetingTable = this.mainControl.getMeetingTable();

	}

	public void saveiCalendarFile() throws IOException {
		Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		Boolean fileExist = false;
		if (ready) {
			int gruppenAnzahl = this.meetingTable.length;
			File defaultPath = new File(mainControl.getPropertiesControl().getDefaultPath());
			String filename = mainControl.getTournament().getTurnierName();

			JFileChooser savefile = new JFileChooser(defaultPath);
			FileFilter filter = new FileNameExtensionFilter("ICS", "ics");
			savefile.addChoosableFileFilter(filter);
			savefile.setFileFilter(filter);
			savefile.setSelectedFile(new File(filename));
			savefile.setDialogType(JFileChooser.SAVE_DIALOG);
			int sf = savefile.showSaveDialog(null);
			if (sf == JFileChooser.APPROVE_OPTION) {

				for (int i = 0; i < gruppenAnzahl; i++) {

					ICal iCalendar = this.meetingTable[i].getiCalendar();
					String fileName = savefile.getCurrentDirectory() + "/"
							+ mainControl.getTournament().getTurnierName().replaceAll(" ", "")
							+ mainControl.getTournament().getGruppe()[i].getGruppenName().replaceAll(" ", "") + ".ics";

					File file = new File(fileName);
					if (file.exists() && fileExist == false) {
						fileExist = true;
						Object[] options = { Messages.getString("SaveDialog.2"), Messages.getString("SaveDialog.3") };
						int n = JOptionPane.showOptionDialog(null,
								Messages.getString("SaveDialog.0") + fileName + Messages.getString("SaveDialog.1"),
								"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								options, options[1]);
						if (n == 0) {
							iCalendar.saveICalender(fileName);
						}
					} else {
						iCalendar.saveICalender(fileName);
					}

				}
			}
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
		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("PDFSaveControler.19") + Messages.getString("PDFSaveControler.20")); //$NON-NLS-1$ //$NON-NLS-2$

		}
	}

}
