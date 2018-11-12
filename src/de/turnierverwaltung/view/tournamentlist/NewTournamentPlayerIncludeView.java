package de.turnierverwaltung.view.tournamentlist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import de.turnierverwaltung.view.ButtonPanelView;
import de.turnierverwaltung.view.Messages;

public class NewTournamentPlayerIncludeView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JButton okButton;
	private JButton cancelButton;
	private JScrollPane scrollPane;
	private ArrayList<PlayerLineView> playerLineViews;

	private int spielerAnzahl;

//	private int[] spielerID;
	public NewTournamentPlayerIncludeView(int spielerAnzahl) {
		super();
		this.spielerAnzahl = spielerAnzahl;
		playerLineViews = new ArrayList<PlayerLineView>();
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		setLayout(new BorderLayout());

		ButtonPanelView buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		okButton = buttonPane.getOkButton();

		cancelButton = buttonPane.getCancelButton();

		JPanel centerPane = new JPanel();

		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		for (int i = 0; i < this.spielerAnzahl; i++) {
			PlayerLineView line = new PlayerLineView(i);
			playerLineViews.add(line);
			centerPane.add(line);

		}
		contentPanel.add(centerPane);
		contentPanel.add(buttonPane);
		JPanel all = new JPanel();
		all.setLayout(new BorderLayout());
		all.add(contentPanel, BorderLayout.NORTH);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(all);

		add(scrollPane, BorderLayout.CENTER);
		setVisible(true);
	}

	public class PlayerLineView extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextField forenameTextfield;
		private JTextField surnameTextfield;
		private int spielerID;
		private JTextField dwzTextfield;
		private JTextField kuerzelTextfield;
		private JButton playerAddButton;
		private int lineIndex;

		public PlayerLineView(int i) {
			super();
			lineIndex = i;
			spielerID = -1;
			JPanel line = new JPanel();
			line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
			JLabel label1 = new JLabel(" " + Messages.getString("SpielerEingabeView.14"));
			forenameTextfield = new JTextField(10);
			forenameTextfield.setEnabled(false);
			forenameTextfield.setDisabledTextColor(Color.BLACK);
			JLabel label2 = new JLabel(" " + Messages.getString("SpielerEingabeView.15"));

			surnameTextfield = new JTextField(10);
			surnameTextfield.setEnabled(false);
			surnameTextfield.setDisabledTextColor(Color.BLACK);
			playerAddButton = new JButton(Messages.getString("SpielerEingabeView.7"));

			line.add(playerAddButton);
			line.add(label1);
			line.add(forenameTextfield);
			line.add(label2);
			line.add(surnameTextfield);

			JLabel label3 = new JLabel(Messages.getString("SpielerEingabeView.8")); //$NON-NLS-1$
			kuerzelTextfield = new JTextField(10);
			kuerzelTextfield.setEnabled(false);
			kuerzelTextfield.setDisabledTextColor(Color.BLACK);
			line.add(label3);
			line.add(kuerzelTextfield);

			JLabel label4 = new JLabel(Messages.getString("SpielerEingabeView.9")); //$NON-NLS-1$
			dwzTextfield = new JTextField(10);
			dwzTextfield.setEnabled(false);

			dwzTextfield.setDisabledTextColor(Color.BLACK);
			line.add(label4);
			line.add(dwzTextfield);

			this.add(line);

		}

		public int getLineIndex() {
			return lineIndex;
		}

		public void setLineIndex(int lineIndex) {
			this.lineIndex = lineIndex;
		}

		public int getSpielerID() {
			return spielerID;
		}

		public void setSpielerID(int spielerID) {
			this.spielerID = spielerID;
		}

		public JTextField getForenameTextfield() {
			return forenameTextfield;
		}

		public void setForenameTextfield(JTextField forenameTextfield) {
			this.forenameTextfield = forenameTextfield;
		}

		public JTextField getSurnameTextfield() {
			return surnameTextfield;
		}

		public void setSurnameTextfield(JTextField surnameTextfield) {
			this.surnameTextfield = surnameTextfield;
		}

		public JTextField getDwzTextfield() {
			return dwzTextfield;
		}

		public void setDwzTextfield(JTextField dwzTextfield) {
			this.dwzTextfield = dwzTextfield;
		}

		public JTextField getKuerzelTextfield() {
			return kuerzelTextfield;
		}

		public void setKuerzelTextfield(JTextField kuerzelTextfield) {
			this.kuerzelTextfield = kuerzelTextfield;
		}

		public JButton getPlayerAddButton() {
			return playerAddButton;
		}

		public void setPlayerAddButton(JButton playerAddButton) {
			this.playerAddButton = playerAddButton;
		}

	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public ArrayList<PlayerLineView> getPlayerLineViews() {
		return playerLineViews;
	}

	public void setPlayerLineViews(ArrayList<PlayerLineView> playerLineViews) {
		this.playerLineViews = playerLineViews;
	}

	public int getSpielerAnzahl() {
		return spielerAnzahl;
	}

	public void setSpielerAnzahl(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
	}

}
