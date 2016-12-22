package de.turnierverwaltung.control;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.model.ICalendar;
import de.turnierverwaltung.model.roundrobin.MeetingTable;
import net.fortuna.ical4j.model.ValidationException;

public class ICalendarSaveControl {

	private MainControl mainControl;
	private MeetingTable[] meetingTable;

	public ICalendarSaveControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.meetingTable = this.mainControl.getTerminTabelle();

	}

	public void saveiCalendarFile() throws IOException, ValidationException {
		Boolean ready = mainControl.getRundenEingabeFormularControl().checkNewTurnier();
		if (ready) {
			int gruppenAnzahl = this.meetingTable.length;
			String path = mainControl.getPropertiesControl().getDefaultPath();
			File defaultPath = new File(mainControl.getPropertiesControl().getDefaultPath());
			String filename = mainControl.getTurnier().getTurnierName();

			JFileChooser savefile = new JFileChooser(defaultPath);
			FileFilter filter = new FileNameExtensionFilter("ICS", "ics");
			savefile.addChoosableFileFilter(filter);
			savefile.setFileFilter(filter);
			savefile.setSelectedFile(new File(filename));
			savefile.setDialogType(JFileChooser.SAVE_DIALOG);
			int sf = savefile.showSaveDialog(null);
			if (sf == JFileChooser.APPROVE_OPTION) {
				for (int i = 0; i < gruppenAnzahl; i++) {
					ICalendar iCalendar = this.meetingTable[i].getiCalendar();
					String fileName = path + "/" + mainControl.getTurnier().getGruppe().get(i).getGruppenName() + "-"
							+ mainControl.getTurnier().getTurnierName() + ".ics";
					File file = new File(fileName);
					if (file.exists()) {
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
					desktop.open(new File(path));
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
