package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConnectStatusView extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JButton okButton;

	public ConnectStatusView() {

		TitleLabelView titleView = new TitleLabelView(Messages.getString("ConnectStatusView.1"));

		setLayout(new BorderLayout());
		this.setAlwaysOnTop(true);
		setTitle(Messages.getString("ConnectStatusView.0"));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setSize(new Dimension(300, 400));
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		northPanel.add(titleView);
		northPanel.add(topPanel);
		getContentPane().add(northPanel, BorderLayout.NORTH);
		textArea = new JTextArea(8, 12);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		ButtonPanelView buttonPane = new ButtonPanelView();
		buttonPane.makeOKButton();
		okButton = buttonPane.getOkButton();
		okButton.addActionListener(this);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);
		setEnabled(true);
		setVisible(true);
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();

	}

}
