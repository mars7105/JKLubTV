package de.turnierverwaltung.view;

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
import javax.swing.border.EmptyBorder;

public class StartpageDWZPanelView extends StartpagePanelView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StartpageDWZPanelView() {
		super();
//		Color titleColor = new Color((SystemColor.text).getRGB());
//		Color titleTextColor = new Color((SystemColor.textText).getRGB());
//		setBackground(titleColor);
//		setForeground(titleTextColor);
		setBorder(new EmptyBorder(25, 25, 25, 25));
		getTextfeld().setText("DWZ Test!");
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
		// downloadLinks();
		downloadDWZListe();
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());
//		htmlAll.setBackground(titleColor);
//		htmlAll.setForeground(titleTextColor);
		wrapper.add(htmlAll, BorderLayout.NORTH);

		centerPane.add(wrapper, BorderLayout.NORTH);
//		centerPane.setBackground(titleColor);
//		centerPane.setForeground(titleTextColor);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(centerPane);
		add(scrollPane, BorderLayout.CENTER);
	}

	class OpenUrlAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(dsbHomepageButton)) {
				open(dsbHomepage);
			}

		}

	}

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

	private JButton openVereineCSVButton;

	private JLabel openVereineCSVLabel;

	private JButton openPlayersCSVButton;

	private JLabel openPlayersCSVLabel;

	private JButton convertDWZToSQLITEButton;

	private JLabel convertDWZToSQLITELabel;

	private void downloadDWZListe() {
		// ohne Header und Footer
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
		JLabel labelHeader1 = new JLabel(Messages.getString("EigenschaftenView.10") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.11")); //$NON-NLS-1$
		JLabel labelHeader1b = new JLabel(Messages.getString("EigenschaftenView.12") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.13")); //$NON-NLS-1$
		JLabel labelHeader2 = new JLabel(Messages.getString("EigenschaftenView.14") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.15")); //$NON-NLS-1$
		JLabel labelHeader2b = new JLabel(Messages.getString("EigenschaftenView.16")); //$NON-NLS-1$
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

		openVereineCSVButton = new JButton(Messages.getString("EigenschaftenView.18")); //$NON-NLS-1$
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(openVereineCSVButton);
		openVereineCSVLabel = new JLabel();
		htmlPanel.add(openVereineCSVLabel);
		htmlAll.add(htmlPanel);

		openPlayersCSVButton = new JButton(Messages.getString("EigenschaftenView.43")); //$NON-NLS-1$
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(openPlayersCSVButton);
		openPlayersCSVLabel = new JLabel();
		htmlPanel.add(openPlayersCSVLabel);
		htmlAll.add(htmlPanel);

		convertDWZToSQLITEButton = new JButton(Messages.getString("EigenschaftenView.65"));
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(convertDWZToSQLITEButton);
		convertDWZToSQLITELabel = new JLabel();
		htmlPanel.add(convertDWZToSQLITELabel);
		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());

	}

	public JButton getDsbHomepageButton() {
		return dsbHomepageButton;
	}

	public void setDsbHomepageButton(JButton dsbHomepageButton) {
		this.dsbHomepageButton = dsbHomepageButton;
	}

	public JButton getOpenVereineCSVButton() {
		return openVereineCSVButton;
	}

	public void setOpenVereineCSVButton(JButton openVereineCSVButton) {
		this.openVereineCSVButton = openVereineCSVButton;
	}

	public JButton getOpenPlayersCSVButton() {
		return openPlayersCSVButton;
	}

	public void setOpenPlayersCSVButton(JButton openPlayersCSVButton) {
		this.openPlayersCSVButton = openPlayersCSVButton;
	}

	public JButton getConvertDWZToSQLITEButton() {
		return convertDWZToSQLITEButton;
	}

	public void setConvertDWZToSQLITEButton(JButton convertDWZToSQLITEButton) {
		this.convertDWZToSQLITEButton = convertDWZToSQLITEButton;
	}

	public JLabel getOpenVereineCSVLabel() {
		return openVereineCSVLabel;
	}

	public void setOpenVereineCSVLabel(JLabel openVereineCSVLabel) {
		this.openVereineCSVLabel = openVereineCSVLabel;
	}

	public JLabel getOpenPlayersCSVLabel() {
		return openPlayersCSVLabel;
	}

	public void setOpenPlayersCSVLabel(JLabel openPlayersCSVLabel) {
		this.openPlayersCSVLabel = openPlayersCSVLabel;
	}

}
