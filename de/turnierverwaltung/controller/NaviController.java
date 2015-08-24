package de.turnierverwaltung.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.view.HTMLTabelleView;
import de.turnierverwaltung.view.NaviView;

public class NaviController implements ActionListener {

	private MainControl mainControl;
	private JButton spielerListeButton;
	private JButton turnierListeButton;
	private JButton newdbButton;
	private JButton loaddbButton;
	private JButton infoButton;
	private NaviView naviView;
	private HTMLTabelleView htmlTabelleView;
	private int aktiveGruppe;
	private int aktiveTabelle;

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
		naviView.getTabelleAktualisierenButton().addActionListener(this);
		naviView.getTabelleSpeichernButton().addActionListener(this);
		naviView.getTabelleHTMLAusgabeButton().addActionListener(this);
		aktiveGruppe = 0;
		aktiveTabelle = 0;
		makeNaviPanel();

	}

	public void makeNaviPanel() {
		JPanel hauptPanel = this.mainControl.getHauptPanel();
		hauptPanel.add(naviView, BorderLayout.WEST);
		hauptPanel.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == infoButton) {
			if (mainControl.getInfoController() == null) {
				mainControl.setInfoController(new InfoController(this.mainControl));
			} else {
				mainControl.getInfoController().makeInfoPanel();
			}
			this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		}
		if (arg0.getSource() == newdbButton) {
			this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
			int abfrage = warnHinweis(
					"Wollen Sie wirklich die Seite verlassen? \n" + "Alle eingegebenen Daten gehen verloren.");
			if (abfrage == 0) {
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
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (sf == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
					}
				}
			}
		}
		if (arg0.getSource() == loaddbButton) {
			this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
			int abfrage = warnHinweis(
					"Wollen Sie wirklich die Seite verlassen? \n" + "Alle eingegebenen Daten gehen verloren.");
			if (abfrage == 0) {
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

				} else {
					JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
				}

			}

		}
		if (arg0.getSource() == turnierListeButton) {
			if (mainControl.getTurnierListeLadenControl() != null) {
				mainControl.getTurnierListeLadenControl().loadTurnier();
			} else {
				mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(mainControl));
				mainControl.getTurnierListeLadenControl().loadTurnier();
			}
			this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		}
		if (arg0.getSource() == spielerListeButton) {
			if (mainControl.getSpielerEditierenControl() != null) {
				mainControl.getSpielerEditierenControl().makePanel();
			} else {
				mainControl.setSpielerEditierenControl(new SpielerLadenControl(mainControl));
				mainControl.getSpielerEditierenControl().makePanel();
			}
			this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		}
		aktiveGruppe = this.mainControl.getTabAnzeigeView().getSelectedIndex();
		if (this.mainControl.getTurnierTabelleControl() != null && aktiveGruppe >= 0) {
			aktiveGruppe = this.mainControl.getTabAnzeigeView().getSelectedIndex();
			aktiveTabelle = this.mainControl.getTabAnzeigeView2()[aktiveGruppe].getSelectedIndex();
			if (arg0.getSource() == naviView.getTabelleAktualisierenButton()) {
				this.mainControl.getTurnierTabelleControl().okAction(aktiveGruppe);

			}
			if (arg0.getSource() == naviView.getTabelleSpeichernButton()) {
				this.mainControl.getSaveTurnierControl().saveTurnier(aktiveGruppe);
			}
			if (aktiveTabelle == 0) {

				if (arg0.getSource() == naviView.getTabelleHTMLAusgabeButton()) {

					int spalte = this.mainControl.getSimpleTableView()[aktiveGruppe].getTable().getModel()
							.getColumnCount();
					int zeile = this.mainControl.getSimpleTableView()[aktiveGruppe].getTable().getModel().getRowCount();
					for (int x = 0; x < spalte; x++) {
						for (int y = 0; y < zeile; y++) {

							this.mainControl.getTurnierTabelle()[aktiveGruppe].getTabellenMatrix()[x][y
									+ 1] = (String) this.mainControl.getSimpleTableView()[aktiveGruppe].getTable()
											.getValueAt(y, x);

						}
					}
					htmlTabelleView = new HTMLTabelleView();
					htmlTabelleView.makeHTMLFrame(this.mainControl.getTurnierTabelle()[aktiveGruppe].getHTMLTable(),
							"Turniertabelle Gruppe "
									+ this.mainControl.getTurnier().getGruppe()[aktiveGruppe].getGruppenName());
					htmlTabelleView.getCloseButton().addActionListener(this);
					htmlTabelleView.getSaveButton().addActionListener(this);
				}

			} else {
				if (arg0.getSource() == naviView.getTabelleHTMLAusgabeButton()) {
					int spalte = this.mainControl.getSimpleTerminTabelleView()[aktiveGruppe].getTable().getModel()
							.getColumnCount();
					int zeile = this.mainControl.getSimpleTerminTabelleView()[aktiveGruppe].getTable().getModel()
							.getRowCount();
					for (int x = 0; x < spalte; x++) {
						for (int y = 0; y < zeile; y++) {

							this.mainControl.getTerminTabelleControl().getTerminTabelle()[aktiveGruppe]
									.getTabellenMatrix()[x][y
											+ 1] = (String) this.mainControl.getSimpleTerminTabelleView()[aktiveGruppe]
													.getTable().getValueAt(y, x);

						}
					}
					htmlTabelleView = new HTMLTabelleView();
					htmlTabelleView.makeHTMLFrame(
							this.mainControl.getTerminTabelleControl().getTerminTabelle()[aktiveGruppe].getHTMLTable(),
							"Termintabelle Gruppe "
									+ this.mainControl.getTurnier().getGruppe()[aktiveGruppe].getGruppenName());
					htmlTabelleView.getCloseButton().addActionListener(this);
					htmlTabelleView.getSaveButton().addActionListener(this);
				}
			}

			if (htmlTabelleView != null) {
				if (arg0.getSource() == htmlTabelleView.getSaveButton()) {

					String filename = JOptionPane.showInputDialog(null, "Dateiname : ", "Eine Eingabeaufforderung",
							JOptionPane.PLAIN_MESSAGE);
					if (filename != null) {
						filename += ".html";

						JFileChooser savefile = new JFileChooser();
						FileFilter filter = new FileNameExtensionFilter("HTML", "html");
						savefile.addChoosableFileFilter(filter);
						savefile.setFileFilter(filter);
						savefile.setDialogType(JFileChooser.SAVE_DIALOG);
						savefile.setSelectedFile(new File(filename));
						BufferedWriter writer;
						int sf = savefile.showSaveDialog(null);
						if (sf == JFileChooser.APPROVE_OPTION) {
							try {
								writer = new BufferedWriter(new FileWriter(savefile.getSelectedFile()));
								if (aktiveTabelle == 0) {
									writer.write(this.mainControl.getTurnierTabelle()[aktiveGruppe].getHTMLTable());
								} else {
									writer.write(
											this.mainControl.getTerminTabelleControl().getTerminTabelle()[aktiveGruppe]
													.getHTMLTable());
								}
								writer.close();
								JOptionPane.showMessageDialog(null, "Datei wurde gespeichert.", "File Saved",
										JOptionPane.INFORMATION_MESSAGE);

							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if (sf == JFileChooser.CANCEL_OPTION) {
							JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
						}
					}
				}
				if (arg0.getSource() == htmlTabelleView.getCloseButton()) {
					htmlTabelleView.closeWindow();
				}
			}
		}
	}

	private int warnHinweis(String hinweisText) {
		int abfrage = 0;
		if (mainControl.getMenueControl().getWarnHinweis()) {
			abfrage = 1;
			// Custom button text
			Object[] options = { "Ja", "Abbrechen" };
			abfrage = JOptionPane.showOptionDialog(null, hinweisText, "Meldung", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		}

		return abfrage;
	}
}
