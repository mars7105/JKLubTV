package de.turnierverwaltung.control;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JTable;

import de.turnierverwaltung.view.EigenschaftenView;

public class EigenschaftenItemListenerControl {
	private MainControl mainControl;
	private EigenschaftenControl esControl;
	private EigenschaftenView eigenschaftenView;
	private int columnWidht;
	private int maxWidth;

	public EigenschaftenItemListenerControl(MainControl mainControl,
			EigenschaftenControl esControl) {
		super();
		this.mainControl = mainControl;
		this.esControl = esControl;
		this.eigenschaftenView = this.esControl.getEigenschaftenView();
		this.columnWidht = this.esControl.getColumnWidht();
		this.maxWidth = this.esControl.getMaxWidth();
	}

	public void addItemListeners() {

		eigenschaftenView.getCheckBoxHeaderFooter().addItemListener(
				new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						Boolean onlyTable = eigenschaftenView
								.getCheckBoxHeaderFooter().isSelected();

						mainControl.getPropertiesControl().setOnlyTables(
								onlyTable);
						if (mainControl.getTurnier() != null) {
							mainControl.getTurnier().setOnlyTables(onlyTable);
						}

						mainControl.getPropertiesControl().writeProperties();
					}
				});

		eigenschaftenView.getCheckBoxohneDWZ().setSelected(
				mainControl.getPropertiesControl().getNoDWZ());
		eigenschaftenView.getCheckBoxohneDWZ().addItemListener(
				new ItemListener() {

					public void itemStateChanged(ItemEvent e) {
						eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(
								false);
						if (mainControl.getTurnier() != null
								&& columnWidht == 0) {
							columnWidht = mainControl.getSimpleTableView()[0]
									.getTable()
									.getColumn(
											Messages.getString("EigenschaftenControl.12")) //$NON-NLS-1$
									.getPreferredWidth();
							maxWidth = mainControl.getSimpleTableView()[0]
									.getTable()
									.getColumn(
											Messages.getString("EigenschaftenControl.13")) //$NON-NLS-1$
									.getMaxWidth();
						}
						Boolean noDWZ = eigenschaftenView.getCheckBoxohneDWZ()
								.isSelected();
						mainControl.getPropertiesControl().setNoFolgeDWZ(noDWZ);
						mainControl.getPropertiesControl().setNoDWZ(noDWZ);
						eigenschaftenView.getCheckBoxohneFolgeDWZ()
								.setSelected(noDWZ);
						eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(
								!noDWZ);
						if (mainControl.getTurnier() != null) {
							mainControl.getTurnier().setNoFolgeDWZCalc(noDWZ);
							mainControl.getTurnier().setNoDWZCalc(noDWZ);

							if (noDWZ) {

								for (int i = 0; i < mainControl.getTurnier()
										.getAnzahlGruppen(); i++) {
									mainControl.getSimpleTableView()[i]
											.getTable().setAutoResizeMode(
													JTable.AUTO_RESIZE_OFF);
									mainControl.getSimpleTableView()[i]
											.getTable()
											.getColumn(
													Messages.getString("EigenschaftenControl.14")) //$NON-NLS-1$
											.setMinWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable()
											.getColumn(
													Messages.getString("EigenschaftenControl.15")) //$NON-NLS-1$
											.setMinWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable()
											.getColumn(
													Messages.getString("EigenschaftenControl.16")) //$NON-NLS-1$
											.setMaxWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable()
											.getColumn(
													Messages.getString("EigenschaftenControl.17")) //$NON-NLS-1$
											.setMaxWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable().updateUI();
									eigenschaftenView.getCheckBoxohneFolgeDWZ()
											.setEnabled(false);
								}
							} else {

								for (int i = 0; i < mainControl.getTurnier()
										.getAnzahlGruppen(); i++) {
									mainControl.getSimpleTableView()[i]
											.getTable()
											.setAutoResizeMode(
													JTable.AUTO_RESIZE_ALL_COLUMNS);
									mainControl.getSimpleTableView()[i]
											.getTable()
											.getColumn(
													Messages.getString("EigenschaftenControl.18")) //$NON-NLS-1$
											.setMaxWidth(maxWidth);
									mainControl.getSimpleTableView()[i]
											.getTable()
											.getColumn(
													Messages.getString("EigenschaftenControl.19")) //$NON-NLS-1$
											.setPreferredWidth(columnWidht);

									if (eigenschaftenView
											.getCheckBoxohneFolgeDWZ()
											.isSelected() == false) {
										mainControl.getSimpleTableView()[i]
												.getTable()
												.getColumn(
														Messages.getString("EigenschaftenControl.20")) //$NON-NLS-1$
												.setMaxWidth(maxWidth);
										mainControl.getSimpleTableView()[i]
												.getTable()
												.getColumn(
														Messages.getString("EigenschaftenControl.21")) //$NON-NLS-1$
												.setPreferredWidth(columnWidht);

									}
									mainControl.getSimpleTableView()[i]
											.getTable().updateUI();
									mainControl.getTurnierTabelleControl()
											.berechneFolgeDWZ(i);

								}
								eigenschaftenView.getCheckBoxohneFolgeDWZ()
										.setEnabled(true);
							}
						}

						mainControl.getPropertiesControl().writeProperties();
					}
				});

		eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(
				mainControl.getPropertiesControl().getNoFolgeDWZ());
		eigenschaftenView.getCheckBoxohneFolgeDWZ().addItemListener(
				new ItemListener() {

					public void itemStateChanged(ItemEvent e) {
						if (mainControl.getTurnier() != null
								&& columnWidht == 0) {
							columnWidht = mainControl.getSimpleTableView()[0]
									.getTable()
									.getColumn(
											Messages.getString("EigenschaftenControl.22")) //$NON-NLS-1$
									.getPreferredWidth();
							maxWidth = mainControl.getSimpleTableView()[0]
									.getTable()
									.getColumn(
											Messages.getString("EigenschaftenControl.23")) //$NON-NLS-1$
									.getMaxWidth();
						}
						Boolean noFolgeDWZ = eigenschaftenView
								.getCheckBoxohneFolgeDWZ().isSelected();

						mainControl.getPropertiesControl().setNoFolgeDWZ(
								noFolgeDWZ);
						if (mainControl.getTurnier() != null) {
							mainControl.getTurnier().setNoDWZCalc(noFolgeDWZ);
							if (noFolgeDWZ) {

								mainControl.getPropertiesControl()
										.setNoFolgeDWZ(true);

								for (int i = 0; i < mainControl.getTurnier()
										.getAnzahlGruppen(); i++) {
									mainControl.getSimpleTableView()[i]
											.getTable().setAutoResizeMode(
													JTable.AUTO_RESIZE_OFF);

									mainControl.getSimpleTableView()[i]
											.getTable()
											.getColumn(
													Messages.getString("EigenschaftenControl.24")) //$NON-NLS-1$
											.setMinWidth(0);
									mainControl.getSimpleTableView()[i]
											.getTable()
											.getColumn(
													Messages.getString("EigenschaftenControl.25")) //$NON-NLS-1$
											.setMaxWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable().updateUI();

								}
							} else {

								for (int i = 0; i < mainControl.getTurnier()
										.getAnzahlGruppen(); i++) {
									mainControl.getSimpleTableView()[i]
											.getTable()
											.setAutoResizeMode(
													JTable.AUTO_RESIZE_ALL_COLUMNS);
									mainControl.getSimpleTableView()[i]
											.getTable()
											.getColumn(
													Messages.getString("EigenschaftenControl.26")) //$NON-NLS-1$
											.setMaxWidth(maxWidth);
									mainControl.getSimpleTableView()[i]
											.getTable()
											.getColumn(
													Messages.getString("EigenschaftenControl.27")) //$NON-NLS-1$
											.setPreferredWidth(columnWidht);

									mainControl.getSimpleTableView()[i]
											.getTable().updateUI();
									mainControl.getTurnierTabelleControl()
											.berechneFolgeDWZ(i);

								}
							}
						}

						mainControl.getPropertiesControl().writeProperties();
					}
				});
	}
}
