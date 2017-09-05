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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.ProgressBarView;
import de.turnierverwaltung.view.TabbedPaneView;
import net.fortuna.ical4j.model.ValidationException;

/**
 * 
 * @author mars
 *
 */
public class NaviControl implements ActionListener {

	public static final int TURNIERTABELLE = 0;
	public static final int TERMINTABELLE = 1;
	public static final int PAARUNGSTABELLE = 2;
	public static final int STANDARD = 1;

	public static final int SORTIEREN = 2;

	private MainControl mainControl;

	private NaviView naviView;
	private int aktiveGruppe;
	private JButton pdfButton;
	private ProgressBarView progressBar;
	private JButton iCalendarButton;

	/**
	 * 
	 * @param mainControl
	 */
	public NaviControl(MainControl mainControl) {

		this.mainControl = mainControl;

		naviView = new NaviView();

		this.mainControl.setNaviView(naviView);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
		this.mainControl.getNaviView().getTurnierListePanel().setVisible(false);
		this.mainControl.getNaviView().getSpielerListePanel().setVisible(false);
		this.mainControl.setPairingMenuActionControl(new PairingsMenuActionControl(this.mainControl));
		this.mainControl.setFileMenuActionControl(new FileMenuActionControl(this.mainControl));
		this.mainControl.setPlayerListMenuActionControl(new PlayerListMenuActionControl(this.mainControl));
		this.mainControl.setTournamentListMenuActionControl(new TournamentListMenuActionControl(mainControl));
		naviView.getPairingsLoadButton().addActionListener(this);

		pdfButton = naviView.getPdfSpeichernButton();
		pdfButton.addActionListener(this);
		naviView.getTabelleAktualisierenButton().addActionListener(this);
		naviView.getTabelleSpeichernButton().addActionListener(this);

		naviView.getTabelleHTMLAusgabeButton().addActionListener(this);
		naviView.getExcelSpeichernButton().addActionListener(this);
		iCalendarButton = naviView.getiCalendarSpeichernButton();
		iCalendarButton.addActionListener(this);

		aktiveGruppe = 0;
		makeNaviPanel();
	}

	/**
	 * 
	 */
	public void makeNaviPanel() {
		JPanel hauptPanel = this.mainControl.getMainPanel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(naviView);
		hauptPanel.add(scrollPane, BorderLayout.WEST);

		hauptPanel.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (this.mainControl.getTabAnzeigeView() != null) {

			if (this.mainControl.getTabAnzeigeView2() != null) {
				if (this.mainControl.getNaviView().getTabellenPanel().isVisible() == true) {
					aktiveGruppe = this.mainControl.getTabAnzeigeView().getTabbedPane().getSelectedIndex();
					this.mainControl.getTabAnzeigeView2()[aktiveGruppe].getTabbedPane().getSelectedIndex();
				}
			}
		}

		if (arg0.getSource() == pdfButton) {
			PDFSaveControl pdfsave = new PDFSaveControl(this.mainControl);
			pdfsave.savePDFFile();

		}
		if (arg0.getSource() == iCalendarButton) {
			ICalendarSaveControl iCalendarsave = new ICalendarSaveControl(this.mainControl);
			try {
				iCalendarsave.saveiCalendarFile();
			} catch (IOException e) {
				// TODO Automatisch generierter Erfassungsblock
				e.printStackTrace();
			} catch (ValidationException e) {
				// TODO Automatisch generierter Erfassungsblock
				e.printStackTrace();
			}

		}

		if (arg0.getSource() == naviView.getExcelSpeichernButton()) {
			ExcelSaveControl excelsave = new ExcelSaveControl(this.mainControl);
			excelsave.saveExcelFile();

		}
		if (arg0.getSource() == naviView.getPairingsLoadButton()) {
			mainControl.setRundenEingabeFormularControl(new PairingsControl(mainControl));

			PairingsControl pairingsControl = mainControl.getRundenEingabeFormularControl();

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

		if (arg0.getSource() == naviView.getTabelleAktualisierenButton())

		{
			Boolean ok = mainControl.getRundenEingabeFormularControl().checkNewTurnier();
			if (ok) {
				makeNewTables();

			} else {
				JOptionPane.showMessageDialog(null,
						Messages.getString("NaviController.19") + Messages.getString("NaviController.20")); //$NON-NLS-1$ //$NON-NLS-2$
			}

		}
		if (arg0.getSource() == naviView.getTabelleSpeichernButton())

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
					makeNewTables();
				}
			}
		}

		if (arg0.getSource() == naviView.getTabelleHTMLAusgabeButton())

		{
			HTMLSaveControl HTMLSave = new HTMLSaveControl(this.mainControl);
			HTMLSave.saveHTMLFile();

		}

	}

	private void makeNewTables() {
		int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();
		mainControl.getNaviView().getTabelleSpeichernButton().setEnabled(false);
		for (int i = 0; i < anzahlGruppen; i++) {

			this.mainControl.getTurnierTabelleControl().okAction(i);

			mainControl.getTurnierTabelleControl().makeSimpleTableView(i);

			mainControl.getTerminTabelleControl().makeSimpleTableView(i);

		}
	}

}
