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

public class StartpageELOPanelView extends StartpagePanelView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel htmlAll;
	private URI fideHomepage;
	private JButton fideHomepageButton;
	private JButton openPlayersELOButton;
	private JLabel openPlayersELOLabel;
	private JButton convertELOToSQLITEButton;
	private JLabel convertELOToSQLITELabel;

	public StartpageELOPanelView() {
		super();
//		Color titleColor = new Color((SystemColor.text).getRGB());
//		Color titleTextColor = new Color((SystemColor.textText).getRGB());
		
		setBorder(new EmptyBorder(25, 25, 25, 25));

		TitleLabelView titleView = new TitleLabelView(Messages.getString("EigenschaftenView.51"));
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
		downloadELOListe();
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());
//		htmlAll.setBackground(titleColor);
//		htmlAll.setForeground(titleTextColor);
		wrapper.add(htmlAll, BorderLayout.NORTH);

		centerPane.add(wrapper, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(centerPane);
		add(scrollPane, BorderLayout.CENTER);
	}

	class OpenUrlAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			open(fideHomepage);

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

	private void downloadELOListe() {

		try {
			fideHomepage = new URI("https://ratings.fide.com/download.phtml"); //$NON-NLS-1$
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}


//		htmlAll.add(titleView);
		fideHomepageButton = new JButton();
		fideHomepageButton
				.setText("<HTML><FONT color=\"#000099\"><U>https://ratings.fide.com/download.phtml</U></FONT></HTML>"); //$NON-NLS-1$

		fideHomepageButton.setOpaque(false);
		fideHomepageButton.setToolTipText(fideHomepage.toString());
		fideHomepageButton.addActionListener(new OpenUrlAction());
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(fideHomepageButton);
		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel labelHeader1 = new JLabel(Messages.getString("EigenschaftenView.53") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.54")); //$NON-NLS-1$
		JLabel labelHeader1b = new JLabel(Messages.getString("EigenschaftenView.55") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.56")); //$NON-NLS-1$
		JLabel labelHeader2 = new JLabel(Messages.getString("EigenschaftenView.57") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.58")); //$NON-NLS-1$
		JLabel labelHeader2b = new JLabel(Messages.getString("EigenschaftenView.59")); //$NON-NLS-1$
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

		htmlAll.add(htmlPanel);

		openPlayersELOButton = new JButton(Messages.getString("EigenschaftenView.52")); //$NON-NLS-1$
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(openPlayersELOButton);
		openPlayersELOLabel = new JLabel();
		htmlPanel.add(openPlayersELOLabel);
		htmlAll.add(htmlPanel);

		convertELOToSQLITEButton = new JButton(Messages.getString("EigenschaftenView.66"));
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(convertELOToSQLITEButton);
		convertELOToSQLITELabel = new JLabel();
		htmlPanel.add(convertELOToSQLITELabel);
		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());
	}

	public JButton getOpenPlayersELOButton() {
		return openPlayersELOButton;
	}

	public void setOpenPlayersELOButton(JButton openPlayersELOButton) {
		this.openPlayersELOButton = openPlayersELOButton;
	}

	public JButton getConvertELOToSQLITEButton() {
		return convertELOToSQLITEButton;
	}

	public void setConvertELOToSQLITEButton(JButton convertELOToSQLITEButton) {
		this.convertELOToSQLITEButton = convertELOToSQLITEButton;
	}

	public JLabel getOpenPlayersELOLabel() {
		return openPlayersELOLabel;
	}

	public void setOpenPlayersELOLabel(JLabel openPlayersELOLabel) {
		this.openPlayersELOLabel = openPlayersELOLabel;
	}
	
}
