package de.turnierverwaltung.controller;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.view.EigenschaftenView;

public class EigenschaftenControl implements ActionListener {
	private MainControl mainControl;
	private EigenschaftenView eigenschaftenView;
	private ImageIcon eigenschaftenIcon = new ImageIcon(Toolkit
			.getDefaultToolkit().getImage(
					getClass().getResource("/images/configure-2.png"))); //$NON-NLS-1$
	private int maxWidth = 0;;
	private int columnWidht = 0;

	/**
	 * @param mainControl
	 */
	public EigenschaftenControl(MainControl mainControl) {
		this.mainControl = mainControl;
		eigenschaftenView = new EigenschaftenView();
		eigenschaftenView.getOpenVereineCSVButton().addActionListener(this);
		eigenschaftenView.setOpenVereineCSVLabel(mainControl
				.getPropertiesControl().getPathToCVS());
	}

	/**
	 * 
	 */
	public void makeeigenschaftenPanel() {
		JTabbedPane hauptPanel = this.mainControl.getHauptPanel();
		hauptPanel
				.addTab(Messages.getString("EigenschaftenControl.1"), eigenschaftenIcon, eigenschaftenView); //$NON-NLS-1$
		if (mainControl.getPropertiesControl() == null) {
			mainControl.setPropertiesControl(new PropertiesControl());
			mainControl.getPropertiesControl().readProperties();
		}
		eigenschaftenView.getCheckBoxHeaderFooter().setSelected(
				mainControl.getPropertiesControl().getOnlyTables());
		eigenschaftenView.getCheckBoxohneDWZ().setSelected(
				mainControl.getPropertiesControl().getNoDWZ());
		eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(
				mainControl.getPropertiesControl().getNoFolgeDWZ());
		eigenschaftenView.getSpielerListeAuswahlBox().setSelectedIndex(
				mainControl.getPropertiesControl().getSpielerProTab());
		eigenschaftenView.getTurnierListeAuswahlBox().setSelectedIndex(
				mainControl.getPropertiesControl().getTurniereProTab());
		if (eigenschaftenView.getCheckBoxohneDWZ().isSelected() == true) {
			eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(true);
			eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(false);
			mainControl.getPropertiesControl().setNoFolgeDWZ(true);
		}
		if (mainControl.getPropertiesControl().getLanguage().equals("german")) { //$NON-NLS-1$
			eigenschaftenView.getGermanLanguageCheckBox().setSelected(true);
			eigenschaftenView.getEnglishLanguageCheckBox().setSelected(false);
			mainControl.getPropertiesControl().setLanguageToGerman();
		} else if (mainControl.getPropertiesControl().getLanguage()
				.equals("english")) { //$NON-NLS-1$
			eigenschaftenView.getGermanLanguageCheckBox().setSelected(false);
			eigenschaftenView.getEnglishLanguageCheckBox().setSelected(true);
			mainControl.getPropertiesControl().setLanguageToEnglish();
		}
		eigenschaftenView.getGermanLanguageCheckBox().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mainControl.getPropertiesControl()
								.setLanguageToGerman();

						mainControl.getPropertiesControl().writeProperties();

					}
				});
		eigenschaftenView.getEnglishLanguageCheckBox().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mainControl.getPropertiesControl()
								.setLanguageToEnglish();
						mainControl.getPropertiesControl().writeProperties();

					}
				});
		eigenschaftenView.getSpielerListeAuswahlBox().addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int anzahlProTab = eigenschaftenView
								.getSpielerListeAuswahlBox().getSelectedIndex();
						mainControl.getPropertiesControl().setSpielerProTab(
								anzahlProTab);
						mainControl.getPropertiesControl().writeProperties();

					}
				});
		eigenschaftenView.getTurnierListeAuswahlBox().addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						int anzahlProTab = eigenschaftenView
								.getTurnierListeAuswahlBox().getSelectedIndex();
						mainControl.getPropertiesControl().setTurniereProTab(
								anzahlProTab);
						mainControl.getPropertiesControl().writeProperties();

					}
				});
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
									// mainControl.getSimpleTableView()[i]
									// .getTable().getColumn("n.DWZ")
									// .setPreferredWidth(0);

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

		hauptPanel.updateUI();

	}

	public int getColumnWidht() {
		return columnWidht;
	}

	public void setColumnWidht(int columnWidht) {
		this.columnWidht = columnWidht;
	}

	public void setColumnWidhtToZero() {
		this.columnWidht = 0;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == eigenschaftenView.getOpenVereineCSVButton()) {
			final JFileChooser fc = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("CSV file", "csv",
					"CSV");

			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(eigenschaftenView);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// This is where a real application would open the file.
				mainControl.getPropertiesControl().setPathToCVS(
						file.getAbsolutePath());
				mainControl.getPropertiesControl().writeProperties();
				eigenschaftenView.setOpenVereineCSVLabel(mainControl
						.getPropertiesControl().getPathToCVS());
			}
		}

	}
}
