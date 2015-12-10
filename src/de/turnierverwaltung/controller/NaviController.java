package de.turnierverwaltung.controller;

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

	}

	public void makeNaviPanel() {
		JPanel hauptPanel = this.mainControl.getMainPanel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(naviView);
		hauptPanel.add(scrollPane, BorderLayout.WEST);
		this.mainControl.getHauptPanel().addChangeListener(new TurnierAnsicht(mainControl));
		dewisDialogControl = new DewisDialogControl(mainControl);
		hauptPanel.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (spielerHinzufuegenView != null) {
			if (arg0.getSource() == spielerHinzufuegenView.getOkButton()) {
				String name = spielerHinzufuegenView.getTextFieldName().getText();
				String kuerzel = spielerHinzufuegenView.getTextFieldKuerzel().getText();
				String dwz = spielerHinzufuegenView.getTextFieldDwz().getText();
				int age = spielerHinzufuegenView.getTextComboBoxAge().getSelectedIndex();
				Spieler neuerSpieler = new Spieler();
				neuerSpieler.setName(name);
				neuerSpieler.setKuerzel(kuerzel);
				neuerSpieler.setDwz(dwz);
				neuerSpieler.setAge(age);
				SpielerTableControl stc = new SpielerTableControl(mainControl);
				neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
				this.mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
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
				if (this.mainControl.getNaviView().getTabellenPanel().isVisible() == true) {
					aktiveGruppe = this.mainControl.getTabAnzeigeView().getSelectedIndex();
					this.mainControl.getTabAnzeigeView2()[aktiveGruppe].getSelectedIndex();
				}
			}
		}

		if (arg0.getSource() == pdfButton) {
			PDFSaveControler pdfsave = new PDFSaveControler(this.mainControl);
			pdfsave.savePDFFile();

		}
		if (arg0.getSource() == newTurnierButton) {
			Turnier turnier = this.mainControl.getTurnier();
			if (turnier == null) {
				mainControl.setTurnierControl(new TurnierControl(mainControl));
			} else {
				// Custom button text
				Object[] options = { "Ja", "Abbrechen" };
				int abfrage = JOptionPane.showOptionDialog(null,
						"Wollen Sie wirklich ein neues Turnier erstellen? " + "Alle eingegebenen Daten gehen verloren.",
						"A Silly Question", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
						options, options[1]);
				if (abfrage == 0) {
					// mainControl.resetApp();
					mainControl.setTurnierControl(new TurnierControl(mainControl));
				}
			}
		}

		if (arg0.getSource() == newdbButton) {

			int abfrage = warnHinweis();
			if (abfrage == 0) {
				// this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
				
				String filename = JOptionPane.showInputDialog(null, "Dateiname : ", "Eine Eingabeaufforderung",
						JOptionPane.PLAIN_MESSAGE);

				if (filename != null) {
					filename += ".ktv";

					JFileChooser savefile = new JFileChooser();
					FileFilter filter = new FileNameExtensionFilter("Turnier Datenbank", "ktv");
					savefile.addChoosableFileFilter(filter);
					savefile.setFileFilter(filter);
					savefile.setDialogType(JFileChooser.SAVE_DIALOG);
					savefile.setSelectedFile(new File(filename));
					BufferedWriter writer;
					int sf = savefile.showSaveDialog(null);
					if (sf == JFileChooser.APPROVE_OPTION) {
						mainControl.resetApp();
						mainControl.datenbankMenueView(false);
						try {
							File file = savefile.getSelectedFile();
							writer = new BufferedWriter(new FileWriter(savefile.getSelectedFile()));
							writer.write("");
							writer.close();

							// true for rewrite, false for override
							SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
							SQLiteControl sqlC = new SQLiteControl();
							sqlC.createAllTables();
							mainControl.datenbankMenueView(true);
							JOptionPane.showMessageDialog(null, "Datei wurde gespeichert.", "File Saved",
									JOptionPane.INFORMATION_MESSAGE);
							this.mainControl.setNeuesTurnier(false);
							mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
							mainControl.getTurnierTableControl().loadTurnierListe();
							mainControl.setSpielerEditierenControl(new SpielerLadenControl(mainControl));
							mainControl.getSpielerEditierenControl().updateSpielerListe();
							mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
							mainControl.getTurnierListeLadenControl().loadTurnier();

							mainControl.getPropertiesControl().setProperties("Path", SQLiteDAOFactory.getDB_PATH());
							mainControl.getPropertiesControl().writeProperties();

						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
						}
					} else if (sf == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
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
				FileFilter filter = new FileNameExtensionFilter("Turnier Datenbank", "ktv");
				fc.addChoosableFileFilter(filter);
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					mainControl.resetApp();
					mainControl.datenbankMenueView(false);
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
					mainControl.datenbankMenueView(true);
					// if (mainControl.getSpielerEditierenControl() != null) {
					// mainControl.getSpielerEditierenControl().makePanel();
					// } else {

					// }
					mainControl.setNeuesTurnier(false);
					// mainControl.getNaviView().getTabellenPanel().setVisible(false);
					mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
					mainControl.getTurnierTableControl().loadTurnierListe();
					mainControl.setSpielerEditierenControl(new SpielerLadenControl(mainControl));
					mainControl.getSpielerEditierenControl().updateSpielerListe();
					mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
					mainControl.getTurnierListeLadenControl().loadTurnier();
					naviView.setPathToDatabase(new JLabel(file.getName()));

					mainControl.getPropertiesControl().setProperties("Path", SQLiteDAOFactory.getDB_PATH());
					mainControl.getPropertiesControl().writeProperties();
				} else {
					JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
				}

			}

		}
		if (arg0.getSource() == naviView.getSpielerImport()) {
			SpielerTableImportController spielerImport = new SpielerTableImportController();
			spielerImport.importSpielerTable();
			mainControl.getSpielerLadenControl().updateSpielerListe();
		}
		if (arg0.getSource() == naviView.getSpielerExport()) {
			SpielerTableExportController spielerExport = new SpielerTableExportController(this.mainControl);
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
			Boolean ok = mainControl.getRundenEingabeFormularControl().checkNewTurnier();
			if (ok) {
				makeNewTables();

			} else {
				JOptionPane.showMessageDialog(null, "Erst nach der Eingabe aller Gruppen\n" + "möglich.");
			}

		}
		if (arg0.getSource() == naviView.getTabelleSpeichernButton())

		{
			Boolean ok = this.mainControl.getSaveTurnierControl().saveChangedPartien();

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
				int changedGroups = mainControl.getRundenEingabeFormularControl().getChangedGroups()[i][x];
				if (changedGroups >= STANDARD) {
					this.mainControl.getTurnierTabelleControl().okAction(i);

					if (x == PAARUNGSTABELLE) {
						if (changedGroups == SORTIEREN) {
							Arrays.sort(mainControl.getTurnier().getGruppe()[i].getPartien());
						}
						mainControl.getRundenEingabeFormularControl().makeNewFormular(i);
					}
					mainControl.getTurnierTabelleControl().makeSimpleTableView(i);
					mainControl.getTerminTabelleControl().makeSimpleTableView(i);
					mainControl.getRundenEingabeFormularControl().getChangedGroups()[i][x] = 0;
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
		String hinweisText = "Alle Änderungen gehen eventuell verloren "
				+ "\nwenn Sie die Tabellen nicht gespeichert haben." + "\nMöchten Sie den Menüpunkt trotzdem laden?";
		if (mainControl.getMenueControl().getWarnHinweis()
				&& this.mainControl.getNaviView().getTabellenPanel().isVisible() == true) {
			abfrage = 1;
			// Custom button text
			Object[] options = { "Ja", "Abbrechen" };
			abfrage = JOptionPane.showOptionDialog(null, hinweisText, "Meldung", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		}
		if (abfrage == 0) {
			mainControl.setNeuesTurnier(false);

		}
		return abfrage;
	}

	class TurnierAnsicht implements ChangeListener {

		private MainControl mainControl;

		public TurnierAnsicht(MainControl mainControl) {
			super();
			this.mainControl = mainControl;
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() instanceof JTabbedPane) {
				JTabbedPane pane = (JTabbedPane) e.getSource();
				int selectedIndex = pane.getSelectedIndex();
				String turnierName = "";
				if (this.mainControl.getTurnier() != null) {
					turnierName = this.mainControl.getTurnier().getTurnierName();
					if (pane.getTitleAt(selectedIndex).equals(turnierName) || pane.getTitleAt(selectedIndex).equals("Neues Turnier")) {
						this.mainControl.getNaviView().getTabellenPanel().setVisible(true);

					} else {
						this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
					}

				}
				
				if (pane.getTitleAt(selectedIndex).equals("Turnierliste")) {
					this.mainControl.getNaviView().getTurnierListePanel().setVisible(true);

				} else {
					this.mainControl.getNaviView().getTurnierListePanel().setVisible(false);
				}
				if (pane.getTitleAt(selectedIndex).equals("Spielerliste")) {
					this.mainControl.getNaviView().getSpielerListePanel().setVisible(true);

				} else {
					this.mainControl.getNaviView().getSpielerListePanel().setVisible(false);
				}

			}
		}

	}

}
