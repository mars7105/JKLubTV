package de.turnierverwaltung.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.turnierverwaltung.view.NaviView;

public class NaviController implements ActionListener {

	private MainControl mainControl;
	private JButton spielerListeButton;
	private JButton turnierListeButton;

	public NaviController(MainControl mainControl) {
		this.mainControl = mainControl;
		NaviView naviView = new NaviView();
		JPanel hauptPanel = this.mainControl.getHauptPanel();
		turnierListeButton = naviView.getTurnierListeButton();
		turnierListeButton.addActionListener(this);
		spielerListeButton = naviView.getSpielerListeButton();
		spielerListeButton.addActionListener(this);
		hauptPanel.add(naviView, BorderLayout.WEST);
		hauptPanel.updateUI();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == turnierListeButton) {
			if (mainControl.getTurnierListeLadenControl() != null) {
				mainControl.getTurnierListeLadenControl().loadTurnier();
			} else {
				mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(mainControl));
				mainControl.getTurnierListeLadenControl().loadTurnier();
			}

		}
		if (arg0.getSource() == spielerListeButton) {
			if (mainControl.getSpielerEditierenControl() != null) {
				mainControl.getSpielerEditierenControl().makePanel();
			} else {
				mainControl.setSpielerEditierenControl(new SpielerLadenControl(mainControl));
				mainControl.getSpielerEditierenControl().makePanel();
			}
		}
	}

}
