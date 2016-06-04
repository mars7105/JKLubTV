package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EigenschaftenActionListenerControl implements ActionListener {
	private MainControl mainControl;
	private EigenschaftenControl esControl;

	public EigenschaftenActionListenerControl(MainControl mainControl,
			EigenschaftenControl esControl) {
		super();
		this.mainControl = mainControl;
		this.esControl = esControl;
		esControl.getEigenschaftenView();
		esControl.getColumnWidht();
		esControl.getMaxWidth();
	}

	public void addActionListeners() {
		esControl.getEigenschaftenView().getGermanLanguageCheckBox()
				.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mainControl.getLanguagePropertiesControl()
								.setLanguageToGerman();

						mainControl.getPropertiesControl().writeProperties();

					}
				});
		esControl.getEigenschaftenView().getEnglishLanguageCheckBox()
				.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mainControl.getLanguagePropertiesControl()
								.setLanguageToEnglish();
						mainControl.getPropertiesControl().writeProperties();

					}
				});
		esControl.getEigenschaftenView().getSpielerListeAuswahlBox()
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int anzahlProTab = esControl.getEigenschaftenView()
								.getSpielerListeAuswahlBox().getSelectedIndex();
						mainControl.getPropertiesControl().setSpielerProTab(
								anzahlProTab);
						mainControl.getPropertiesControl().writeProperties();

					}
				});
		esControl.getEigenschaftenView().getTurnierListeAuswahlBox()
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						int anzahlProTab = esControl.getEigenschaftenView()
								.getTurnierListeAuswahlBox().getSelectedIndex();
						mainControl.getPropertiesControl().setTurniereProTab(
								anzahlProTab);
						mainControl.getPropertiesControl().writeProperties();

					}
				});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == esControl.getEigenschaftenView()
				.getOpenVereineCSVButton()) {
			final JFileChooser fc = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("CSV file", "csv",
					"CSV");

			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(esControl.getEigenschaftenView());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// This is where a real application would open the file.
				mainControl.getPropertiesControl().setPathToCVS(
						file.getAbsolutePath());
				mainControl.getPropertiesControl().writeProperties();
				esControl.getEigenschaftenView().setOpenVereineCSVLabel(
						mainControl.getPropertiesControl().getPathToCVS());
			}
		}

	}

}
