package de.turnierverwaltung.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.turnierverwaltung.view.InfoView;
import de.turnierverwaltung.view.InfoTexteView;

public class InfoController implements ActionListener {
	private MainControl mainControl;
	private InfoView infoView;
	private JButton lizenzButton;
	private InfoTexteView infoTexteView;

	public InfoController(MainControl mainControl) {
		this.mainControl = mainControl;
		infoView = new InfoView();
		lizenzButton = infoView.getLizenzenButton();
		lizenzButton.addActionListener(this);
		infoTexteView = new InfoTexteView();
		makeInfoPanel();

	}

	public void makeInfoPanel() {
		JPanel hauptPanel = this.mainControl.getHauptPanel();
		hauptPanel.removeAll();
		this.mainControl.getNaviController().makeNaviPanel();
		hauptPanel.add(infoView, BorderLayout.CENTER);
		hauptPanel.updateUI();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == lizenzButton) {
			String ausgabe = "AHHH!";
			infoView.getTextArea().setText(infoTexteView.getLizenzText());
		}

	}
}
