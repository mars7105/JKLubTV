package de.turnierverwaltung.controller;

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
import java.util.Arrays;

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

public class NaviController implements ActionListener {

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

	public NaviController(MainControl mainControl) {

		this.mainControl = mainControl;

		naviView = new NaviView();
		mainControl.setNaviView(naviView);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
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
				String kuerzel = spielerHinzufuegenView.getTextFieldKuerzel()
						.getText();
				String dwz = spielerHinzufuegenView.getTextFieldDwz().getText();
				int age = spielerHinzufuegenView.getTextComboBoxAge()
						.getSelectedIndex();
				Spieler neuerSpieler = new Spieler();
				neuerSpieler.setName(name);
				neuerSpieler.setKuerzel(kuerzel);
				neuerSpieler.setDwz(dwz);
				neuerSpieler.setAge(age);
				SpielerTableControl stc = new SpielerTableControl(mainControl);
				neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
				this.mainControl.getSpielerLadenControl().getSpieler()
						.add(neuerSpieler);
				spielerHinzufuegenView.getTextFieldName().setEditable(false);
				spielerHinzufuegenView.getTextFieldKuerzel().setEditable(false);
				spielerHinzufuegenView.getTextFieldDwz().setEditable(false);
				spielerHinzufuegenView.getTextComboBoxAge().setEnabled(false);
				spielerHinzufuegenView.spielerPanel();

			}

