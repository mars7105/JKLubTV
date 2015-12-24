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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import de.turnierverwaltung.view.EigenschaftenView;

public class EigenschaftenControl {
	private MainControl mainControl;
	private EigenschaftenView eigenschaftenView;
	private ImageIcon eigenschaftenIcon = new ImageIcon(Toolkit
			.getDefaultToolkit().getImage(
					getClass().getResource("/images/configure-2.png")));
	private int columnWidht;

	/**
	 * @param mainControl
	 */
	public EigenschaftenControl(MainControl mainControl) {
		this.mainControl = mainControl;
		eigenschaftenView = new EigenschaftenView();
		columnWidht = 0;
	}

	/**
	 * 
	 */
	public void makeeigenschaftenPanel() {
		JTabbedPane hauptPanel = this.mainControl.getHauptPanel();
		hauptPanel
				.addTab("Einstellungen", eigenschaftenIcon, eigenschaftenView);
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
		if (eigenschaftenView.getCheckBoxohneDWZ().isSelected() == true) {
			eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(true);
			eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(false);
			mainControl.getPropertiesControl().setNoFolgeDWZ(true);
		}

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
						if (mainControl.getTurnier() != null
								&& columnWidht == 0) {
							columnWidht = mainControl.getSimpleTableView()[0]
									.getTable().getColumn("a.DWZ")
									.getPreferredWidth();
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
											.getTable().getColumn("a.DWZ")
											.setMinWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable().getColumn("n.DWZ")
											.setMinWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable().getColumn("a.DWZ")
											.setPreferredWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable().getColumn("n.DWZ")
											.setPreferredWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable().updateUI();

								}
							} else {

								for (int i = 0; i < mainControl.getTurnier()
										.getAnzahlGruppen(); i++) {

									mainControl.getSimpleTableView()[i]
											.getTable().getColumn("a.DWZ")
											.setPreferredWidth(columnWidht);

									if (eigenschaftenView
											.getCheckBoxohneFolgeDWZ()
											.isSelected() == false) {
										mainControl.getSimpleTableView()[i]
												.getTable().getColumn("n.DWZ")
												.setPreferredWidth(columnWidht);

									}
									mainControl.getSimpleTableView()[i]
											.getTable().updateUI();

								}
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
									.getTable().getColumn("a.DWZ")
									.getPreferredWidth();
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
											.getTable().getColumn("n.DWZ")
											.setMinWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable().getColumn("n.DWZ")
											.setPreferredWidth(0);

									mainControl.getSimpleTableView()[i]
											.getTable().updateUI();

								}
							} else {

								for (int i = 0; i < mainControl.getTurnier()
										.getAnzahlGruppen(); i++) {

									mainControl.getSimpleTableView()[i]
											.getTable().getColumn("n.DWZ")
											.setPreferredWidth(columnWidht);

									mainControl.getSimpleTableView()[i]
											.getTable().updateUI();
								}
							}
						}

						mainControl.getPropertiesControl().writeProperties();
					}
				});

		hauptPanel.updateUI();

	}
}
