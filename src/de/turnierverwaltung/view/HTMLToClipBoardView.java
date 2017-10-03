package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HTMLToClipBoardView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton copyToClipBoardButton;
	private JLabel label;
	private ImageIcon copyIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-copy-2.png")));
	public HTMLToClipBoardView() {
		super();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		label = new JLabel();
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(5, 5, 5, 5));
		copyToClipBoardButton = new JButton(Messages.getString("HTMLToClipBoardView.0"),copyIcon);
		copyToClipBoardButton.setBorder(new EmptyBorder(5, 5, 5, 5));

		add(label, BorderLayout.WEST);
		add(copyToClipBoardButton, BorderLayout.EAST);

	}

	public JButton getCopyToClipBoardButton() {
		return copyToClipBoardButton;
	}

	public void setCopyToClipBoardButton(JButton copyToClipBoardButton) {
		this.copyToClipBoardButton = copyToClipBoardButton;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

}
