package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.net.URISyntaxException;

import javax.swing.JPanel;

public class FirstStartWizardPage1 extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FirstStartWizardPage1() {
		setLayout(new BorderLayout());
		InfoHTMLView info = new InfoHTMLView("english");
		try {
			add(info.getLizenzText(), BorderLayout.CENTER);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
