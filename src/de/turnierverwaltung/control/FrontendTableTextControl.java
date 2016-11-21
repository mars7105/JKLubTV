package de.turnierverwaltung.control;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.turnierverwaltung.view.ColorSelectorView;
import de.turnierverwaltung.view.FrontendTableTextView;
import de.turnierverwaltung.view.WebsiteConfigView;

public class FrontendTableTextControl {

	private FrontendTableTextView fd;
	private JTextField crossHeader;
	private JTextArea crossBody;
	private JTextField meetingHeader;
	private JTextArea meetingBody;
	private ColorSelectorView crossColorSelector;
	private ColorSelectorView meetingColorSelector;
	private MainControl mainControl;

	public FrontendTableTextControl(MainControl mainControl) {
		this.mainControl = mainControl;
		fd = new FrontendTableTextView();
		crossHeader = fd.getHeaderCrossTextField();
		crossBody = fd.getBodyCrossTextArea();
		meetingHeader = fd.getHeaderMeetingTextField();
		meetingBody = fd.getBodyMeetingTextArea();
		crossColorSelector = fd.getCrossColorSelectorPanel();
		meetingColorSelector = fd.getMeetingColorSelectorPanel();
	}

	public void makeDialog(String title) {
		fd.getHeaderCrossTextField().setText(crossHeader.getText());
		fd.getBodyCrossTextArea().setText(crossBody.getText());
		fd.getHeaderMeetingTextField().setText(meetingHeader.getText());
		fd.getBodyMeetingTextArea().setText(meetingBody.getText());
		fd.getCrossColorSelectorPanel().setSelectedIndex(crossColorSelector.getSelectedIndex());
		fd.getMeetingColorSelectorPanel().setSelectedIndex(meetingColorSelector.getSelectedIndex());
		fd.makeDialog();
		WebsiteConfigView webconfigView = null;
		if (mainControl.getWebconfigView() == null) {
			webconfigView = new WebsiteConfigView();
			mainControl.setWebconfigView(webconfigView);
		} else {
			webconfigView = mainControl.getWebconfigView();
		}
		webconfigView.getDialog().setLocationRelativeTo(mainControl);
		webconfigView.getDialog();
		webconfigView.getTabbedPane().addTab(title, fd.getMainPanel());

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
