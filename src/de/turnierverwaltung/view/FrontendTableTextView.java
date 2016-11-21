package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FrontendTableTextView {

	private JPanel mainPanel;
	private JPanel htmlAll;
	private JTextField headerCrossTextField;
	private JTextArea bodyCrossTextArea;
//	private ButtonPanelView buttonPane;
//	private JButton okButton;

	private JPanel htmlAll2;
	private JTextField headerMeetingTextField;
	private JTextArea bodyMeetingTextArea;

	private JTabbedPane tabPane;
	private ColorSelectorView crossColorSelectorPanel;
	private ColorSelectorView meetingColorSelectorPanel;

	public FrontendTableTextView() {
		// Erzeugung eines neuen Frames mit
		// dem Titel Beispiel JDialog
		mainPanel = new JPanel();
		tabPane = new JTabbedPane();
		// mainPanel.setAlwaysOnTop(true);
		// Titel wird gesetzt
		// mainPanel.setTitle(Messages.getString("FrontendTableTextView.0"));
		// Breite und Höhe des Fensters werden
		// auf 200 Pixel gesetzt
		mainPanel.setSize(600, 500);
		// Dialog wird auf modal gesetzt
		// mainPanel.setModal(true);
		mainPanel.setLayout(new BorderLayout());
		htmlAll = new JPanel();
		htmlAll.setLayout(new BorderLayout());

		htmlAll2 = new JPanel();
		htmlAll2.setLayout(new BorderLayout());
		Dimension dimTextField = new Dimension(350, 30);
		int textFieldColumns = 18;
		int textFieldRows = 18;

		ContextMenuMouseListener cmmL = new ContextMenuMouseListener();
		ContextMenuMouseListener cmmL2 = new ContextMenuMouseListener();

		headerCrossTextField = new JTextField("", textFieldColumns);
		headerCrossTextField.setPreferredSize(dimTextField);
		headerCrossTextField.addMouseListener(cmmL);

		bodyCrossTextArea = new JTextArea("", textFieldRows, textFieldColumns);
		bodyCrossTextArea.addMouseListener(cmmL);

//		buttonPane = new ButtonPanelView();
//		buttonPane.makeOKButton();
//		okButton = buttonPane.getOkButton();

		headerMeetingTextField = new JTextField("", textFieldColumns);
		headerMeetingTextField.setPreferredSize(dimTextField);
		headerMeetingTextField.addMouseListener(cmmL2);

		bodyMeetingTextArea = new JTextArea("", textFieldRows, textFieldColumns);
		bodyMeetingTextArea.addMouseListener(cmmL2);
		crossColorSelectorPanel = new ColorSelectorView();
		meetingColorSelectorPanel = new ColorSelectorView();
	}

	public void makeDialog() {
		makeCrossTableText();
		makeMeetingTableText();
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
//		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		main.add(tabPane, BorderLayout.CENTER);
//		main.add(buttonPane, BorderLayout.SOUTH);

		mainPanel.add(main, BorderLayout.CENTER);
		// mainPanel.pack();
		// mainPanel.setLocationRelativeTo(null);
		mainPanel.setEnabled(true);
		mainPanel.setVisible(true);
	}

	private void makeCrossTableText() {
		JPanel bothPanel = new JPanel();
		bothPanel.setLayout(new BorderLayout());
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel headerTextFieldLabel = new JLabel(Messages.getString("FrontendTableTextView.1") + ":");
		headerTextFieldLabel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel bodyTextAreaLabel = new JLabel(Messages.getString("FrontendTableTextView.2") + ":");
		bodyTextAreaLabel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel crossColorSelectorLabel = new JLabel(Messages.getString("FrontendSidePanelView.3") + ":");
		// bodyTextAreaLabel.setPreferredSize(dim);
		crossColorSelectorLabel.setLayout(new FlowLayout(FlowLayout.LEFT));

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new BoxLayout(htmlPanel, BoxLayout.PAGE_AXIS));
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(headerTextFieldLabel);
		htmlPanel.add(labelPanel);
		htmlPanel.add(headerCrossTextField);
		// leftPanel.add(htmlPanel, BorderLayout.NORTH);
		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(crossColorSelectorLabel);
		htmlPanel.add(labelPanel);
		htmlPanel.add(crossColorSelectorPanel);
		leftPanel.add(htmlPanel, BorderLayout.NORTH);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new BoxLayout(htmlPanel, BoxLayout.PAGE_AXIS));
		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(bodyTextAreaLabel);
		htmlPanel.add(labelPanel);
		Dimension dimTextArea = new Dimension(350, 350);
		JScrollPane scrollPane = new JScrollPane(bodyCrossTextArea);
		scrollPane.setPreferredSize(dimTextArea);
		htmlPanel.add(scrollPane);
		leftPanel.add(htmlPanel, BorderLayout.CENTER);

		bothPanel.add(leftPanel, BorderLayout.NORTH);
		htmlAll.add(bothPanel, BorderLayout.CENTER);

		JPanel right = new JPanel();

		right.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		right.add(htmlAll);

		tabPane.addTab("Kreuztabelle", right);

	}

	private void makeMeetingTableText() {
		JPanel bothPanel = new JPanel();
		bothPanel.setLayout(new BorderLayout());
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel headerTextFieldLabel = new JLabel(Messages.getString("FrontendTableTextView.1") + ":");
		headerTextFieldLabel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel bodyTextAreaLabel = new JLabel(Messages.getString("FrontendTableTextView.2") + ":");
		bodyTextAreaLabel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel meetingColorSelectorLabel = new JLabel(Messages.getString("FrontendSidePanelView.3") + ":");
		// bodyTextAreaLabel.setPreferredSize(dim);
		meetingColorSelectorLabel.setLayout(new FlowLayout(FlowLayout.LEFT));

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new BoxLayout(htmlPanel, BoxLayout.PAGE_AXIS));
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(headerTextFieldLabel);
		htmlPanel.add(labelPanel);
		htmlPanel.add(headerMeetingTextField);
		// leftPanel.add(htmlPanel, BorderLayout.NORTH);

		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(meetingColorSelectorLabel);
		htmlPanel.add(labelPanel);
		htmlPanel.add(meetingColorSelectorPanel);
		leftPanel.add(htmlPanel, BorderLayout.NORTH);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new BoxLayout(htmlPanel, BoxLayout.PAGE_AXIS));
		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(bodyTextAreaLabel);
		htmlPanel.add(labelPanel);
		Dimension dimTextArea = new Dimension(350, 350);
		JScrollPane scrollPane = new JScrollPane(bodyMeetingTextArea);
		scrollPane.setPreferredSize(dimTextArea);
		htmlPanel.add(scrollPane);
		leftPanel.add(htmlPanel, BorderLayout.CENTER);

		bothPanel.add(leftPanel, BorderLayout.NORTH);
		htmlAll2.add(bothPanel, BorderLayout.CENTER);

		JPanel right = new JPanel();

		right.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		right.add(htmlAll2);

		tabPane.addTab("Termintabelle", right);
	}

	public JTextField getHeaderCrossTextField() {
		return headerCrossTextField;
	}

	public void setHeaderCrossTextField(JTextField headerCrossTextField) {
		this.headerCrossTextField = headerCrossTextField;
	}

	public JTextArea getBodyCrossTextArea() {
		return bodyCrossTextArea;
	}

	public void setBodyCrossTextArea(JTextArea bodyCrossTextArea) {
		this.bodyCrossTextArea = bodyCrossTextArea;
	}

	public JTextField getHeaderMeetingTextField() {
		return headerMeetingTextField;
	}

	public void setHeaderMeetingTextField(JTextField headerMeetingTextField) {
		this.headerMeetingTextField = headerMeetingTextField;
	}

	public JTextArea getBodyMeetingTextArea() {
		return bodyMeetingTextArea;
	}

	public void setBodyMeetingTextArea(JTextArea bodyMeetingTextArea) {
		this.bodyMeetingTextArea = bodyMeetingTextArea;
	}

//	public JButton getOkButton() {
//		return okButton;
//	}
//
//	public void setOkButton(JButton okButton) {
//		this.okButton = okButton;
//	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public ColorSelectorView getCrossColorSelectorPanel() {
		return crossColorSelectorPanel;
	}

	public void setCrossColorSelectorPanel(ColorSelectorView crossColorSelectorPanel) {
		this.crossColorSelectorPanel = crossColorSelectorPanel;
	}

	public ColorSelectorView getMeetingColorSelectorPanel() {
		return meetingColorSelectorPanel;
	}

	public void setMeetingColorSelectorPanel(ColorSelectorView meetingColorSelectorPanel) {
		this.meetingColorSelectorPanel = meetingColorSelectorPanel;
	}

}
