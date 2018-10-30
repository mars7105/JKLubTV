package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SystemColor;
import javax.swing.BoxLayout;
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
	private JButton databaseButton;
	private JButton dwzButton;
	private JButton eloButton;
	private JButton tournamentButton;
	private JButton playerButton;
//	private ImageIcon checkImg = new ImageIcon(
//			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/check.png")));
	private StartpageItemView dwzPanel;
	private StartpageItemView eloPanel;
	private StartpageItemView databasePanel;
	private StartpageItemView playerPanel;
	private StartpageItemView tournamentPanel;
	private ButtonPanelView buttonPane;

	public StartpageView() {
		super();
		JPanel hauptPanel = new JPanel();
		hauptPanel.setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel();

		mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
		mainPanel.setLayout(new GridLayout(0, 1, 20, 20));
		dwzButton = new StartpageButtonView("1. DWZ Liste einrichten");
		dwzPanel = new StartpageItemView();

		dwzPanel.add(dwzButton);
		JPanel labelPanel = new JPanel();
		Color titleColor = new Color((SystemColor.text).getRGB());
		Color titleTextColor = new Color((SystemColor.textText).getRGB());
		labelPanel.setBackground(titleColor);
		labelPanel.setForeground(titleTextColor);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));
		JLabel dwzLabel = new JLabel("1. Schritt: DWZ Liste vom Deutschen Schachbund downloaden und einrichten.");
		JLabel dwzLabel2 = new JLabel("(Optional)");
		labelPanel.add(dwzLabel);
		labelPanel.add(dwzLabel2);
		dwzPanel.add(labelPanel);
		mainPanel.add(dwzPanel);
		eloButton = new StartpageButtonView("2. ELO Liste einrichten");

		eloPanel = new StartpageItemView();

		eloPanel.add(eloButton);
		JLabel eloLabel = new JLabel(
				"2. Schritt: ELO Liste vom World Chess Federation (FIDE) downloaden und einrichten.");
		JLabel eloLabel2 = new JLabel("(Optional)");
		labelPanel = new JPanel();

		labelPanel.setBackground(titleColor);
		labelPanel.setForeground(titleTextColor);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));

		labelPanel.add(eloLabel);
		labelPanel.add(eloLabel2);
		eloPanel.add(labelPanel);
		mainPanel.add(eloPanel);
		databaseButton = new StartpageButtonView("3. Datenbank erstellen");

		databasePanel = new StartpageItemView();

		databasePanel.add(databaseButton);
		JLabel databaseLabel = new JLabel("3. Schritt: Datenbank erstellen.");
		databasePanel.add(databaseLabel);
		mainPanel.add(databasePanel);
		playerButton = new StartpageButtonView("4. Spielerliste erstellen");

		playerPanel = new StartpageItemView();

		playerPanel.add(playerButton);
		JLabel playerLabel = new JLabel("4. Schritt: Spielerliste für die Turniere erstellen.");
		playerPanel.add(playerLabel);
		mainPanel.add(playerPanel);
		tournamentButton = new StartpageButtonView("5. Turnier erstellen");

		tournamentPanel = new StartpageItemView();

		tournamentPanel.add(tournamentButton);
		JLabel tournamentLabel = new JLabel("5. Schritt: Turnier(e) erstellen.");
		tournamentPanel.add(tournamentLabel);
		buttonPane = new ButtonPanelView();
		buttonPane.makeOKButton();
		hauptPanel.add(buttonPane, BorderLayout.SOUTH);
		mainPanel.add(tournamentPanel);
		hauptPanel.add(mainPanel, BorderLayout.NORTH);
		this.setViewportView(hauptPanel);

	}

	public JButton getDatabaseButton() {
		return databaseButton;
	}

	public void setDatabaseButton(JButton databaseButton) {
		this.databaseButton = databaseButton;
	}

	public JButton getDwzButton() {
		return dwzButton;
	}

	public void setDwzButton(JButton dwzButton) {
		this.dwzButton = dwzButton;
	}

	public JButton getEloButton() {
		return eloButton;
	}

	public void setEloButton(JButton eloButton) {
		this.eloButton = eloButton;
	}

	public JButton getTournamentButton() {
		return tournamentButton;
	}

	public void setTournamentButton(JButton tournamentButton) {
		this.tournamentButton = tournamentButton;
	}

	public JButton getPlayerButton() {
		return playerButton;
	}

	public void setPlayerButton(JButton playerButton) {
		this.playerButton = playerButton;
	}

	public StartpageItemView getDwzPanel() {
		return dwzPanel;
	}

	public void setDwzPanel(StartpageItemView dwzPanel) {
		this.dwzPanel = dwzPanel;
	}

	public StartpageItemView getEloPanel() {
		return eloPanel;
	}

	public void setEloPanel(StartpageItemView eloPanel) {
		this.eloPanel = eloPanel;
	}

	public StartpageItemView getDatabasePanel() {
		return databasePanel;
	}

	public void setDatabasePanel(StartpageItemView databasePanel) {
		this.databasePanel = databasePanel;
	}

	public StartpageItemView getPlayerPanel() {
		return playerPanel;
	}

	public void setPlayerPanel(StartpageItemView playerPanel) {
		this.playerPanel = playerPanel;
	}

	public StartpageItemView getTournamentPanel() {
		return tournamentPanel;
	}

	public void setTournamentPanel(StartpageItemView tournamentPanel) {
		this.tournamentPanel = tournamentPanel;
	}

	public ButtonPanelView getButtonPane() {
		return buttonPane;
	}

	public void setButtonPane(ButtonPanelView buttonPane) {
		this.buttonPane = buttonPane;
	}

}
