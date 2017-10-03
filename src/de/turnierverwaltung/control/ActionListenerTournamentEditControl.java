package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.ProgressBarView;
import de.turnierverwaltung.view.TabbedPaneView;

public class ActionListenerTournamentEditControl implements ActionListener {
	private MainControl mainControl;
	private NaviView naviView;
	private JButton pdfButton;
	private JButton iCalendarButton;
	private ProgressBarView progressBar;

	public ActionListenerTournamentEditControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		naviView = this.mainControl.getNaviView();
		pdfButton = naviView.getPdfSpeichernButton();
		pdfButton.addActionListener(this);
		naviView.getTabelleAktualisierenButton().addActionListener(this);
		naviView.getTabelleSpeichernButton().addActionListener(this);
		naviView.getPairingsLoadButton().addActionListener(this);
		naviView.getTabelleHTMLAusgabeButton().addActionListener(this);
		naviView.getExcelSpeichernButton().addActionListener(this);
		iCalendarButton = naviView.getiCalendarSpeichernButton();
		iCalendarButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(pdfButton)) {
			PDFSaveControl pdfsave = new PDFSaveControl(this.mainControl);
			pdfsave.savePDFFile();

		}
		if (arg0.getSource().equals(iCalendarButton)) {
			ICalendarSaveControl iCalendarsave = new ICalendarSaveControl(this.mainControl);
			try {
				iCalendarsave.saveiCalendarFile();
			} catch (IOException e) {
				// TODO Automatisch generierter Erfassungsblock
				e.printStackTrace();
			}

		}

		if (arg0.getSource().equals(naviView.getExcelSpeichernButton())) {
			ExcelSaveControl excelsave = new ExcelSaveControl(this.mainControl);
			excelsave.saveExcelFile();

		}
		if (arg0.getSource().equals(naviView.getPairingsLoadButton())) {
			mainControl.setPairingsControl(new PairingsControl(mainControl));

			PairingsControl pairingsControl = mainControl.getPairingsControl();

			Boolean ready = pairingsControl.checkNewTurnier();
			if (ready) {
				int gruppenAnzahl = mainControl.getTurnier().getAnzahlGruppen();

				progressBar = new ProgressBarView(Messages.getString("NaviController.32"),
						Messages.getString("NaviController.31"), gruppenAnzahl);

				mainControl.getNaviView().getTabellenPanel().setVisible(false);
				progressBar.iterate(gruppenAnzahl);

				TabbedPaneView[] tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();

				for (int i = 0; i < gruppenAnzahl; i++) {
					progressBar.iterate();
					tabAnzeigeView2[i].getTabbedPane().setEnabledAt(0, false);
					tabAnzeigeView2[i].getTabbedPane().setEnabledAt(1, false);
					pairingsControl.makeRundenEditView(i);
					tabAnzeigeView2[i].getTabbedPane().setSelectedIndex(2);
					progressBar.iterate();

				}
				this.mainControl.getNaviView().getPairingsPanel().setVisible(true);
				mainControl.getPairingsMenuActionControl().setPairingIsActive(true);
				progressBar.iterate(gruppenAnzahl);
				progressBar.iterate(gruppenAnzahl);

			} else {
				JOptionPane.showMessageDialog(null,
						Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

			}
		}

		if (arg0.getSource().equals(naviView.getTabelleAktualisierenButton()))

		{
			Boolean ok = mainControl.getPairingsControl().checkNewTurnier();
			if (ok) {
				makeNewTables();

			} else {
				JOptionPane.showMessageDialog(null,
						Messages.getString("NaviController.19") + Messages.getString("NaviController.20")); //$NON-NLS-1$ //$NON-NLS-2$
			}

		}
		if (arg0.getSource().equals(naviView.getTabelleSpeichernButton()))

		{
			Boolean ok = false;
			if (mainControl.getNeuesTurnier()) {
				try {
					ok = this.mainControl.getSaveTurnierControl().saveChangedPartien();
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}

			} else {
				try {
					ok = this.mainControl.getSaveTurnierControl().saveChangedPartien();
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}
				if (ok) {
					mainControl.getNaviView().getTabelleSpeichernButton().setEnabled(false);
					makeNewTables();
				}
			}
		}

		if (arg0.getSource().equals(naviView.getTabelleHTMLAusgabeButton()))

		{
			if (this.mainControl.getPropertiesControl().gethtmlToClipboard() == true) {
				HTMLCopyToClipboardControl htmlcopy = new HTMLCopyToClipboardControl(mainControl);
				htmlcopy.copyToClipboard();
			} else {
				HTMLSaveControl HTMLSave = new HTMLSaveControl(this.mainControl);
				HTMLSave.saveHTMLFile();
			}
		}

	}

	private void makeNewTables() {
		int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();

		for (int i = 0; i < anzahlGruppen; i++) {

			this.mainControl.getTurnierTabelleControl().okAction(i);

			mainControl.getTurnierTabelleControl().makeSimpleTableView(i);

			mainControl.getTerminTabelleControl().makeSimpleTableView(i);

		}
	}
}