			if (arg0.getSource() == spielerHinzufuegenView.getCancelButton()) {
				mainControl.setEnabled(true);
				this.mainControl.getSpielerLadenControl().updateSpielerListe();

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
			PDFSaveControler pdfsave = new PDFSaveControler(this.mainControl);
			pdfsave.savePDFFile();

		}
		if (arg0.getSource() == newTurnierButton) {
			mainControl.setSpielerEingabeControl(null);
			Turnier turnier = this.mainControl.getTurnier();
			if (turnier == null) {
				mainControl.setTurnierControl(new TurnierControl(mainControl));
			} else {
				// Custom button text
				Object[] options = { Messages.getString("NaviController.0"), Messages.getString("NaviController.1") }; //$NON-NLS-1$ //$NON-NLS-2$
				int abfrage = JOptionPane.showOptionDialog(null,
						Messages.getString("NaviController.2") //$NON-NLS-1$
								+ Messages.getString("NaviController.3"), //$NON-NLS-1$
						Messages.getString("NaviController.4"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
						JOptionPane.WARNING_MESSAGE, null, options, options[1]);
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

				String filename = JOptionPane.showInputDialog(null,
						Messages.getString("NaviController.5"), Messages.getString("NaviController.6"), //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.PLAIN_MESSAGE);

				if (filename != null) {
					filename += ".ktv"; //$NON-NLS-1$

					JFileChooser savefile = new JFileChooser();
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
							writer.write(Messages.getString("NaviController.10")); //$NON-NLS-1$
							writer.close();

							// true for rewrite, false for override
							SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
							SQLiteControl sqlC = new SQLiteControl();
							sqlC.createAllTables();
							// mainControl.datenbankMenueView(true);
							JOptionPane.showMessageDialog(null,
									Messages.getString("NaviController.11"), Messages.getString("NaviController.12"), //$NON-NLS-1$ //$NON-NLS-2$
									JOptionPane.INFORMATION_MESSAGE);
							this.mainControl.setNeuesTurnier(false);
							mainControl
									.setTurnierTableControl(new TurnierTableControl(
											mainControl));
							mainControl.getTurnierTableControl()
									.loadTurnierListe();
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

							mainControl.getPropertiesControl().setPath(
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
				JFileChooser fc = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter(
						Messages.getString("NaviController.15"), Messages.getString("NaviController.16")); //$NON-NLS-1$ //$NON-NLS-2$
				fc.addChoosableFileFilter(filter);
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					mainControl.resetApp();
					// mainControl.datenbankMenueView(false);
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
					// mainControl.datenbankMenueView(true);
					// if (mainControl.getSpielerEditierenControl() != null) {
					// mainControl.getSpielerEditierenControl().makePanel();
					// } else {

					// }
					mainControl.setNeuesTurnier(false);
					// mainControl.getNaviView().getTabellenPanel().setVisible(false);
					mainControl.setTurnierTableControl(new TurnierTableControl(
							mainControl));
					mainControl.getTurnierTableControl().loadTurnierListe();
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
					naviView.setPathToDatabase(new JLabel(file.getName()));

					mainControl.getPropertiesControl().setPath(
							SQLiteDAOFactory.getDB_PATH());
					mainControl.getPropertiesControl().writeProperties();
					turnierAnsicht = new TurnierAnsicht(mainControl);
					mainControl.getHauptPanel().addChangeListener(
							turnierAnsicht);
					for (int i = 0; i < mainControl.getHauptPanel()
							.getTabCount(); i++) {
						if (mainControl.getHauptPanel().getTitleAt(i)
								.equals(Messages.getString("NaviController.17"))) { //$NON-NLS-1$
							mainControl.getHauptPanel().setSelectedIndex(i);
						}
					}
					naviView.updateUI();

				} else {
					JOptionPane.showMessageDialog(null, Messages.getString("NaviController.18")); //$NON-NLS-1$
				}

			}

		}
		if (arg0.getSource() == naviView.getSpielerImport()) {
			SpielerTableImportController spielerImport = new SpielerTableImportController();
			spielerImport.importSpielerTable();
			mainControl.getSpielerLadenControl().updateSpielerListe();
		}
		if (arg0.getSource() == naviView.getSpielerExport()) {
			SpielerTableExportController spielerExport = new SpielerTableExportController(
					this.mainControl);
			spielerExport.exportSpielerTable();
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
				JOptionPane.showMessageDialog(null,
						Messages.getString("NaviController.19") + Messages.getString("NaviController.20")); //$NON-NLS-1$ //$NON-NLS-2$
			}

		}
		if (arg0.getSource() == naviView.getTabelleSpeichernButton())

		{
			Boolean ok = this.mainControl.getSaveTurnierControl()
					.saveChangedPartien();

			if (ok) {
				makeNewTables();

			}

		}

		if (arg0.getSource() == naviView.getTabelleHTMLAusgabeButton())

		{
			HTMLSaveControler HTMLSave = new HTMLSaveControler(this.mainControl);
			HTMLSave.saveHTMLFile();

		}

	}

	private void makeNewTables() {
		int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();
		for (int i = 0; i < anzahlGruppen; i++) {
			for (int x = 0; x < 3; x++) {
				int changedGroups = mainControl
						.getRundenEingabeFormularControl().getChangedGroups()[i][x];
				if (changedGroups >= STANDARD) {
					this.mainControl.getTurnierTabelleControl().okAction(i);

					if (x == TURNIERTABELLE) {
						mainControl.getTurnierTabelleControl()
								.makeSimpleTableView(i);
					}
					if (x == TERMINTABELLE) {
						mainControl.getTerminTabelleControl()
								.makeSimpleTableView(i);
					}
					if (x == PAARUNGSTABELLE) {
						if (changedGroups == SORTIEREN) {
							Arrays.sort(mainControl.getTurnier().getGruppe()[i]
									.getPartien());
						}
						mainControl.getRundenEingabeFormularControl()
								.makeNewFormular(i);
					}
					mainControl.getRundenEingabeFormularControl()
							.getChangedGroups()[i][x] = 0;
				}

			}
		}
	}

	public void neuerSpieler() {
		this.mainControl.getSpielerLadenControl().updateSpielerListe();
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
			Object[] options = { Messages.getString("NaviController.24"), Messages.getString("NaviController.25") }; //$NON-NLS-1$ //$NON-NLS-2$
			abfrage = JOptionPane.showOptionDialog(null, hinweisText,
					Messages.getString("NaviController.26"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);

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
									Messages.getString("NaviController.28"))) { //$NON-NLS-1$
						this.mainControl.getNaviView().getTabellenPanel()
								.setVisible(true);

					} else {
						this.mainControl.getNaviView().getTabellenPanel()
								.setVisible(false);
					}

				}

				if (pane.getTitleAt(selectedIndex).equals(Messages.getString("NaviController.29"))) { //$NON-NLS-1$
					this.mainControl.getNaviView().getTurnierListePanel()
							.setVisible(true);

				} else {
					this.mainControl.getNaviView().getTurnierListePanel()
							.setVisible(false);
				}
				if (pane.getTitleAt(selectedIndex).equals(Messages.getString("NaviController.30"))) { //$NON-NLS-1$
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
