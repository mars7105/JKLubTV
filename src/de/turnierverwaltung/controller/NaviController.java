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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.view.NaviView;

public class NaviController implements ActionListener {

	private MainControl mainControl;
	private JButton spielerListeButton;
	private JButton turnierListeButton;
	private JButton newdbButton;
	private JButton loaddbButton;
	private JButton infoButton;
	private NaviView naviView;
	// private HTMLTabelleView htmlTabelleView;
	private int aktiveGruppe;
	private JButton pdfButton;

	public NaviController(MainControl mainControl) {

		this.mainControl = mainControl;

		naviView = new NaviView();
		mainControl.setNaviView(naviView);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		newdbButton = naviView.getNewDatabseButton();
		newdbButton.addActionListener(this);
		loaddbButton = naviView.getLoadDatabaseButton();
		loaddbButton.addActionListener(this);
		turnierListeButton = naviView.getTurnierListeButton();
		turnierListeButton.addActionListener(this);
		spielerListeButton = naviView.getSpielerListeButton();
		spielerListeButton.addActionListener(this);
		infoButton = naviView.getInfoButton();
		infoButton.addActionListener(this);
		pdfButton = naviView.getPdfSpeichernButton();
		pdfButton.addActionListener(this);
		naviView.getTabelleAktualisierenButton().addActionListener(this);
		naviView.getTabelleSpeichernButton().addActionListener(this);
		naviView.getTabelleHTMLAusgabeButton().addActionListener(this);
		aktiveGruppe = 0;
		makeNaviPanel();

	}

	public void makeNaviPanel() {
		JPanel hauptPanel = this.mainControl.getHauptPanel();
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
					aktiveGruppe = this.mainControl.getTabAnzeigeView().getSelectedIndex();
					this.mainControl.getTabAnzeigeView2()[aktiveGruppe].getSelectedIndex();
				}
			}
		}

		if (arg0.getSource() == pdfButton) {
			PDFSaveControler pdfsave = new PDFSaveControler(this.mainControl);
			pdfsave.savePDFFile();

		}
		if (arg0.getSource() == infoButton) {
			int abfrage = warnHinweis();
			if (abfrage == 0) {

				mainControl.resetApp();
				if (SQLiteDAOFactory.getDB_PATH() == null) {
					mainControl.datenbankMenueView(false);
				}
				if (mainControl.getInfoController() == null) {
					mainControl.setInfoController(new InfoController(this.mainControl));
				} else {
					mainControl.getInfoController().makeInfoPanel();
				}
				this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
				this.mainControl.setNeuesTurnier(false);
			}
		}
		if (arg0.getSource() == newdbButton) {

			int abfrage = warnHinweis();
			if (abfrage == 0) {
				this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
				mainControl.resetApp();
				mainControl.datenbankMenueView(false);
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
							if (mainControl.getTurnierTableControl() == null) {
								this.mainControl.setNeuesTurnier(false);
								mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
								mainControl.getTurnierTableControl().loadTurnierListe();
								mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
								mainControl.getTurnierListeLadenControl().loadTurnier();
							} else {
								mainControl.resetApp();
								mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
								mainControl.getTurnierTableControl().loadTurnierListe();
								mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
								mainControl.getTurnierListeLadenControl().loadTurnier();
							}
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
				this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
				mainControl.getNaviView().getTabellenPanel().setVisible(false);
				mainControl.resetApp();
				mainControl.datenbankMenueView(false);
				// Create a file chooser
				JFileChooser fc = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("Turnier Datenbank", "ktv");
				fc.addChoosableFileFilter(filter);
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
					mainControl.datenbankMenueView(true);
					if (mainControl.getTurnierTableControl() == null) {
						mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
						mainControl.getTurnierTableControl().loadTurnierListe();
						mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
						mainControl.getTurnierListeLadenControl().loadTurnier();
						naviView.setPathToDatabase(new JLabel(file.getName()));

					} else {
						mainControl.resetApp();
						mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
						mainControl.getTurnierTableControl().loadTurnierListe();
						mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
						mainControl.getTurnierListeLadenControl().loadTurnier();
						naviView.setPathToDatabase(new JLabel(file.getPath()));
					}
					mainControl.getPropertiesControl().setProperties("Path", SQLiteDAOFactory.getDB_PATH());
					mainControl.getPropertiesControl().writeProperties();
				} else {
					JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
				}

			}

		}
		if (arg0.getSource() == turnierListeButton)

		{
			int abfrage = warnHinweis();
			if (abfrage == 0) {
				mainControl.resetApp();
				if (mainControl.getTurnierListeLadenControl() != null) {
					mainControl.getTurnierListeLadenControl().loadTurnier();
				} else {
					mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(mainControl));
					mainControl.getTurnierListeLadenControl().loadTurnier();
				}
				this.mainControl.setNeuesTurnier(false);
				this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
				mainControl.getNaviView().getTabellenPanel().setVisible(false);
			}
		}
		if (arg0.getSource() == spielerListeButton)

		{
			int abfrage = warnHinweis();
			if (abfrage == 0) {
				mainControl.resetApp();
				if (mainControl.getSpielerEditierenControl() != null) {
					mainControl.getSpielerEditierenControl().makePanel();
				} else {
					mainControl.setSpielerEditierenControl(new SpielerLadenControl(mainControl));
					mainControl.getSpielerEditierenControl().makePanel();
				}
				this.mainControl.setNeuesTurnier(false);
				this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
				mainControl.getNaviView().getTabellenPanel().setVisible(false);
			}
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
				if (mainControl.getRundenEingabeFormularControl().getChangedGroups()[i][x] == 1) {
					this.mainControl.getTurnierTabelleControl().okAction(i);
					
					if (x == 2) {
						Arrays.sort(mainControl.getTurnier().getGruppe()[i].getPartien());
						mainControl.getRundenEingabeFormularControl().makeNewFormular(i);
					}
					mainControl.getTurnierTabelleControl().makeSimpleTableView(i);
					mainControl.getTerminTabelleControl().makeSimpleTableView(i);
					mainControl.getRundenEingabeFormularControl().getChangedGroups()[i][x] = 0;
				}
			}
		}
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
}
