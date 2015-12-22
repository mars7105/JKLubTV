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

	/**
	 * @param mainControl
	 */
	public EigenschaftenControl(MainControl mainControl) {
		this.mainControl = mainControl;
		eigenschaftenView = new EigenschaftenView();

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
		String wert = mainControl.getPropertiesControl().getProperties(
				"onlyTables");
		if (wert.equals("true")) {
			eigenschaftenView.getCheckBoxHeaderFooter().setSelected(true);
		}
		eigenschaftenView.getCheckBoxHeaderFooter().addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent e) {
		    	  if (eigenschaftenView.getCheckBoxHeaderFooter().isSelected() == true) {
		    		  mainControl.getPropertiesControl().setProperties("onlyTables", "true");
		    	  } else {
		    		  mainControl.getPropertiesControl().setProperties("onlyTables", "false");
		    	  }
		    	  mainControl.getPropertiesControl().writeProperties();
		        }
		      });
		hauptPanel.updateUI();

	}

}
