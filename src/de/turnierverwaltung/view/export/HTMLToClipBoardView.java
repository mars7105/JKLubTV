package de.turnierverwaltung.view.export;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.turnierverwaltung.view.Messages;

public class HTMLToClipBoardView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton copyToClipBoardButton;
	private JLabel label;
	private ImageIcon copyIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-copy-2.png")));

	public HTMLToClipBoardView() {
		super();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		label = new JLabel();
		label.setPreferredSize(new Dimension(500, 40));
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		copyToClipBoardButton = new JButton(Messages.getString("HTMLToClipBoardView.0"), copyIcon);
		copyToClipBoardButton.setBorder(new EmptyBorder(5, 5, 5, 5));

		add(label, BorderLayout.WEST);
		add(copyToClipBoardButton, BorderLayout.EAST);

	}

	public JButton getCopyToClipBoardButton() {
		return copyToClipBoardButton;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setCopyToClipBoardButton(JButton copyToClipBoardButton) {
		this.copyToClipBoardButton = copyToClipBoardButton;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

}
