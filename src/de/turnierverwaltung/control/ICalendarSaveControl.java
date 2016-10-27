package de.turnierverwaltung.control;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import de.turnierverwaltung.model.ICalendar;
import de.turnierverwaltung.model.MeetingTable;
import net.fortuna.ical4j.model.ValidationException;

public class ICalendarSaveControl {

	private MainControl mainControl;
	private MeetingTable[] meetingTable;

	public ICalendarSaveControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.meetingTable = this.mainControl.getTerminTabelle();

	}

	public void saveiCalendarFile() throws IOException, ValidationException {
		int gruppenAnzahl = this.meetingTable.length;
		String path = mainControl.getPropertiesControl().getDefaultPath();

		for (int i = 0; i < gruppenAnzahl; i++) {
			ICalendar iCalendar = this.meetingTable[i].getiCalendar();
			String fileName = path + "/" + mainControl.getTurnier().getGruppe()[i].getGruppenName() + "-"
					+ mainControl.getTurnier().getTurnierName() + ".ics";
			iCalendar.saveICalender(fileName);
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
	}

}
