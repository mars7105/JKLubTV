package de.turnierverwaltung.control.export;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.CopyToClipboard;
import de.turnierverwaltung.model.table.CrossTable;
import de.turnierverwaltung.model.table.NewsArticle;
import de.turnierverwaltung.view.export.HTMLToClipBoardDialogView;
import de.turnierverwaltung.view.export.HTMLToClipBoardView;
import de.turnierverwaltung.view.export.HTMLToClipBoardWithSelectBoxView;

public class HTMLCopyToClipboardControl {

	private final MainControl mainControl;

	public HTMLCopyToClipboardControl(final MainControl mainControl) {
		super();
		this.mainControl = mainControl;

	}

	public void copyToClipboard() {
		final Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		if (ready) {
			final Boolean ohneHeaderundFooter = mainControl.getPropertiesControl().getOnlyTables();

			final int anzahlGruppen = mainControl.getTournament().getAnzahlGruppen();
			final String cssTable = mainControl.getPropertiesControl().getCSSTable();

			final String webserverPath = mainControl.getPropertiesControl().getWebserverPath();
			final HTMLToClipBoardDialogView htmlToClipboardDialog = new HTMLToClipBoardDialogView();

			final ArrayList<HTMLToClipBoardView> htmlToClipboardArray = new ArrayList<HTMLToClipBoardView>();
			HashMap<String, String[][]> allNewsArticleMatrix = new HashMap<String, String[][]>();
			for (int i = 0; i < anzahlGruppen; i++) {

				String wfn = mainControl.getTournament().getTurnierName();
				wfn += Messages.getString("PDFSaveControler.2")
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".pdf";
				final String webfilename1 = wfn.replaceAll(" ", "");

				String wfn2 = mainControl.getTournament().getTurnierName();
				wfn2 += Messages.getString("PDFSaveControler.8")
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".pdf";
				final String webfilename2 = wfn2.replaceAll(" ", "");

				final String ical = mainControl.getTournament().getTurnierName()
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".ics";
				final String webfilename3 = ical.replaceAll(" ", "");

				final String crossLabel = mainControl.getTournament().getTurnierName() + " "
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.22");
				final String meetingLabel = mainControl.getTournament().getTurnierName() + " "
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.23");
				final String allLabel = mainControl.getTournament().getTurnierName() + " "
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.21");
				final String newsLabel = mainControl.getTournament().getTurnierName() + " "
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + " " + "News Article";
				if (mainControl.getCrossTable()[i] == null) {
					mainControl.getCrossTableControl().makeSimpleTableView(i);

					mainControl.getMeetingTableControl().makeSimpleTableView(i);

				}

				final CrossTable turnierTabelle = mainControl.getCrossTable()[i];

				final int spalte = mainControl.getCrossTableView()[i].getTable().getModel().getColumnCount();
				final int zeile = mainControl.getCrossTableView()[i].getTable().getModel().getRowCount();
				for (int x = 0; x < spalte; x++) {
					for (int y = 0; y < zeile; y++) {

						turnierTabelle.getTabellenMatrix()[x][y + 1] = (String) mainControl.getCrossTableView()[i]
								.getTable().getValueAt(y, x);

					}
				}

				final Boolean showLink = mainControl.getPropertiesControl().getPDFLinks();

				final HTMLToClipBoardView crosshtmlToClipboard = new HTMLToClipBoardView();
				crosshtmlToClipboard.getLabel().setText(crossLabel);
				final String crosstable = mainControl.getCrossTable()[i].getHTMLTable(ohneHeaderundFooter,
						webserverPath, webfilename1, showLink, cssTable);
				crosshtmlToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						final CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(crosstable);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + crossLabel);
					}

				});

				htmlToClipboardArray.add(crosshtmlToClipboard);

				final HTMLToClipBoardView meetinghtmlToClipboard = new HTMLToClipBoardView();
				meetinghtmlToClipboard.getLabel().setText(meetingLabel);

				final String meetingtable = mainControl.getMeetingTableControl().getTerminTabelle()[i].getHTMLTable(
						ohneHeaderundFooter, webserverPath, webfilename2, webfilename3, showLink, cssTable);
				meetinghtmlToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						final CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(meetingtable);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + meetingLabel);
					}

				});

				htmlToClipboardArray.add(meetinghtmlToClipboard);

				final HTMLToClipBoardView allToClipboard = new HTMLToClipBoardView();
				allToClipboard.getLabel().setText(allLabel);
				final String crosstableall = mainControl.getCrossTable()[i].getHTMLTableOnlyWithHeader(
						ohneHeaderundFooter, webserverPath, webfilename1, showLink, cssTable);
				final String meetingtableall = mainControl.getMeetingTableControl().getTerminTabelle()[i]
						.getHTMLTableOnlyWithFooter(ohneHeaderundFooter, webserverPath, webfilename2, webfilename3,
								showLink, cssTable);
				allToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {

						final CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(crosstableall + "\n" + meetingtableall);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + allLabel);
					}

				});

				htmlToClipboardArray.add(allToClipboard);
				allNewsArticleMatrix.put(mainControl.getTournament().getGruppe()[i].getGruppenName(),
						mainControl.getMeetingTableControl().getTerminTabelle()[i].getTabellenMatrix());
				final NewsArticle htmlArticle = new NewsArticle(
						mainControl.getMeetingTableControl().getTerminTabelle()[i].getTabellenMatrix(),
						mainControl.getTournament().getTurnierName() + " "
								+ mainControl.getTournament().getGruppe()[i].getGruppenName(),
						cssTable);
				final HTMLToClipBoardWithSelectBoxView newsHTMLContentToClipboard = new HTMLToClipBoardWithSelectBoxView(
						htmlArticle.getDateList());
				newsHTMLContentToClipboard.getLabel().setText(newsLabel);

				newsHTMLContentToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						final CopyToClipboard clipBoardcopy = new CopyToClipboard();
						int selectedIndex = newsHTMLContentToClipboard.getjComboBox().getSelectedIndex();
						String selectedItem = newsHTMLContentToClipboard.getjComboBox().getItemAt(selectedIndex);
						String newsHTMLContent = htmlArticle.getHtmlContent(ohneHeaderundFooter, selectedItem);

						clipBoardcopy.copy(newsHTMLContent);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + newsLabel);
					}

				});
				htmlToClipboardArray.add(newsHTMLContentToClipboard);
			}
			String groupname = mainControl.getTournament().getTurnierName();
			TreeMap<String, String[][]> tMap = new TreeMap<String, String[][]>();
			tMap.putAll(allNewsArticleMatrix);
			final NewsArticle allhtmlArticles = new NewsArticle(tMap, groupname, cssTable);
			final String newsLabel = mainControl.getTournament().getTurnierName() + " News Article (all Groups)";
			final HTMLToClipBoardWithSelectBoxView allNewsHTMLContentToClipboard = new HTMLToClipBoardWithSelectBoxView(
					allhtmlArticles.getDateList());
			allNewsHTMLContentToClipboard.getLabel().setText(newsLabel);

			allNewsHTMLContentToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					int selectedIndex = allNewsHTMLContentToClipboard.getjComboBox().getSelectedIndex();
					String selectedItem = allNewsHTMLContentToClipboard.getjComboBox().getItemAt(selectedIndex);
					String allNewsHTMLContent = allhtmlArticles.getHtmlContent(ohneHeaderundFooter, selectedItem);

					final CopyToClipboard clipBoardcopy = new CopyToClipboard();
					clipBoardcopy.copy(allNewsHTMLContent);
					htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + newsLabel);
				}

			});
			htmlToClipboardArray.add(allNewsHTMLContentToClipboard);
			htmlToClipboardDialog.makeDialog(htmlToClipboardArray);

			htmlToClipboardDialog.getButtonPanel().getOkButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					htmlToClipboardDialog.dispose();

				}

			});
			htmlToClipboardDialog.showDialog();
		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

		}
	}
}
