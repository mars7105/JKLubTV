package de.turnierverwaltung.controller;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	private int aktiveTabelle;
	private JButton pdfButton;

	public NaviController(MainControl mainControl) {

		this.mainControl = mainControl;

		naviView = new NaviView();
		mainControl.setNaviView(naviView);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		this.mainControl.getNaviView().getPaarungsPanel().setVisible(false);
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
		naviView.getPaarungenSpeichernButton().addActionListener(this);
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
		if (this.mainControl.getTabAnzeigeView() != null) {

			if (this.mainControl.getTabAnzeigeView2() != null) {
				if (this.mainControl.getNaviView().getTabellenPanel().isVisible() == true
						|| mainControl.getNaviView().getPaarungsPanel().isVisible() == true) {
					aktiveGruppe = this.mainControl.getTabAnzeigeView().getSelectedIndex();
					aktiveTabelle = this.mainControl.getTabAnzeigeView2()[aktiveGruppe].getSelectedIndex();
				}
			}
		}
		if (arg0.getSource() == naviView.getPaarungenSpeichernButton()) {
			aktiveGruppe = this.mainControl.getTabAnzeigeView().getSelectedIndex();
			// partien = gruppe[aktiveGruppe].getPartien();

			if (mainControl.getRundenEingabeFormularControl() == null) {
				mainControl.setRundenEingabeFormularControl(new RundenEingabeFormularControl(mainControl));
				mainControl.getRundenEingabeFormularControl().makeRundenEditView(aktiveGruppe);
				mainControl.getRundenEingabeFormularControl().changeWerte(aktiveGruppe);
				mainControl.getRundenEingabeFormularControl().saveTurnier(aktiveGruppe);
				mainControl.getRundenEingabeFormularControl().makeRundenEditView(aktiveGruppe);
			} else {
				int selectedPaarungen = this.mainControl.getRundenEingabeFormularView()[aktiveGruppe].getTabbedPane()
						.getSelectedIndex();
				mainControl.getRundenEingabeFormularControl().changeWerte(aktiveGruppe);
				mainControl.getRundenEingabeFormularControl().saveTurnier(aktiveGruppe);
				mainControl.getRundenEingabeFormularControl().makeRundenEditView(aktiveGruppe);
				this.mainControl.getRundenEingabeFormularView()[aktiveGruppe].getTabbedPane()
						.setSelectedIndex(selectedPaarungen);

			}
		}

		if (arg0.getSource() == pdfButton) {
			aktiveGruppe = this.mainControl.getTabAnzeigeView().getSelectedIndex();
			aktiveTabelle = this.mainControl.getTabAnzeigeView2()[aktiveGruppe].getSelectedIndex();

			int spalte = this.mainControl.getSimpleTableView()[aktiveGruppe].getTable().getModel().getColumnCount();
			int zeile = this.mainControl.getSimpleTableView()[aktiveGruppe].getTable().getModel().getRowCount();
			for (int x = 0; x < spalte; x++) {
				for (int y = 0; y < zeile; y++) {

					this.mainControl.getTurnierTabelle()[aktiveGruppe].getTabellenMatrix()[x][y
							+ 1] = (String) this.mainControl.getSimpleTableView()[aktiveGruppe].getTable().getValueAt(y,
									x);

				}
			}

			PDFTabellenAusgabe mft = new PDFTabellenAusgabe();

			String filename = JOptionPane.showInputDialog(null, "Dateiname : ", "Eine Eingabeaufforderung",
					JOptionPane.PLAIN_MESSAGE);
			if (filename != null) {
				filename += ".pdf";

				JFileChooser savefile = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("PDF Datei", "pdf");
				savefile.addChoosableFileFilter(filter);
				savefile.setFileFilter(filter);
				savefile.setDialogType(JFileChooser.SAVE_DIALOG);
				savefile.setSelectedFile(new File(filename));
				int sf = savefile.showSaveDialog(null);
				if (sf == JFileChooser.APPROVE_OPTION) {

					if (aktiveTabelle == 0) {
						mainControl.getTurnierTabelle()[aktiveGruppe].createMatrix();
						String titel = "Kreuztabelle " + mainControl.getTurnier().getTurnierName() + " - "
								+ mainControl.getTurnier().getGruppe()[aktiveGruppe].getGruppenName();
						String pathName = savefile.getSelectedFile().getAbsolutePath();
						mft.createTurnierPdf(titel, pathName,
								mainControl.getTurnierTabelle()[aktiveGruppe].getTabellenMatrix());
						// JOptionPane.showMessageDialog(null, "Datei
						// gespeichert.");

					} else {
						mainControl.getTerminTabelle()[aktiveGruppe].createTerminTabelle();
						String titel = "Termintabelle " + mainControl.getTurnier().getTurnierName() + " - "
								+ mainControl.getTurnier().getGruppe()[aktiveGruppe].getGruppenName();
						String pathName = savefile.getSelectedFile().getAbsolutePath();
						mft.createTerminPdf(titel, pathName,
								mainControl.getTerminTabelle()[aktiveGruppe].getTabellenMatrix());
						// JOptionPane.showMessageDialog(null, "Datei
						// gespeichert.");
					}
					File file = savefile.getSelectedFile();
					// first check if Desktop is supported by
					// Platform or not
					if (!Desktop.isDesktopSupported()) {
						JOptionPane.showMessageDialog(null, "Desktop is not supported.", "File not opened",
								JOptionPane.INFORMATION_MESSAGE);
					} else {

						Desktop desktop = Desktop.getDesktop();
						if (file.exists()) {
							try {
								desktop.open(file);
							} catch (IOException e) {
								JOptionPane.showMessageDialog(null, "Desktop is not supported.", "File not opened",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					}

				} else {
					JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
				}
			}

		}
		if (arg0.getSource() == infoButton) {
			int abfrage = warnHinweis();
			if (abfrage == 0) {
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
				mainControl.getNaviView().getTabellenPanel().setVisible(false);
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
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
						}
					} else if (sf == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
					}
				}
			}
		}
		if (arg0.getSource() == loaddbButton) {

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

				} else {
					JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
				}

			}

		}
		if (arg0.getSource() == turnierListeButton) {
			int abfrage = warnHinweis();
			if (abfrage == 0) {
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
		if (arg0.getSource() == spielerListeButton) {
			int abfrage = warnHinweis();
			if (abfrage == 0) {
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

		if (arg0.getSource() == naviView.getTabelleAktualisierenButton()) {
			this.mainControl.getTurnierTabelleControl().okAction(aktiveGruppe);

		}
		if (arg0.getSource() == naviView.getTabelleSpeichernButton()) {
			this.mainControl.getSaveTurnierControl().saveTurnier(aktiveGruppe);
		}

		if (arg0.getSource() == naviView.getTabelleHTMLAusgabeButton()) {
			this.mainControl.getTurnierTabelle()[aktiveGruppe].createMatrix();
			int spalte = this.mainControl.getSimpleTableView()[aktiveGruppe].getTable().getModel().getColumnCount();
			int zeile = this.mainControl.getSimpleTableView()[aktiveGruppe].getTable().getModel().getRowCount();
			for (int x = 0; x < spalte; x++) {
				for (int y = 0; y < zeile; y++) {

					this.mainControl.getTurnierTabelle()[aktiveGruppe].getTabellenMatrix()[x][y
							+ 1] = (String) this.mainControl.getSimpleTableView()[aktiveGruppe].getTable().getValueAt(y,
									x);

				}
			}
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
				// BufferedWriter writer;
				Writer writer;
				int sf = savefile.showSaveDialog(null);
				if (sf == JFileChooser.APPROVE_OPTION) {
					try {
						// Construct a writer for a specific encoding
						writer = new OutputStreamWriter(new FileOutputStream(savefile.getSelectedFile()), "UTF8");

						// writer = new BufferedWriter(new
						// FileWriter(savefile.getSelectedFile()));
						if (aktiveTabelle == 0) {

							writer.write(this.mainControl.getTurnierTabelle()[aktiveGruppe].getHTMLTable());
						} else {

							writer.write(this.mainControl.getTerminTabelleControl().getTerminTabelle()[aktiveGruppe]
									.getHTMLTable());
						}

						writer.flush();
						writer.close();
						try {
							InputStreamReader isReader = new InputStreamReader(
									this.getClass().getResourceAsStream("/files/style.css"));
							BufferedReader br = new BufferedReader(isReader);

							PrintWriter writer1 = new PrintWriter(
									new File(savefile.getCurrentDirectory() + "/style.css"));

							String Bs;
							while ((Bs = br.readLine()) != null) {
								writer1.println(Bs);
							}

							writer1.close();
							br.close();

						} catch (FileNotFoundException fnfe) {
							JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
						} catch (IOException ioe) {
							JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
						}
						File file = savefile.getSelectedFile();
						// first check if Desktop is supported by
						// Platform or not
						if (!Desktop.isDesktopSupported()) {
							JOptionPane.showMessageDialog(null, "Desktop is not supported.", "File not opened",
									JOptionPane.INFORMATION_MESSAGE);
						} else {

							Desktop desktop = Desktop.getDesktop();
							if (file.exists()) {
								desktop.open(file);
							}

						}

					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
					}
				} else if (sf == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
				}
			}

		}

	}

	private int warnHinweis() {
		int abfrage = 0;
		String hinweisText = "Alle Änderungen gehen eventuell verloren "
				+ "\nwenn Sie die Tabellen nicht gespeichert haben." + "\nMöchten Sie den Menüpunkt trotzdem laden?";
		if (mainControl.getMenueControl().getWarnHinweis()
				&& (this.mainControl.getNaviView().getTabellenPanel().isVisible() == true ||
						this.mainControl.getNaviView().getPaarungsPanel().isVisible() == true)) {
			abfrage = 1;
			// Custom button text
			Object[] options = { "Ja", "Abbrechen" };
			abfrage = JOptionPane.showOptionDialog(null, hinweisText, "Meldung", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		}

		return abfrage;
	}
}
