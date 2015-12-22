package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class EigenschaftenView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JScrollPane scrollPane;
	private JPanel centerPane;
	
	private JCheckBox checkBoxHeaderFooter;

	/**
	 * Create the panel.
	 */
	public EigenschaftenView() {
		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel("Eigenschaften");
		JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		titlepanel.add(titleLabel);

		northPanel.add(titlepanel);
		northPanel.add(topPanel);
		add(northPanel, BorderLayout.NORTH);
		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.PAGE_AXIS));
		// centerPane.setPreferredSize(new Dimension(900,1000));
		makeHTMLEigenschaften();
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(centerPane);
		// contentPanel.add(scrollPane);
		add(scrollPane, BorderLayout.CENTER);
	}

	private void makeHTMLEigenschaften() {
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel labelHeader = new JLabel(
				"HTML Datei ohne Header und Footer. (<table> ... </table>)");

		checkBoxHeaderFooter = new JCheckBox();
		htmlPanel.add(checkBoxHeaderFooter);
		htmlPanel.add(labelHeader);

		JPanel htmlAll = new JPanel();
		htmlAll.setLayout(new BorderLayout());
		htmlAll.add(htmlPanel, BorderLayout.NORTH);
		htmlAll.add(new JSeparator());
		JPanel title = new JPanel();
		title.setLayout(new FlowLayout(FlowLayout.LEFT));
		title.add(new JLabel("HTML Ausgabe"));
		centerPane.add(title);
		centerPane.add(htmlAll);
	}

	public JCheckBox getCheckBoxHeaderFooter() {
		return checkBoxHeaderFooter;
	}

	public void setCheckBoxHeaderFooter(JCheckBox checkBoxHeaderFooter) {
		this.checkBoxHeaderFooter = checkBoxHeaderFooter;
	}

}
