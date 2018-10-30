package de.turnierverwaltung.view.tournamentlist;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.turnierverwaltung.model.EventDate;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.view.ButtonPanelView;
import de.turnierverwaltung.view.DateChooserPanel;
import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.OwnLabel;

public class EditTournamentView extends JDialog {
	public class DateLabelFormatter extends AbstractFormatter {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private final String datePattern = Messages.getString("TurnierView.15"); //$NON-NLS-1$
		private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern, Locale.getDefault());

		@Override
		public Object stringToValue(final String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(final Object value) throws ParseException {
			if (value != null) {
				final Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return ""; //$NON-NLS-1$
		}

	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private JButton okButton;
	private JButton cancelButton;
	private JTextField textFieldTurnierName;
	private final ButtonPanelView buttonPane;
	private DateChooserPanel startDatumTextField;
	private DateChooserPanel endDatumTextField;

	private final Properties property;
	private JTextField[] textFieldGruppenName;
	private final JButton playerOfGroupButtons[];
	// private final JButton addGroupButton;
	// private final JButton deleteButtons[];
	// private final ImageIcon deleteIcon = new ImageIcon(
	// Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-group-delete-2.png")));
	// //$NON-NLS-1$
	// private final ImageIcon groupIcon = new ImageIcon(
	// Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-group-new-2.png")));
	// //$NON-NLS-1$
	private final ImageIcon playerIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-group.png"))); //$NON-NLS-1$

	public EditTournamentView(final Tournament turnier) {
		textFieldGruppenName = new JTextField[turnier.getAnzahlGruppen()];

		playerOfGroupButtons = new JButton[turnier.getAnzahlGruppen()];
		// deleteButtons = new JButton[turnier.getAnzahlGruppen()];

		property = new Properties();
		property.put("text.today", Messages.getString("TurnierEditierenView.3")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.month", Messages.getString("TurnierEditierenView.5")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.year", Messages.getString("TurnierEditierenView.7")); //$NON-NLS-1$ //$NON-NLS-2$

		textFieldTurnierName = new JTextField(20);
		// setAlwaysOnTop(true);
		setTitle(Messages.getString("TurnierEditierenView.10")); //$NON-NLS-1$
		// setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		final JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		JPanel centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		textFieldTurnierName.setText(turnier.getTurnierName());
		OwnLabel label = new OwnLabel();
		label.setText(Messages.getString("TurnierEditierenView.11")); //$NON-NLS-1$
		centerPane.add(label);
		centerPane.add(textFieldTurnierName);
		contentPanel.add(centerPane);

		startDatumTextField = new DateChooserPanel();
		startDatumTextField.setLocale(Locale.getDefault());
		final EventDate evstart = new EventDate(turnier.getStartDatum());
		if (turnier.getStartDatum().contains("/")) {
			startDatumTextField.setDateFormatString(evstart.getEnglishFormat());
		}
		if (turnier.getStartDatum().contains(".")) {
			startDatumTextField.setDateFormatString(evstart.getGermanFormat());
		}
		final Date dstart = evstart.getDate();
		startDatumTextField.setDate(dstart);
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		label = new OwnLabel();
		label.setText(Messages.getString("TurnierEditierenView.12")); //$NON-NLS-1$
		centerPane.add(label);
		centerPane.add(startDatumTextField);
		contentPanel.add(centerPane);

		endDatumTextField = new DateChooserPanel();
		endDatumTextField.setLocale(Locale.getDefault());
		final EventDate evend = new EventDate(turnier.getEndDatum());
		if (turnier.getEndDatum().contains("/")) {
			endDatumTextField.setDateFormatString(evend.getEnglishFormat());
		}
		if (turnier.getEndDatum().contains(".")) {
			endDatumTextField.setDateFormatString(evend.getGermanFormat());
		}

		final Date dend = evend.getDate();
		endDatumTextField.setDate(dend);

		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		label = new OwnLabel();
		label.setText(Messages.getString("TurnierEditierenView.13")); //$NON-NLS-1$
		centerPane.add(label);
		centerPane.add(endDatumTextField);
		contentPanel.add(centerPane);
		buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		okButton = buttonPane.getOkButton();
		cancelButton = buttonPane.getCancelButton();

		for (int i = 0; i < turnier.getAnzahlGruppen(); i++) {
			centerPane = new JPanel();
			centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
			textFieldGruppenName[i] = new JTextField(15);
			textFieldGruppenName[i].setText(turnier.getGruppe()[i].getGruppenName());
			playerOfGroupButtons[i] = new JButton(Messages.getString("TurnierEditierenView.15"), playerIcon);
			label = new OwnLabel();
			label.setText(Messages.getString("TurnierEditierenView.14") + (i + 1));
			// deleteButtons[i] = new JButton(Messages.getString("TurnierEditierenView.16"),
			// deleteIcon);
			//
			// deleteButtons[i].setActionCommand(Messages.getString("TurnierEditierenView.16"));

			centerPane.add(label);
			centerPane.add(textFieldGruppenName[i]);
			// centerPane.add(deleteButtons[i]);

			centerPane.add(playerOfGroupButtons[i]);
			contentPanel.add(centerPane);
		}
		// addGroupButton = new JButton(Messages.getString("TurnierEditierenView.17"),
		// groupIcon);
		// centerPane = new JPanel();
		// centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.add(addGroupButton);
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

	// public JButton getAddGroupButton() {
	// return addGroupButton;
	// }
	//
	// public JButton[] getDeleteButtons() {
	// return deleteButtons;
	// }

	public JButton[] getPlayerOfGroupButtons() {
		return playerOfGroupButtons;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public DateChooserPanel getEndDatumTextField() {
		return endDatumTextField;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public DateChooserPanel getStartDatumTextField() {
		return startDatumTextField;
	}

	public JTextField[] getTextFieldGruppenName() {
		return textFieldGruppenName;
	}

	public JTextField getTextFieldTurnierName() {
		return textFieldTurnierName;
	}

	public void setCancelButton(final JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setEndDatumTextField(final DateChooserPanel endDatumTextField) {
		this.endDatumTextField = endDatumTextField;
	}

	public void setOkButton(final JButton okButton) {
		this.okButton = okButton;
	}

	public void setStartDatumTextField(final DateChooserPanel startDatumTextField) {
		this.startDatumTextField = startDatumTextField;
	}

	public void setTextFieldGruppenName(final JTextField[] textFieldGruppenName) {
		this.textFieldGruppenName = textFieldGruppenName;
	}

	public void setTextFieldTurnierName(final JTextField textFieldTurnierName) {
		this.textFieldTurnierName = textFieldTurnierName;
	}

}
