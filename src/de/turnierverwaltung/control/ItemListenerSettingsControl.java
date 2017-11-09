package de.turnierverwaltung.control;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import de.turnierverwaltung.view.SettingsView;

public class ItemListenerSettingsControl {
	private MainControl mainControl;
	private SettingsControl esControl;
	private SettingsView eigenschaftenView;

	public ItemListenerSettingsControl(MainControl mainControl, SettingsControl esControl) {
		super();
		this.mainControl = mainControl;
		this.esControl = esControl;
		this.eigenschaftenView = this.esControl.getEigenschaftenView();
	}

	public void addItemListeners() {

		eigenschaftenView.getCheckBoxHeaderFooter().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Boolean onlyTable = eigenschaftenView.getCheckBoxHeaderFooter().isSelected();

				mainControl.getPropertiesControl().setOnlyTables(onlyTable);
				if (mainControl.getTurnier() != null) {
					mainControl.getTurnier().setOnlyTables(onlyTable);
				}

				mainControl.getPropertiesControl().writeProperties();
			}
		});
		eigenschaftenView.getCheckBoxPDFLinks().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Boolean pdflink = eigenschaftenView.getCheckBoxPDFLinks().isSelected();
				eigenschaftenView.getWebserverPathTextField().setEnabled(pdflink);

				mainControl.getPropertiesControl().setPDFLinks(pdflink);

				mainControl.getPropertiesControl().writeProperties();

			}
		});
		eigenschaftenView.getCheckBoxohneDWZ().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(false);

				Boolean noDWZ = eigenschaftenView.getCheckBoxohneDWZ().isSelected();
				mainControl.getPropertiesControl().setNoFolgeDWZ(noDWZ);
				mainControl.getPropertiesControl().setNoDWZ(noDWZ);
				eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(noDWZ);
				eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(!noDWZ);
				if (mainControl.getTurnier() != null) {
					mainControl.getTurnier().setNoDWZCalc(noDWZ);

					if (noDWZ) {

						eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(false);

					} else {

						for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {

							mainControl.getTurnierTabelleControl().berechneFolgeDWZ(i);

						}
						eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(true);
					}
					try {
						mainControl.getTurnierListeLadenControl().reloadTurnier();
					} catch (SQLException e1) {
						mainControl.fileSQLError(e1.getMessage());
					}

				}

				mainControl.getPropertiesControl().writeProperties();
			}
		});

		eigenschaftenView.getCheckBoxohneFolgeDWZ().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				Boolean noFolgeDWZ = eigenschaftenView.getCheckBoxohneFolgeDWZ().isSelected();

				mainControl.getPropertiesControl().setNoFolgeDWZ(noFolgeDWZ);
				if (mainControl.getTurnier() != null) {
					mainControl.getTurnier().setNoFolgeDWZCalc(noFolgeDWZ);
					if (noFolgeDWZ) {

					} else {

						for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {

							mainControl.getTurnierTabelleControl().berechneFolgeDWZ(i);

						}
					}
					try {
						mainControl.getTurnierListeLadenControl().reloadTurnier();
					} catch (SQLException e1) {
						mainControl.fileSQLError(e1.getMessage());
					}
				}

				mainControl.getPropertiesControl().writeProperties();
			}
		});
		eigenschaftenView.getCheckBoxohneELO().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				eigenschaftenView.getCheckBoxohneFolgeELO().setEnabled(false);

				Boolean noELO = eigenschaftenView.getCheckBoxohneELO().isSelected();
				mainControl.getPropertiesControl().setNoFolgeELO(noELO);
				mainControl.getPropertiesControl().setNoELO(noELO);
				eigenschaftenView.getCheckBoxohneFolgeELO().setSelected(noELO);
				eigenschaftenView.getCheckBoxohneFolgeELO().setEnabled(!noELO);
				if (mainControl.getTurnier() != null) {
					mainControl.getTurnier().setNoELOCalc(noELO);

					if (noELO) {

						eigenschaftenView.getCheckBoxohneFolgeELO().setEnabled(false);

					} else {

						for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {

							mainControl.getTurnierTabelleControl().berechneFolgeELO(i);

						}
						eigenschaftenView.getCheckBoxohneFolgeELO().setEnabled(true);
					}
					try {
						mainControl.getTurnierListeLadenControl().reloadTurnier();
					} catch (SQLException e1) {
						mainControl.fileSQLError(e1.getMessage());
					}

				}

				mainControl.getPropertiesControl().writeProperties();
			}
		});

		eigenschaftenView.getCheckBoxohneFolgeELO().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				Boolean noFolgeELO = eigenschaftenView.getCheckBoxohneFolgeELO().isSelected();

				mainControl.getPropertiesControl().setNoFolgeELO(noFolgeELO);
				if (mainControl.getTurnier() != null) {
					mainControl.getTurnier().setNoFolgeELOCalc(noFolgeELO);

					try {
						mainControl.getTurnierListeLadenControl().reloadTurnier();
					} catch (SQLException e1) {
						mainControl.fileSQLError(e1.getMessage());
					}
				}

				mainControl.getPropertiesControl().writeProperties();
			}
		});
		eigenschaftenView.getCheckBoxhtmlToClipboard().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				Boolean copy = eigenschaftenView.getCheckBoxhtmlToClipboard().isSelected();

				mainControl.getPropertiesControl().sethtmlToClipboard(copy);
				mainControl.getPropertiesControl().writeProperties();
			}
		});
	}
}
