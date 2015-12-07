package de.turnierverwaltung.controller;

import java.awt.BorderLayout;

import java.net.URISyntaxException;

import javax.swing.JPanel;
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
			lizenzenPane.addTab("Information", infoHelpView.getLizenzText());

			lizenzenPane.addTab("Lizenzen", infoTexteView.getLizenzText());
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
		JPanel hauptPanel = this.mainControl.getHauptPanel();
		hauptPanel.removeAll();
		this.mainControl.getNaviController().makeNaviPanel();

		hauptPanel.add(infoView, BorderLayout.CENTER);
		hauptPanel.updateUI();

	}

}
