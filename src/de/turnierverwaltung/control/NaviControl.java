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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.NewPlayerView;
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

	private JButton newTurnierButton;
	private NaviView naviView;
	private int aktiveGruppe;
	private JButton pdfButton;
	private NewPlayerView spielerHinzufuegenView;
	private DSBDWZControl dewisDialogControl;
	private ProgressBarView progressBar;
	private JButton iCalendarButton;

	/**
	 * 
	 * @param mainControl
	 */
	public NaviControl(MainControl mainControl) {

		this.mainControl = mainControl;

		naviView = new NaviView();
		
		
		mainControl.setNaviView(naviView);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
		this.mainControl.getNaviView().getTurnierListePanel().setVisible(false);
		this.mainControl.getNaviView().getSpielerListePanel().setVisible(false);
		this.mainControl.setPairingMenuActionControl(new PairingsMenuActionControl(mainControl));
		this.mainControl.setFileMenuActionControl(new FileMenuActionControl(mainControl));
		newTurnierButton = naviView.getTurnierAddButton();
		newTurnierButton.addActionListener(this);
		naviView.getPairingsLoadButton().addActionListener(this);
		naviView.getSpielerAddButton().addActionListener(this);
		naviView.getSpielerExport().addActionListener(this);
		naviView.getSpielerImport().addActionListener(this);
		naviView.getSpielerDEWISSearchButton().addActionListener(this);
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
		// turnierAnsicht = new TurnierAnsicht(mainControl);
	}

	/**
	 * 
	 */
	public void makeNaviPanel() {
		JPanel hauptPanel = this.mainControl.getMainPanel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(naviView);
		hauptPanel.add(scrollPane, BorderLayout.WEST);

		dewisDialogControl = new DSBDWZControl(mainControl);
		hauptPanel.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (spielerHinzufuegenView != null) {
			if (arg0.getSource() == spielerHinzufuegenView.getOkButton()) {
				try {
					String name = spielerHinzufuegenView.getTextFieldName().getText();
					if (!name.equals("Spielfrei")) {
						String kuerzel = spielerHinzufuegenView.getTextFieldKuerzel().getText();
						String dwz = spielerHinzufuegenView.getTextFieldDwz().getText();
						int age = spielerHinzufuegenView.getTextComboBoxAge().getSelectedIndex();
						Player neuerSpieler = new Player();
						neuerSpieler.setName(name);
						neuerSpieler.setKuerzel(kuerzel);
						neuerSpieler.setDwz(dwz);
						neuerSpieler.setAge(age);
						SQLPlayerControl stc = new SQLPlayerControl(mainControl);

						neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));

						this.mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
					}
					spielerHinzufuegenView.getTextFieldName().setEditable(false);
					spielerHinzufuegenView.getTextFieldKuerzel().setEditable(false);
					spielerHinzufuegenView.getTextFieldDwz().setEditable(false);
					spielerHinzufuegenView.getTextComboBoxAge().setEnabled(false);
					spielerHinzufuegenView.spielerPanel();
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}
			}

			if (arg0.getSource() == spielerHinzufuegenView.getCancelButton()) {
				mainControl.setEnabled(true);
				try {
					this.mainControl.getSpielerLadenControl().updateSpielerListe();
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}

				spielerHinzufuegenView.closeWindow();
			}
		}
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

		if (arg0.getSource() == newTurnierButton) {
			mainControl.setSpielerEingabeControl(null);
			Tournament turnier = this.mainControl.getTurnier();
			if (turnier == null) {
				mainControl.setTurnierControl(new NewTournamentControl(mainControl));
			} else {

				ArrayList<Game> changedPartien = this.mainControl.getChangedPartien();
				if (changedPartien != null) {
					if (changedPartien.size() > 0) {
						// Custom button text
						Object[] options = { Messages.getString("TurnierListeLadenControl.10"), //$NON-NLS-1$
								Messages.getString("TurnierListeLadenControl.11") }; //$NON-NLS-1$
						int abfrage = JOptionPane.showOptionDialog(mainControl,
								Messages.getString("TurnierListeLadenControl.12") //$NON-NLS-1$
										+ Messages.getString("TurnierListeLadenControl.13"), //$NON-NLS-1$
								Messages.getString("TurnierListeLadenControl.14"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
								JOptionPane.WARNING_MESSAGE, null, options, options[1]);
						if (abfrage == 0) {
							SaveTournamentControl saveGames = new SaveTournamentControl(mainControl);
							try {
								Boolean saved = saveGames.saveChangedPartien();
								if (saved == false) {
									changedPartien.clear();
								}

							} catch (SQLException e) {
								changedPartien.clear();
							}
							mainControl.setTurnierControl(new NewTournamentControl(mainControl));
						}
					} else if (changedPartien.size() == 0) {
						mainControl.setTurnierControl(new NewTournamentControl(mainControl));
					}
				} else {
					mainControl.setTurnierControl(new NewTournamentControl(mainControl));
				}
			}
		}

		if (arg0.getSource() == naviView.getSpielerImport()) {
			SQLImportPlayerListControl spielerImport = new SQLImportPlayerListControl(mainControl);
			try {
				spielerImport.importSpielerTable();

				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}
		if (arg0.getSource() == naviView.getSpielerExport()) {
			SQLExportPlayerListControl spielerExport = new SQLExportPlayerListControl(this.mainControl);
			try {
				spielerExport.exportSpielerTable();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}
		if (arg0.getSource() == naviView.getSpielerAddButton()) {
			spielerHinzufuegenView = new NewPlayerView();

			spielerHinzufuegenView.getOkButton().addActionListener(this);
			spielerHinzufuegenView.getCancelButton().addActionListener(this);
			mainControl.setEnabled(false);
		}
		if (arg0.getSource() == naviView.getSpielerDEWISSearchButton()) {
			dewisDialogControl.makeDialog();
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

	/**
	 * 
	 */
	public void neuerSpieler() {
		try {
			this.mainControl.getSpielerLadenControl().updateSpielerListe();
		} catch (SQLException e) {
			mainControl.fileSQLError();
		}
		spielerHinzufuegenView = new NewPlayerView();

		spielerHinzufuegenView.getOkButton().addActionListener(this);
		spielerHinzufuegenView.getCancelButton().addActionListener(this);
		mainControl.setEnabled(false);
	}

}
