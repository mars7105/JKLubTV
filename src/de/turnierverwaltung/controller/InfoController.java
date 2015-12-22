package de.turnierverwaltung.controller;

import java.awt.Toolkit;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.view.InfoView;
import de.turnierverwaltung.view.InfoHomeScreenView;
import de.turnierverwaltung.view.InfoLizenzenView;

public class InfoController {
	private MainControl mainControl;
	private InfoView infoView;
	// private JButton lizenzButton;
	private JTabbedPane lizenzenPane;
	private InfoLizenzenView infoTexteView;
	private InfoHomeScreenView infoHelpView;
	private ImageIcon infoIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-notice.png")));
	private ImageIcon lizenzenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-paragraph.png")));
	/**
	 * @param mainControl
	 */
	public InfoController(MainControl mainControl) {
		this.mainControl = mainControl;
		infoView = new InfoView();
		infoHelpView = new InfoHomeScreenView();
		lizenzenPane = new JTabbedPane();
		infoTexteView = new InfoLizenzenView();
		try {
			lizenzenPane.addTab("Information",infoIcon, infoHelpView.getLizenzText());

			lizenzenPane.addTab("Lizenzen",lizenzenIcon, infoTexteView.getLizenzText());
			infoView.setLizenzenPane(lizenzenPane);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		makeInfoPanel();

	}

	/**
	 * 
	 */
	public void makeInfoPanel() {
		JTabbedPane hauptPanel = this.mainControl.getHauptPanel();
		// hauptPanel.removeAll();
		// this.mainControl.getNaviController().makeNaviPanel();

		hauptPanel.addTab("Info", infoIcon, infoView);
		hauptPanel.updateUI();
		EigenschaftenControl eigenschaftenControl = new EigenschaftenControl(mainControl);
		mainControl.setEigenschaftenControl(eigenschaftenControl);
		eigenschaftenControl.makeeigenschaftenPanel();
	}

}
