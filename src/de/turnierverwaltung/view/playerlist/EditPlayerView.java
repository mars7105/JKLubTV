package de.turnierverwaltung.view.playerlist;
//JKlubTV - Ein Programm zum verwalten von Schach Turnieren

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.view.ButtonPanelView;
import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.OwnLabel;

public class EditPlayerView extends JDialog {
	/**
	 *
	 */
	private static final long serialVersionUID = -5642277833139693453L;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField textFieldForename;
	private JTextField textFieldSurname;
	private JTextField textFieldName;
	private JTextField textFieldKuerzel;
	private JTextField textFieldDwz;
	private final ButtonPanelView buttonPane;
	// private JButton addSpielerButton;
	private JComboBox<String> textComboBoxAge;
	private JTextField textFieldZPS;
	private JTextField textFieldMGL;
	private JTextField textFieldDwzIndex;
	private JTextField textFieldFideId;
	private JTextField textFieldELO;
	private final JButton tournamentsButton;
	private final ImageIcon tournamentsIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/games-highscores.png"))); //$NON-NLS-1$

	public EditPlayerView(final Player spieler) {
		// final Dimension dim = new Dimension(150, 40);
		buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		okButton = buttonPane.getOkButton();
		cancelButton = buttonPane.getCancelButton();
		tournamentsButton = new JButton(Messages.getString("SpielerEditierenView.17"), tournamentsIcon);
		textFieldName = new JTextField(15);
		textFieldForename = new JTextField(15);
		textFieldSurname = new JTextField(15);
		textFieldKuerzel = new JTextField(15);
		textFieldDwz = new JTextField(15);
		textFieldDwzIndex = new JTextField(15);
		textFieldZPS = new JTextField(15);
		textFieldMGL = new JTextField(15);
		textFieldFideId = new JTextField(15);
		textFieldELO = new JTextField(15);
		setTitle(Messages.getString("SpielerEditierenView.2")); //$NON-NLS-1$
		// setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		final JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		JPanel centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));

		textFieldForename.setText(spieler.getForename());
		OwnLabel label = new OwnLabel();
		// label.setPreferredSize(dim);
		label.setText(Messages.getString("SpielerEditierenView.10")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.add(label);
		centerPane.add(textFieldForename);
		contentPanel.add(centerPane);

		textFieldSurname.setText(spieler.getSurname());
		label = new OwnLabel();
		label.setText(Messages.getString("SpielerEditierenView.11")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.add(label);
		centerPane.add(textFieldSurname);
		contentPanel.add(centerPane);

		textFieldKuerzel.setText(spieler.getKuerzel());
		label = new OwnLabel();

		label.setText(Messages.getString("SpielerEditierenView.4")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.add(label);
		centerPane.add(textFieldKuerzel);
		contentPanel.add(centerPane);

		textFieldDwz.setText(spieler.getDwz());
		label = new OwnLabel();

		label.setText(Messages.getString("SpielerEditierenView.5")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.add(label);
		centerPane.add(textFieldDwz);
		contentPanel.add(centerPane);

		textFieldDwzIndex.setText(Integer.toString(spieler.getDwzData().getCsvIndex()));
		label = new OwnLabel();

		label.setText(Messages.getString("SpielerEditierenView.14")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.add(label);
		centerPane.add(textFieldDwzIndex);
		contentPanel.add(centerPane);

		final String[] ageStrings = { Messages.getString("SpielerEditierenView.6"), //$NON-NLS-1$
				Messages.getString("SpielerEditierenView.7"), Messages.getString("SpielerEditierenView.8") }; //$NON-NLS-1$ //$NON-NLS-2$
		textComboBoxAge = new JComboBox<String>(ageStrings);
		textComboBoxAge.setSelectedIndex(spieler.getAge());
		label = new OwnLabel();

		label.setText(Messages.getString("SpielerEditierenView.9")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.add(label);
		centerPane.add(textComboBoxAge);
		contentPanel.add(centerPane);

		textFieldZPS.setText(spieler.getDwzData().getCsvZPS());
		label = new OwnLabel();

		label.setText(Messages.getString("SpielerEditierenView.12")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.add(label);
		centerPane.add(textFieldZPS);
		contentPanel.add(centerPane);

		textFieldMGL.setText(spieler.getDwzData().getCsvMgl_Nr());
		label = new OwnLabel();

		label.setText(Messages.getString("SpielerEditierenView.13")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.add(label);
		centerPane.add(textFieldMGL);

		contentPanel.add(centerPane);
		int fideId = -1;
		if (spieler.getDwzData().getCsvFIDE_ID() > 0) {
			fideId = spieler.getDwzData().getCsvFIDE_ID();
		}
		if (spieler.getEloData().getFideid() > 0) {
			fideId = spieler.getEloData().getFideid();
		}
		if (fideId > 0) {
			textFieldFideId.setText(Integer.toString(fideId));
		}
		label = new OwnLabel();

		label.setText(Messages.getString("SpielerEditierenView.15")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.add(label);
		centerPane.add(textFieldFideId);

		contentPanel.add(centerPane);

		int elo = -1;
		if (spieler.getDwzData().getCsvFIDE_Elo() > 0) {
			elo = spieler.getDwzData().getCsvFIDE_Elo();
		}
		if (spieler.getEloData().getRating() > 0) {
			elo = spieler.getEloData().getRating();
		}
		if (elo > 0) {
			textFieldELO.setText(Integer.toString(elo));
		}
		label = new OwnLabel();

		label.setText(Messages.getString("SpielerEditierenView.16")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.add(label);
		centerPane.add(textFieldELO);
		contentPanel.add(centerPane);

		// label = new OwnLabel();
		//
		// label.setText(Messages.getString("SpielerEditierenView.18")); //$NON-NLS-1$
		// centerPane = new JPanel();
		// centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.add(label);
		// centerPane.add(tournamentsButton);
		//
		// contentPanel.add(centerPane);

		contentPanel.add(buttonPane);
		add(contentPanel);
		contentPanel.updateUI();

	}

	public void showDialog() {
		pack();

		setLocationRelativeTo(null);
		setEnabled(true);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setVisible(true);

	}

	public void closeWindow() {
		dispose();
	}

	public JButton getTournamentsButton() {
		return tournamentsButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JComboBox<String> getTextComboBoxAge() {
		return textComboBoxAge;
	}

	public JTextField getTextFieldDwz() {
		return textFieldDwz;
	}

	public JTextField getTextFieldDwzIndex() {
		return textFieldDwzIndex;
	}

	public JTextField getTextFieldELO() {
		return textFieldELO;
	}

	public JTextField getTextFieldFideId() {
		return textFieldFideId;
	}

	public JTextField getTextFieldForename() {
		return textFieldForename;
	}

	public JTextField getTextFieldKuerzel() {
		return textFieldKuerzel;
	}

	public JTextField getTextFieldMGL() {
		return textFieldMGL;
	}

	public JTextField getTextFieldName() {
		return textFieldName;
	}

	public JTextField getTextFieldSurname() {
		return textFieldSurname;
	}

	public JTextField getTextFieldZPS() {
		return textFieldZPS;
	}

	// public void setAddSpielerButton(final JButton addSpielerButton) {
	// this.addSpielerButton = addSpielerButton;
	// }

	public void setCancelButton(final JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setOkButton(final JButton okButton) {
		this.okButton = okButton;
	}

	public void setTextComboBoxAge(final JComboBox<String> textComboBoxAge) {
		this.textComboBoxAge = textComboBoxAge;
	}

	public void setTextFieldDwz(final JTextField textFieldDwz) {
		this.textFieldDwz = textFieldDwz;
	}

	public void setTextFieldDwzIndex(final JTextField textFieldDwzIndex) {
		this.textFieldDwzIndex = textFieldDwzIndex;
	}

	public void setTextFieldELO(final JTextField textFieldELO) {
		this.textFieldELO = textFieldELO;
	}

	public void setTextFieldFideId(final JTextField textFieldFideId) {
		this.textFieldFideId = textFieldFideId;
	}

	public void setTextFieldForename(final JTextField textFieldForename) {
		this.textFieldForename = textFieldForename;
	}

	public void setTextFieldKuerzel(final JTextField textFieldKuerzel) {
		this.textFieldKuerzel = textFieldKuerzel;
	}

	public void setTextFieldMGL(final JTextField textFieldMGL) {
		this.textFieldMGL = textFieldMGL;
	}

	public void setTextFieldName(final JTextField name) {
		textFieldName = name;
	}

	public void setTextFieldSurname(final JTextField textFieldSurname) {
		this.textFieldSurname = textFieldSurname;
	}

	public void setTextFieldZPS(final JTextField textFieldZPS) {
		this.textFieldZPS = textFieldZPS;
	}

}
