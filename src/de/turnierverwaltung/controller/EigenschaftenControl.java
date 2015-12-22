package de.turnierverwaltung.controller;

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
