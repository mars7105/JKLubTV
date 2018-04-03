package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class StartpageView extends JScrollPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton databaseButton;
	private JButton dwzButton;
	private JButton eloButton;
	private JButton tournamentButton;
	private JButton playerButton;

	public StartpageView() {
		super();
		JPanel hauptPanel = new JPanel();
		hauptPanel.setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel();

		mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
		mainPanel.setLayout(new GridLayout(0, 1, 20, 20));
		dwzButton = new StartpageButtonView("1. DWZ Liste einrichten");
		StartpageItemView panel = new StartpageItemView();

		panel.add(dwzButton);
		JLabel dwzLabel = new JLabel("1. Schritt: DWZ Liste vom Deutschen Schachbund downloaden und einrichten.");
		panel.add(dwzLabel);
		mainPanel.add(panel);
		eloButton = new StartpageButtonView("2. ELO Liste einrichten");

		panel = new StartpageItemView();

		panel.add(eloButton);
		JLabel eloLabel = new JLabel(
				"2. Schritt: ELO Liste vom World Chess Federation (FIDE) downloaden und einrichten.");
		panel.add(eloLabel);
		mainPanel.add(panel);
		databaseButton = new StartpageButtonView("3. Datenbank erstellen");

		panel = new StartpageItemView();

		panel.add(databaseButton);
		JLabel databaseLabel = new JLabel("3. Schritt: Datenbank erstellen.");
		panel.add(databaseLabel);
		mainPanel.add(panel);
		playerButton = new StartpageButtonView("4. Spielerliste erstellen");

		panel = new StartpageItemView();

		panel.add(playerButton);
		JLabel playerLabel = new JLabel("4. Schritt: Spielerliste für die Turniere erstellen.");
		panel.add(playerLabel);
		mainPanel.add(panel);
		tournamentButton = new StartpageButtonView("5. Turnier erstellen");

		panel = new StartpageItemView();

		panel.add(tournamentButton);
		JLabel tournamentLabel = new JLabel("5. Schritt: Turnier(e) erstellen.");
		panel.add(tournamentLabel);
		mainPanel.add(panel);
		hauptPanel.add(mainPanel, BorderLayout.NORTH);
		this.setViewportView(hauptPanel);

	}

}
