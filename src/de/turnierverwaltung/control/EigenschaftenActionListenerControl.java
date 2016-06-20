package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.view.EigenschaftenView;

public class EigenschaftenActionListenerControl {
	private MainControl mainControl;
	private EigenschaftenControl esControl;
	private JDialog dialog;

	public EigenschaftenActionListenerControl(MainControl mainControl, EigenschaftenControl esControl) {
		super();
		this.mainControl = mainControl;
		this.esControl = esControl;
		addPropertiesActionListener();
	}

	private void addPropertiesActionListener() {
		mainControl.getNaviView().getPropertiesButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				PropertiesControl ppC = mainControl.getPropertiesControl();
				mainControl.getEigenschaftenControl().setEigenschaftenView(new EigenschaftenView());
				EigenschaftenView eigenschaftenView = mainControl.getEigenschaftenControl().getEigenschaftenView();
				mainControl.getEigenschaftenControl().setItemListenerControl(
						new EigenschaftenItemListenerControl(mainControl, mainControl.getEigenschaftenControl()));
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
					ppC.readProperties();
				}
				eigenschaftenView.getCheckBoxHeaderFooter().setSelected(ppC.getOnlyTables());
				eigenschaftenView.getCheckBoxohneDWZ().setSelected(ppC.getNoDWZ());
				eigenschaftenView.getCheckBoxohneFolgeDWZ()
						.setSelected(mainControl.getPropertiesControl().getNoFolgeDWZ());
				eigenschaftenView.getSpielerListeAuswahlBox().setSelectedIndex(ppC.getSpielerProTab());
				eigenschaftenView.getTurnierListeAuswahlBox().setSelectedIndex(ppC.getTurniereProTab());
				if (eigenschaftenView.getCheckBoxohneDWZ().isSelected() == true) {
					eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(true);
					eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(false);
					ppC.setNoFolgeDWZ(true);
				}
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
				ppC.setTableComumnBlack(esControl.getEigenschaftenView().getBlackTextField().getText());
				ppC.setTableComumnWhite(esControl.getEigenschaftenView().getWhiteTextField().getText());
				ppC.setTableComumnMeeting(esControl.getEigenschaftenView().getMeetingTextField().getText());
				ppC.setTableComumnNewDWZ(esControl.getEigenschaftenView().getNewDWZTextField().getText());
				ppC.setTableComumnOldDWZ(esControl.getEigenschaftenView().getOldDWZTextField().getText());
				ppC.setTableComumnPlayer(esControl.getEigenschaftenView().getPlayerTextField().getText());
				ppC.setTableComumnPoints(esControl.getEigenschaftenView().getPointsTextField().getText());
				ppC.setTableComumnRanking(esControl.getEigenschaftenView().getRankingTextField().getText());
				ppC.setTableComumnResult(esControl.getEigenschaftenView().getResultTextField().getText());
				ppC.setTableComumnSonnebornBerger(esControl.getEigenschaftenView().getSbbTextField().getText());
				ppC.setTableComumnRound(esControl.getEigenschaftenView().getRoundTextField().getText());
				ppC.checkCrossTableColumnForDoubles();
				ppC.checkMeetingTableColumnForDoubles();

				ppC.writeProperties();
				esControl.setTableColumns();

			}

		});
		esControl.getEigenschaftenView().getOpenVereineCSVButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter("CSV file", "csv", "CSV");

				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(esControl.getEigenschaftenView());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.
					mainControl.getPropertiesControl().setPathToCVS(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					esControl.getEigenschaftenView()
							.setOpenVereineCSVLabel(mainControl.getPropertiesControl().getPathToCVS());
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
		esControl.getEigenschaftenView().setOpenVereineCSVLabel(mainControl.getPropertiesControl().getPathToCVS());
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
		

	}
}
