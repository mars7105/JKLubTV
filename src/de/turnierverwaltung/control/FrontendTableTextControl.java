package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.turnierverwaltung.view.ColorSelectorView;
import de.turnierverwaltung.view.FrontendTableTextView;

public class FrontendTableTextControl implements ActionListener {

	private JButton okButton;
	private JDialog dialog;
	private FrontendTableTextView fd;
	private JTextField crossHeader;
	private JTextArea crossBody;
	private JTextField meetingHeader;
	private JTextArea meetingBody;
	private ColorSelectorView crossColorSelector;
	private ColorSelectorView meetingColorSelector;

	public FrontendTableTextControl() {
		fd = new FrontendTableTextView();
		dialog = fd.getJsonDialog();
		okButton = fd.getOkButton();
		okButton.addActionListener(this);
		crossHeader = fd.getHeaderCrossTextField();
		crossBody = fd.getBodyCrossTextArea();
		meetingHeader = fd.getHeaderMeetingTextField();
		meetingBody = fd.getBodyMeetingTextArea();
		crossColorSelector = fd.getCrossColorSelectorPanel();
		meetingColorSelector = fd.getCrossColorSelectorPanel();
	}

	public void makeDialog() {

		fd.makeDialog();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		dialog.dispose();
	}

	public JTextField getCrossHeader() {
		return crossHeader;
	}

	public void setCrossHeader(JTextField crossHeader) {
		this.crossHeader = crossHeader;
	}

	public JTextArea getCrossBody() {
		return crossBody;
	}

	public void setCrossBody(JTextArea crossBody) {
		this.crossBody = crossBody;
	}

	public JTextField getMeetingHeader() {
		return meetingHeader;
	}

	public void setMeetingHeader(JTextField meetingHeader) {
		this.meetingHeader = meetingHeader;
	}

	public JTextArea getMeetingBody() {
		return meetingBody;
	}

	public void setMeetingBody(JTextArea meetingBody) {
		this.meetingBody = meetingBody;
	}

	public ColorSelectorView getCrossColorSelector() {
		return crossColorSelector;
	}

	public void setCrossColorSelector(ColorSelectorView crossColorSelector) {
		this.crossColorSelector = crossColorSelector;
	}

	public ColorSelectorView getMeetingColorSelector() {
		return meetingColorSelector;
	}

	public void setMeetingColorSelector(ColorSelectorView meetingColorSelector) {
		this.meetingColorSelector = meetingColorSelector;
	}

}
