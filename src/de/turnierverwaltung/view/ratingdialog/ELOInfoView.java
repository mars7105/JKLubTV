package de.turnierverwaltung.view.ratingdialog;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.TitleLabelView;

public class ELOInfoView extends JPanel {
	class OpenUrlAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(dsbHomepageButton)) {
				open(dsbHomepage);
			}

		}

	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				/* TODO: error handling */}
		} else {
			/* TODO: error handling */}
	}

	private JButton dsbHomepageButton;

	private URI dsbHomepage;

	private JPanel htmlAll;

	public ELOInfoView() {
		TitleLabelView titleView = new TitleLabelView(Messages.getString("EigenschaftenView.5"));
		titleView.setFlowLayoutLeft();

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));

		northPanel.add(titleView);
		northPanel.add(topPanel);
		add(northPanel, BorderLayout.NORTH);
		JPanel centerPane = new JPanel();
		centerPane.setLayout(new BorderLayout());
		htmlAll = new JPanel();
		htmlAll.setLayout(new BoxLayout(htmlAll, BoxLayout.PAGE_AXIS));
		downloadLinks();
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());
		wrapper.add(htmlAll, BorderLayout.NORTH);

		centerPane.add(wrapper, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(centerPane);
		add(scrollPane, BorderLayout.CENTER);
	}

	private void downloadLinks() {

		try {
			dsbHomepage = new URI("http://www.schachbund.de/download.html"); //$NON-NLS-1$
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		dsbHomepageButton = new JButton();
		dsbHomepageButton
				.setText("<HTML><FONT color=\"#000099\"><U>http://www.schachbund.de/download.html</U></FONT></HTML>"); //$NON-NLS-1$

		dsbHomepageButton.setOpaque(false);
		dsbHomepageButton.setToolTipText(dsbHomepage.toString());
		dsbHomepageButton.addActionListener(new OpenUrlAction());
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(dsbHomepageButton);
		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel labelHeader1 = new JLabel(Messages.getString("EigenschaftenView.44") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.45")); //$NON-NLS-1$
		JLabel labelHeader1b = new JLabel(Messages.getString("EigenschaftenView.46") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.47")); //$NON-NLS-1$
		JLabel labelHeader2 = new JLabel(Messages.getString("EigenschaftenView.48") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.49")); //$NON-NLS-1$
		JLabel labelHeader2b = new JLabel(Messages.getString("EigenschaftenView.50")); //$NON-NLS-1$
		htmlPanel.add(labelHeader1);
		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(labelHeader1b);

		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(labelHeader2);
		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(labelHeader2b);
		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());

	}

	public JButton getDsbHomepageButton() {
		return dsbHomepageButton;
	}

	public void setDsbHomepageButton(JButton dsbHomepageButton) {
		this.dsbHomepageButton = dsbHomepageButton;
	}

}
