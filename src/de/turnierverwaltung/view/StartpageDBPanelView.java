package de.turnierverwaltung.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class StartpageDBPanelView extends StartpagePanelView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private ImageIcon dbNewIcon = new ImageIcon(
//			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/db_add.png")));
//	private JButton newDatabseButton;

	public StartpageDBPanelView() {
		super();
		setLayout(new BorderLayout());
//		Color titleColor = new Color((SystemColor.text).getRGB());
//		Color titleTextColor = new Color((SystemColor.textText).getRGB());

		setBorder(new EmptyBorder(25, 25, 25, 25));
		this.setLayout(new BorderLayout());
//		newDatabseButton = new JButton(Messages.getString("NaviView.11"), dbNewIcon); //$NON-NLS-1$
//		newDatabseButton.setPreferredSize(new Dimension(275, 40));
//		newDatabseButton.setHorizontalAlignment(SwingConstants.LEFT);
//		JPanel dbPanel = new JPanel();
//		dbPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		dbPanel.add(newDatabseButton);

		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());
		StartpageDBHTMLView startPage = new StartpageDBHTMLView();
		JScrollPane scrollPane = new JScrollPane(startPage);
		
		panel.add(scrollPane, BorderLayout.CENTER);
//		panel.add(dbPanel, BorderLayout.CENTER);
		add(panel);

	}

}
