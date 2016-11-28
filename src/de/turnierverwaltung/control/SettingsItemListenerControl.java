package de.turnierverwaltung.control;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import de.turnierverwaltung.view.SettingsView;

public class SettingsItemListenerControl {
	private MainControl mainControl;
	private SettingsControl esControl;
	private SettingsView eigenschaftenView;

	public SettingsItemListenerControl(MainControl mainControl, SettingsControl esControl) {
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

		eigenschaftenView.getCheckBoxohneDWZ().setSelected(mainControl.getPropertiesControl().getNoDWZ());
		eigenschaftenView.getCheckBoxohneDWZ().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(false);

				Boolean noDWZ = eigenschaftenView.getCheckBoxohneDWZ().isSelected();
				mainControl.getPropertiesControl().setNoFolgeDWZ(noDWZ);
				mainControl.getPropertiesControl().setNoDWZ(noDWZ);
				eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(noDWZ);
				eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(!noDWZ);
				if (mainControl.getTurnier() != null) {
					mainControl.getTurnier().setNoFolgeDWZCalc(noDWZ);
					mainControl.getTurnier().setNoDWZCalc(noDWZ);

					if (noDWZ) {

						for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {

							eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(false);
						}
					} else {

						for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {

							mainControl.getTurnierTabelleControl().berechneFolgeDWZ(i);

						}
						eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(true);
					}
					try {
						mainControl.getTurnierListeLadenControl().reloadTurnier();
					} catch (SQLException e1) {
						mainControl.fileSQLError();
					}

				}

				mainControl.getPropertiesControl().writeProperties();
			}
		});

		eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(mainControl.getPropertiesControl().getNoFolgeDWZ());
		eigenschaftenView.getCheckBoxohneFolgeDWZ().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				Boolean noFolgeDWZ = eigenschaftenView.getCheckBoxohneFolgeDWZ().isSelected();

				mainControl.getPropertiesControl().setNoFolgeDWZ(noFolgeDWZ);
				if (mainControl.getTurnier() != null) {
					mainControl.getTurnier().setNoDWZCalc(noFolgeDWZ);
					if (noFolgeDWZ) {

						mainControl.getPropertiesControl().setNoFolgeDWZ(true);

					} else {

						for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {

							mainControl.getTurnierTabelleControl().berechneFolgeDWZ(i);

						}
					}
					try {
						mainControl.getTurnierListeLadenControl().reloadTurnier();
					} catch (SQLException e1) {
						mainControl.fileSQLError();
					}
				}

				mainControl.getPropertiesControl().writeProperties();
			}
		});
		eigenschaftenView.getSortMeetingTableCheckBox()
				.setSelected(mainControl.getPropertiesControl().getSortMeetingTable());
		eigenschaftenView.getSortMeetingTableCheckBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				Boolean sortMeetingTable = eigenschaftenView.getSortMeetingTableCheckBox().isSelected();

				mainControl.getPropertiesControl().setSortMeetingTableCheckBox(sortMeetingTable);
				if (mainControl.getTurnier() != null) {
					mainControl.getTurnier().setSortMeetingTable(sortMeetingTable);
					if (sortMeetingTable) {

						mainControl.getPropertiesControl().setSortMeetingTableCheckBox(true);

					}
					try {
						mainControl.getTurnierListeLadenControl().reloadTurnier();
					} catch (SQLException e1) {
						mainControl.fileSQLError();
					}
				}

				mainControl.getPropertiesControl().writeProperties();
			}
		});
	}
}
