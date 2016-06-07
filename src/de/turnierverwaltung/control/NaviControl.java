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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.SpielerHinzufuegenView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class NaviControl implements ActionListener {

	public static final int TURNIERTABELLE = 0;
	public static final int TERMINTABELLE = 1;
	public static final int PAARUNGSTABELLE = 2;
	public static final int STANDARD = 1;

	public static final int SORTIEREN = 2;

	private MainControl mainControl;
	// private JButton spielerListeButton;
	// private JButton turnierListeButton;
	private JButton newdbButton;
	private JButton loaddbButton;
	private JButton newTurnierButton;
	private NaviView naviView;
	private int aktiveGruppe;
	private JButton pdfButton;
	private SpielerHinzufuegenView spielerHinzufuegenView;
	private DewisDialogControl dewisDialogControl;
	private TurnierAnsicht turnierAnsicht;
	private boolean pairingIsActive;

	public NaviControl(MainControl mainControl) {

		this.mainControl = mainControl;

		naviView = new NaviView();
		mainControl.setNaviView(naviView);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		this.mainControl.getNaviView().getPairingsPanel().setVisible(false);

		newdbButton = naviView.getNewDatabseButton();
		newdbButton.addActionListener(this);
		loaddbButton = naviView.getLoadDatabaseButton();
		loaddbButton.addActionListener(this);
		newTurnierButton = naviView.getTurnierAddButton();
		newTurnierButton.addActionListener(this);

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
		naviView.getPairingsLoadButton().addActionListener(this);
		naviView.getPairingsSaveButton().addActionListener(this);
		pairingIsActive = false;
		aktiveGruppe = 0;
		makeNaviPanel();
		turnierAnsicht = new TurnierAnsicht(mainControl);
	}

	public void makeNaviPanel() {
		JPanel hauptPanel = this.mainControl.getMainPanel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(naviView);
		hauptPanel.add(scrollPane, BorderLayout.WEST);
		// this.mainControl.getHauptPanel().addChangeListener(new
		// TurnierAnsicht(mainControl));
		dewisDialogControl = new DewisDialogControl(mainControl);
		hauptPanel.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (spielerHinzufuegenView != null) {
			if (arg0.getSource() == spielerHinzufuegenView.getOkButton()) {
				String name = spielerHinzufuegenView.getTextFieldName()
						.getText();
				if (!name.equals("Spielfrei")) {
					String kuerzel = spielerHinzufuegenView
							.getTextFieldKuerzel().getText();
					String dwz = spielerHinzufuegenView.getTextFieldDwz()
							.getText();
					int age = spielerHinzufuegenView.getTextComboBoxAge()
							.getSelectedIndex();
					Spieler neuerSpieler = new Spieler();
					neuerSpieler.setName(name);
					neuerSpieler.setKuerzel(kuerzel);
					neuerSpieler.setDwz(dwz);
					neuerSpieler.setAge(age);
					SpielerTableControl stc = new SpielerTableControl(
							mainControl);
					neuerSpieler.setSpielerId(stc
							.insertOneSpieler(neuerSpieler));
					this.mainControl.getSpielerLadenControl().getSpieler()
							.add(neuerSpieler);
				}
				spielerHinzufuegenView.getTextFieldName().setEditable(false);
				spielerHinzufuegenView.getTextFieldKuerzel().setEditable(false);
				spielerHinzufuegenView.getTextFieldDwz().setEditable(false);
				spielerHinzufuegenView.getTextComboBoxAge().setEnabled(false);
				spielerHinzufuegenView.spielerPanel();

			}

			if (arg0.getSource() == spielerHinzufuegenView.getCancelButton()) {
				mainControl.setEnabled(true);
				try {
					this.mainControl.getSpielerLadenControl()
							.updateSpielerListe();
				} catch (SQLException e) {
					mainControl.resetProperties();
				}

				spielerHinzufuegenView.closeWindow();
			}
		}
		if (this.mainControl.getTabAnzeigeView() != null) {

			if (this.mainControl.getTabAnzeigeView2() != null) {
				if (this.mainControl.getNaviView().getTabellenPanel()
						.isVisible() == true) {
					aktiveGruppe = this.mainControl.getTabAnzeigeView()
							.getSelectedIndex();
					this.mainControl.getTabAnzeigeView2()[aktiveGruppe]
							.getSelectedIndex();
				}
			}
		}

		if (arg0.getSource() == pdfButton) {
			PDFSaveControl pdfsave = new PDFSaveControl(this.mainControl);
			pdfsave.savePDFFile();

		}
		if (arg0.getSource() == naviView.getExcelSpeichernButton()) {
			ExcelSaveControl excelsave = new ExcelSaveControl(this.mainControl);
			excelsave.saveExcelFile();

		}
		if (arg0.getSource() == naviView.getPairingsLoadButton()) {

			Boolean ready = mainControl.getRundenEingabeFormularControl()
					.checkNewTurnier();
			if (ready) {
				mainControl.getNaviView().getTabellenPanel().setVisible(false);
				RundenEingabeFormularControl pairingsControl = mainControl
						.getRundenEingabeFormularControl();
				pairingsControl.init();
				int gruppenAnzahl = mainControl.getTurnier().getAnzahlGruppen();
				TabAnzeigeView[] tabAnzeigeView2 = this.mainControl
						.getTabAnzeigeView2();

				for (int i = 0; i < gruppenAnzahl; i++) {
					tabAnzeigeView2[i].setEnabledAt(0, false);
					tabAnzeigeView2[i].setEnabledAt(1, false);
					pairingsControl.makeRundenEditView(i);
					tabAnzeigeView2[i].setSelectedIndex(2);

				}
				this.mainControl.getNaviView().getPairingsPanel()
						.setVisible(true);
				pairingIsActive = true;
			} else {
				JOptionPane
						.showMessageDialog(
								null,
								Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

			}
		}
		if (arg0.getSource() == naviView.getPairingsSaveButton()) {

			saveAndReloadTurnier();
			setTabsEnable(true);
			pairingIsActive = false;

		}
		if (arg0.getSource() == newTurnierButton) {
			mainControl.setSpielerEingabeControl(null);
			Turnier turnier = this.mainControl.getTurnier();
			if (turnier == null) {
				mainControl.setTurnierControl(new TurnierControl(mainControl));
			} else {
				// Custom button text
				Object[] options = {
						Messages.getString("NaviController.0"), Messages.getString("NaviController.1") }; //$NON-NLS-1$ //$NON-NLS-2$
				int abfrage = JOptionPane
						.showOptionDialog(
								mainControl,
								Messages.getString("NaviController.2") //$NON-NLS-1$
										+ Messages
												.getString("NaviController.3"), //$NON-NLS-1$
								Messages.getString("NaviController.4"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
								JOptionPane.WARNING_MESSAGE, null, options,
								options[1]);
				if (abfrage == 0) {
					// mainControl.resetApp();
					mainControl.setTurnierControl(new TurnierControl(
							mainControl));
				}
			}
		}

		if (arg0.getSource() == newdbButton) {

			int abfrage = warnHinweis();
			if (abfrage == 0) {
				// this.mainControl.getNaviView().getTabellenPanel().setVisible(false);

				String filename = JOptionPane
						.showInputDialog(
								null,
								Messages.getString("NaviController.5"), Messages.getString("NaviController.6"), //$NON-NLS-1$ //$NON-NLS-2$
								JOptionPane.PLAIN_MESSAGE);

				if (filename != null) {
					filename += ".ktv"; //$NON-NLS-1$
					File path = new File(mainControl.getPropertiesControl()
							.getDefaultPath());

					JFileChooser savefile = new JFileChooser(path);
					FileFilter filter = new FileNameExtensionFilter(
							Messages.getString("NaviController.8"), "ktv"); //$NON-NLS-1$ //$NON-NLS-2$
					savefile.addChoosableFileFilter(filter);
					savefile.setFileFilter(filter);
					savefile.setDialogType(JFileChooser.SAVE_DIALOG);
					savefile.setSelectedFile(new File(filename));
					BufferedWriter writer;
					int sf = savefile.showSaveDialog(null);
					if (sf == JFileChooser.APPROVE_OPTION) {
						mainControl.resetApp();
						// mainControl.datenbankMenueView(false);
						try {
							File file = savefile.getSelectedFile();
							writer = new BufferedWriter(new FileWriter(
									savefile.getSelectedFile()));
							writer.write(""); //$NON-NLS-1$
							writer.close();

							// true for rewrite, false for override
							SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
							mainControl.getPropertiesControl()
									.setPathToDatabase(
											SQLiteDAOFactory.getDB_PATH());
							mainControl.setTitle(Messages
									.getString("MainControl.8") //$NON-NLS-1$
									+ SQLiteDAOFactory.getDB_PATH());
							mainControl.getPropertiesControl().setDefaultPath(
									file.getParent());
							mainControl.getEigenschaftenControl()
									.getEigenschaftenView()
									.setOpenDefaultPathLabel(file.getParent());
							SQLiteControl sqlC = new SQLiteControl();
							sqlC.createAllTables();
							// mainControl.datenbankMenueView(true);
							JOptionPane
									.showMessageDialog(
											null,
											Messages.getString("NaviController.11"), Messages.getString("NaviController.12"), //$NON-NLS-1$ //$NON-NLS-2$
											JOptionPane.INFORMATION_MESSAGE);
							this.mainControl.setNeuesTurnier(false);
							mainControl
									.setTurnierTableControl(new TurnierTableControl(
											mainControl));
							// mainControl.getTurnierTableControl()
							// .loadTurnierListe();
							mainControl
									.setSpielerEditierenControl(new SpielerLadenControl(
											mainControl));
							mainControl.getSpielerEditierenControl()
									.updateSpielerListe();
							mainControl
									.setTurnierListeLadenControl(new TurnierListeLadenControl(
											this.mainControl));
							mainControl.getTurnierListeLadenControl()
									.loadTurnierListe();

							mainControl.getPropertiesControl()
									.setPathToDatabase(
											SQLiteDAOFactory.getDB_PATH());
							mainControl.getPropertiesControl()
									.writeProperties();
							turnierAnsicht = new TurnierAnsicht(mainControl);
							mainControl.getHauptPanel().addChangeListener(
									turnierAnsicht);

							naviView.updateUI();

						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									Messages.getString("NaviController.13")); //$NON-NLS-1$
						} catch (SQLException e) {
							mainControl.resetProperties();
						}
					} else if (sf == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null,
								Messages.getString("NaviController.14")); //$NON-NLS-1$
					}
				}
			}
		}
		if (arg0.getSource() == loaddbButton)

		{

			int abfrage = warnHinweis();
			if (abfrage == 0) {

				// Create a file chooser
				File path = new File(mainControl.getPropertiesControl()
						.getDefaultPath());

				JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter(
						Messages.getString("NaviController.15"), Messages.getString("NaviController.16")); //$NON-NLS-1$ //$NON-NLS-2$
				fc.addChoosableFileFilter(filter);
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);
				try {
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						mainControl.resetApp();
						// mainControl.datenbankMenueView(false);
						File file = fc.getSelectedFile();
						// This is where a real application would open the file.
						SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
						// mainControl.datenbankMenueView(true);
						// if (mainControl.getSpielerEditierenControl() != null)
						// {
						// mainControl.getSpielerEditierenControl().makePanel();
						// } else {

						// }
						mainControl.setNeuesTurnier(false);
						// mainControl.getNaviView().getTabellenPanel().setVisible(false);
						mainControl
								.setTurnierTableControl(new TurnierTableControl(
										mainControl));
						// mainControl.getTurnierTableControl().loadTurnierListe();
						mainControl
								.setSpielerEditierenControl(new SpielerLadenControl(
										mainControl));
						mainControl.getSpielerEditierenControl()
								.updateSpielerListe();
						mainControl
								.setTurnierListeLadenControl(new TurnierListeLadenControl(
										this.mainControl));

						mainControl.getTurnierListeLadenControl()
								.loadTurnierListe();

						mainControl.getPropertiesControl().setPathToDatabase(
								SQLiteDAOFactory.getDB_PATH());
						mainControl.getPropertiesControl().setDefaultPath(
								file.getParent());
						mainControl.getEigenschaftenControl()
								.getEigenschaftenView()
								.setOpenDefaultPathLabel(file.getParent());
						mainControl.getPropertiesControl().writeProperties();
						naviView.setPathToDatabase(new JLabel(SQLiteDAOFactory
								.getDB_PATH()));

						mainControl.setTitle(Messages
								.getString("MainControl.8") //$NON-NLS-1$
								+ SQLiteDAOFactory.getDB_PATH());
						turnierAnsicht = new TurnierAnsicht(mainControl);
						mainControl.getHauptPanel().addChangeListener(
								turnierAnsicht);
						for (int i = 0; i < mainControl.getHauptPanel()
								.getTabCount(); i++) {
							if (mainControl
									.getHauptPanel()
									.getTitleAt(i)
									.equals(Messages
											.getString("NaviController.17"))) { //$NON-NLS-1$
								mainControl.getHauptPanel().setSelectedIndex(i);
							}
						}

						naviView.updateUI();

					} else {
						JOptionPane.showMessageDialog(null,
								Messages.getString("NaviController.18")); //$NON-NLS-1$
					}
				} catch (SQLException e) {
					mainControl.resetProperties();
				}
			}

		}
		if (arg0.getSource() == naviView.getSpielerImport()) {
			SpielerTableImportControl spielerImport = new SpielerTableImportControl(
					mainControl);
			try {
				spielerImport.importSpielerTable();

				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				mainControl.resetProperties();
			}
		}
		if (arg0.getSource() == naviView.getSpielerExport()) {
			SpielerTableExportControl spielerExport = new SpielerTableExportControl(
					this.mainControl);
			try {
				spielerExport.exportSpielerTable();
			} catch (SQLException e) {
				mainControl.resetProperties();
			}
		}
		if (arg0.getSource() == naviView.getSpielerAddButton()) {
			spielerHinzufuegenView = new SpielerHinzufuegenView();

			spielerHinzufuegenView.getOkButton().addActionListener(this);
			spielerHinzufuegenView.getCancelButton().addActionListener(this);
			mainControl.setEnabled(false);
		}
		if (arg0.getSource() == naviView.getSpielerDEWISSearchButton()) {
			dewisDialogControl.makeDialog();
		}

		if (arg0.getSource() == naviView.getTabelleAktualisierenButton())

		{
			Boolean ok = mainControl.getRundenEingabeFormularControl()
					.checkNewTurnier();
			if (ok) {
				makeNewTables();

			} else {
				JOptionPane
						.showMessageDialog(
								null,
								Messages.getString("NaviController.19") + Messages.getString("NaviController.20")); //$NON-NLS-1$ //$NON-NLS-2$
			}

		}
		if (arg0.getSource() == naviView.getTabelleSpeichernButton())

		{
			Boolean ok = false;
			if (mainControl.getNeuesTurnier()) {
				try {
					ok = this.mainControl.getSaveTurnierControl()
							.saveChangedPartien();
				} catch (SQLException e) {
					mainControl.resetProperties();
				}

			} else {
				try {
					ok = this.mainControl.getSaveTurnierControl()
							.saveChangedPartien();
				} catch (SQLException e) {
					mainControl.resetProperties();
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

	private Boolean saveAndReloadTurnier() {

		Boolean ok = true;
		try {
			ok = this.mainControl.getSaveTurnierControl().saveChangedPartien();
		} catch (SQLException e) {
			ok = false;
			e.printStackTrace();
		}
		return ok;
	}

	public boolean getPairingIsActive() {
		return pairingIsActive;
	}

	public void setPairingIsActive(boolean pairingIsActive) {
		this.pairingIsActive = pairingIsActive;
	}

	private void setTabsEnable(Boolean enable) {
		int gruppenAnzahl = mainControl.getTurnier().getAnzahlGruppen();
		TabAnzeigeView[] tabAnzeigeView2 = this.mainControl
				.getTabAnzeigeView2();
		mainControl.getNaviView().getTabellenPanel().setVisible(enable);
		mainControl.getNaviView().getPairingsPanel().setVisible(!enable);
		for (int i = 0; i < gruppenAnzahl; i++) {
			tabAnzeigeView2[i].setEnabledAt(0, enable);
			tabAnzeigeView2[i].setEnabledAt(1, enable);
			tabAnzeigeView2[i].setEnabledAt(2, !enable);
		}
		mainControl.getTurnierListeLadenControl().reloadTurnier();

	}

	private void makeNewTables() {
		int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();
		for (int i = 0; i < anzahlGruppen; i++) {

			this.mainControl.getTurnierTabelleControl().okAction(i);

			mainControl.getTurnierTabelleControl().makeSimpleTableView(i);

			mainControl.getTerminTabelleControl().makeSimpleTableView(i);

		}
	}

	public void neuerSpieler() {
		try {
			this.mainControl.getSpielerLadenControl().updateSpielerListe();
		} catch (SQLException e) {
			mainControl.resetProperties();
		}
		spielerHinzufuegenView = new SpielerHinzufuegenView();

		spielerHinzufuegenView.getOkButton().addActionListener(this);
		spielerHinzufuegenView.getCancelButton().addActionListener(this);
		mainControl.setEnabled(false);
	}

	private int warnHinweis() {
		int abfrage = 0;
		String hinweisText = Messages.getString("NaviController.21") //$NON-NLS-1$
				+ Messages.getString("NaviController.22") //$NON-NLS-1$
				+ Messages.getString("NaviController.23"); //$NON-NLS-1$
		if (this.mainControl.getNaviView().getTabellenPanel().isVisible() == true) {
			abfrage = 1;
			// Custom button text
			Object[] options = {
					Messages.getString("NaviController.24"), Messages.getString("NaviController.25") }; //$NON-NLS-1$ //$NON-NLS-2$
			abfrage = JOptionPane
					.showOptionDialog(
							mainControl,
							hinweisText,
							Messages.getString("NaviController.26"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
							JOptionPane.WARNING_MESSAGE, null, options,
							options[1]);

		}
		if (abfrage == 0) {
			mainControl.setNeuesTurnier(false);

		}
		return abfrage;
	}

	public TurnierAnsicht getTurnierAnsicht() {
		return turnierAnsicht;
	}

	public void setTurnierAnsicht(TurnierAnsicht turnierAnsicht) {
		this.turnierAnsicht = turnierAnsicht;
	}

	class TurnierAnsicht implements ChangeListener {

		private MainControl mainControl;

		public TurnierAnsicht(MainControl mainControl) {
			super();
			this.mainControl = mainControl;
			this.mainControl.getNaviView().getTurnierListePanel()
					.setVisible(false);
			this.mainControl.getNaviView().getSpielerListePanel()
					.setVisible(false);
			if (this.mainControl.getNeuesTurnier() == true) {
				this.mainControl.getNaviView().getTabellenPanel()
						.setVisible(false);
				this.mainControl.getNaviView().getPairingsPanel()
						.setVisible(false);
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() instanceof JTabbedPane) {
				JTabbedPane pane = (JTabbedPane) e.getSource();
				int selectedIndex = pane.getSelectedIndex();
				String turnierName = Messages.getString("NaviController.27"); //$NON-NLS-1$
				if (this.mainControl.getTurnier() != null) {
					turnierName = this.mainControl.getTurnier()
							.getTurnierName();
					if (pane.getTitleAt(selectedIndex).equals(turnierName)
							|| pane.getTitleAt(selectedIndex).equals(
									Messages.getString("NaviController.28"))) {
						if (this.mainControl.getNeuesTurnier() == false) {
							if (pairingIsActive == true) {
								this.mainControl.getNaviView()
										.getTabellenPanel().setVisible(false);
								this.mainControl.getNaviView()
										.getPairingsPanel().setVisible(true);
							} else {
								this.mainControl.getNaviView()
										.getTabellenPanel().setVisible(true);
								this.mainControl.getNaviView()
										.getPairingsPanel().setVisible(false);
							}

						} else {
							this.mainControl.getNaviView().getTabellenPanel()
									.setVisible(false);
							this.mainControl.getNaviView().getPairingsPanel()
									.setVisible(false);
						}

					} else {
						this.mainControl.getNaviView().getTabellenPanel()
								.setVisible(false);
						this.mainControl.getNaviView().getPairingsPanel()
								.setVisible(false);
					}

				}

				if (pane.getTitleAt(selectedIndex).equals(
						Messages.getString("NaviController.29"))) { //$NON-NLS-1$
					this.mainControl.getNaviView().getTurnierListePanel()
							.setVisible(true);

				} else {
					this.mainControl.getNaviView().getTurnierListePanel()
							.setVisible(false);
				}
				if (pane.getTitleAt(selectedIndex).equals(
						Messages.getString("NaviController.30"))) { //$NON-NLS-1$
					this.mainControl.getNaviView().getSpielerListePanel()
							.setVisible(true);

				} else {
					this.mainControl.getNaviView().getSpielerListePanel()
							.setVisible(false);
				}

			}
		}

	}

}
