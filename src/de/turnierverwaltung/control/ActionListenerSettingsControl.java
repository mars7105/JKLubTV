package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.view.SettingsView;

public class ActionListenerSettingsControl {
	private MainControl mainControl;
	private SettingsControl esControl;
	private JDialog dialog;

	public ActionListenerSettingsControl(MainControl mainControl, SettingsControl esControl) {
		super();
		this.mainControl = mainControl;
		this.esControl = esControl;
		addPropertiesActionListener();
	}

	private void addPropertiesActionListener() {
		mainControl.getNaviView().getPropertiesButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				mainControl.getEigenschaftenControl().setEigenschaftenView(new SettingsView());
				SettingsView eigenschaftenView = mainControl.getEigenschaftenControl().getEigenschaftenView();
				mainControl.getEigenschaftenControl().setItemListenerControl(
						new ItemListenerSettingsControl(mainControl, mainControl.getEigenschaftenControl()));
				mainControl.getEigenschaftenControl().getItemListenerControl().addItemListeners();
				if (dialog == null) {
					dialog = new JDialog();
				} else {
					dialog.dispose();
					dialog = new JDialog();
				}
				dialog.setAlwaysOnTop(true);
				dialog.getContentPane().add(eigenschaftenView);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setEnabled(true);
				dialog.setVisible(true);
				if (mainControl.getPropertiesControl() == null) {

					mainControl.setPropertiesControl(new PropertiesControl(mainControl));

				}
				PropertiesControl ppC = mainControl.getPropertiesControl();
				ppC.readProperties();
				eigenschaftenView.getCheckBoxHeaderFooter().setSelected(ppC.getOnlyTables());
				eigenschaftenView.getCheckBoxohneDWZ().setSelected(ppC.getNoDWZ());
				eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(ppC.getNoFolgeDWZ());
				eigenschaftenView.getCheckBoxohneELO().setSelected(ppC.getNoELO());
				eigenschaftenView.getCheckBoxohneFolgeELO().setSelected(ppC.getNoFolgeELO());
				eigenschaftenView.getSpielerListeAuswahlBox().setSelectedIndex(ppC.getSpielerProTab());
				eigenschaftenView.getTurnierListeAuswahlBox().setSelectedIndex(ppC.getTurniereProTab());
				eigenschaftenView.getForenameLengthBox().setValue(ppC.getCutForename());
				eigenschaftenView.getSurnameLengthBox().setValue(ppC.getCutSurname());
				eigenschaftenView.getWebserverPathTextField().setText(ppC.getWebserverPath());
				// eigenschaftenView.getCheckBoxohneDWZ().setSelected(ppC.getNoDWZ());
				eigenschaftenView.getCheckBoxhtmlToClipboard().setSelected(ppC.gethtmlToClipboard());
				if (eigenschaftenView.getCheckBoxohneDWZ().isSelected() == true) {
					eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(true);
					eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(false);
					ppC.setNoFolgeDWZ(true);
				}
				if (eigenschaftenView.getCheckBoxohneELO().isSelected() == true) {
					eigenschaftenView.getCheckBoxohneFolgeELO().setSelected(true);
					eigenschaftenView.getCheckBoxohneFolgeELO().setEnabled(false);
					ppC.setNoFolgeELO(true);
				}
				eigenschaftenView.getCheckBoxPDFLinks().setSelected(ppC.getPDFLinks());
				eigenschaftenView.getWebserverPathTextField().setEnabled(ppC.getPDFLinks());

				if (mainControl.getPropertiesControl().getLanguage().equals("german")) { //$NON-NLS-1$
					eigenschaftenView.getGermanLanguageCheckBox().setSelected(true);
					eigenschaftenView.getEnglishLanguageCheckBox().setSelected(false);
					mainControl.getLanguagePropertiesControl().setLanguageToGerman();
				} else if (mainControl.getPropertiesControl().getLanguage().equals("english")) { //$NON-NLS-1$
					eigenschaftenView.getGermanLanguageCheckBox().setSelected(false);
					eigenschaftenView.getEnglishLanguageCheckBox().setSelected(true);
					mainControl.getLanguagePropertiesControl().setLanguageToEnglish();
				}
				eigenschaftenView.setOpenDefaultPathLabel(ppC.getDefaultPath());

				esControl.setTableColumns();
				addActionListeners();
				esControl.getItemListenerControl().addItemListeners();

			}

		});
	}

	public void addActionListeners() {
		esControl.getEigenschaftenView().getOkButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
				PropertiesControl ppC = mainControl.getPropertiesControl();
				SettingsView settingsView = esControl.getEigenschaftenView();
				ppC.setTableComumnBlack(settingsView.getBlackTextField().getText());
				ppC.setTableComumnWhite(settingsView.getWhiteTextField().getText());
				ppC.setTableComumnMeeting(settingsView.getMeetingTextField().getText());
				ppC.setTableComumnNewDWZ(settingsView.getNewDWZTextField().getText());
				ppC.setTableComumnOldDWZ(settingsView.getOldDWZTextField().getText());
				ppC.setTableComumnNewELO(settingsView.getNewELOTextField().getText());
				ppC.setTableComumnOldELO(settingsView.getOldELOTextField().getText());
				ppC.setTableComumnPlayer(settingsView.getPlayerTextField().getText());
				ppC.setTableComumnPoints(settingsView.getPointsTextField().getText());
				ppC.setTableComumnRanking(settingsView.getRankingTextField().getText());
				ppC.setTableComumnResult(settingsView.getResultTextField().getText());
				ppC.setTableComumnSonnebornBerger(settingsView.getSbbTextField().getText());
				ppC.setTableComumnRound(settingsView.getRoundTextField().getText());
				ppC.setCutForename(settingsView.getForenameLengthBox().getValue());
				ppC.setCutSurname(settingsView.getSurnameLengthBox().getValue());
				ppC.setWebserverPath(settingsView.getWebserverPathTextField().getText());
				ppC.checkCrossTableColumnForDoubles();
				ppC.checkMeetingTableColumnForDoubles();

				ppC.writeProperties();
				esControl.setTableColumns();

			}

		});
		esControl.getEigenschaftenView().getOpenVereineCSVButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// vereine.csv
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter("CSV file", "csv", "CSV");

				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(esControl.getEigenschaftenView());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.
					mainControl.getPropertiesControl().setPathToVereineCVS(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					esControl.getEigenschaftenView()
							.setOpenVereineCSVLabel(mainControl.getPropertiesControl().getPathToVereineCVS());
				}

			}
		});
		esControl.getEigenschaftenView().getOpenPlayersCSVButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// spieler.csv
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter("CSV file", "csv", "CSV", "sqlite");

				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(esControl.getEigenschaftenView());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.
					mainControl.getPropertiesControl().setPathToPlayersCSV(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					esControl.getEigenschaftenView()
							.setOpenPlayersCSVLabel(mainControl.getPropertiesControl().getPathToPlayersCSV());
				}

			}
		});
		esControl.getEigenschaftenView().getOpenPlayersELOButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// spieler.csv
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter("TXT file", "txt", "TXT", "sqlite");

				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(esControl.getEigenschaftenView());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.
					mainControl.getPropertiesControl().setPathToPlayersELO(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					esControl.getEigenschaftenView()
							.setOpenPlayersELOLabel(mainControl.getPropertiesControl().getPathToPlayersELO());
				}

			}
		});
		esControl.getEigenschaftenView().getOpenDefaultPathButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());
				final JFileChooser fc = new JFileChooser(path);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setMultiSelectionEnabled(false);

				int returnVal = fc.showOpenDialog(esControl.getEigenschaftenView());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.
					mainControl.getPropertiesControl().setDefaultPath(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					esControl.getEigenschaftenView()
							.setOpenDefaultPathLabel(mainControl.getPropertiesControl().getDefaultPath());
				}

			}
		});
		esControl.getEigenschaftenView()
				.setOpenVereineCSVLabel(mainControl.getPropertiesControl().getPathToVereineCVS());
		esControl.getEigenschaftenView()
				.setOpenPlayersCSVLabel(mainControl.getPropertiesControl().getPathToPlayersCSV());
		esControl.getEigenschaftenView()
				.setOpenPlayersELOLabel(mainControl.getPropertiesControl().getPathToPlayersELO());
		esControl.getEigenschaftenView().getGermanLanguageCheckBox().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainControl.getLanguagePropertiesControl().setLanguageToGerman();

				mainControl.getPropertiesControl().writeProperties();

			}
		});
		esControl.getEigenschaftenView().getEnglishLanguageCheckBox().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainControl.getLanguagePropertiesControl().setLanguageToEnglish();
				mainControl.getPropertiesControl().writeProperties();

			}
		});
		esControl.getEigenschaftenView().getSpielerListeAuswahlBox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int anzahlProTab = esControl.getEigenschaftenView().getSpielerListeAuswahlBox().getSelectedIndex();
				mainControl.getPropertiesControl().setSpielerProTab(anzahlProTab);
				mainControl.getPropertiesControl().writeProperties();

			}
		});
		esControl.getEigenschaftenView().getTurnierListeAuswahlBox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int anzahlProTab = esControl.getEigenschaftenView().getTurnierListeAuswahlBox().getSelectedIndex();
				mainControl.getPropertiesControl().setTurniereProTab(anzahlProTab);
				mainControl.getPropertiesControl().writeProperties();

			}
		});

		String filename = mainControl.getPropertiesControl().getPathToPlayersELO();
		int positionEXT = filename.lastIndexOf('.');

		if (positionEXT > 0) {
			String newFile = filename.substring(positionEXT);
			if (newFile.equals(".sqlite")) {
				esControl.getEigenschaftenView().getConvertELOToSQLITEButton().setEnabled(false);
			} else {
				esControl.getEigenschaftenView().getConvertELOToSQLITEButton().setEnabled(true);
				esControl.getEigenschaftenView().getConvertELOToSQLITEButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						ELOListToSQLITEControl eloL = new ELOListToSQLITEControl(mainControl);
						eloL.convertELOListToSQLITE();

					}
				});

			}
		}

		filename = mainControl.getPropertiesControl().getPathToPlayersCSV();
		positionEXT = filename.lastIndexOf('.');
		if (positionEXT > 0) {
			String newFile = filename.substring(positionEXT);
			if (newFile.equals(".sqlite")) {
				esControl.getEigenschaftenView().getConvertDWZToSQLITEButton().setEnabled(false);
			} else {
				esControl.getEigenschaftenView().getConvertDWZToSQLITEButton().setEnabled(true);
				esControl.getEigenschaftenView().getConvertDWZToSQLITEButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						DWZListToSQLITEControl dwzL = new DWZListToSQLITEControl(mainControl);
						dwzL.convertDWZListToSQLITE();

					}
				});

			}
		}

	}

}
