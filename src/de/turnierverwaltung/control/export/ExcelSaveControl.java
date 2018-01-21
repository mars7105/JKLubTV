package de.turnierverwaltung.control.export;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.table.CrossTable;

/**
 * 
 * @author mars
 *
 */
public class ExcelSaveControl {

	private MainControl mainControl;
	private Workbook wb;

	/**
	 * 
	 * @param mainControl
	 */
	public ExcelSaveControl(MainControl mainControl) {
		this.mainControl = mainControl;
	}

	/**
	 * 
	 */
	public void saveExcelFile() {
		Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		if (ready) {
			int anzahlGruppen = this.mainControl.getTournament().getAnzahlGruppen();
			String filename = mainControl.getTournament().getTurnierName();

			File path = new File(mainControl.getPropertiesControl().getDefaultPath());

			JFileChooser savefile = new JFileChooser(path);
			FileFilter filter = new FileNameExtensionFilter("XLS", "xls"); //$NON-NLS-1$ //$NON-NLS-2$
			savefile.addChoosableFileFilter(filter);
			savefile.setFileFilter(filter);
			savefile.setSelectedFile(new File(filename));
			savefile.setDialogType(JFileChooser.SAVE_DIALOG);

			int sf = savefile.showSaveDialog(null);
			if (sf == JFileChooser.APPROVE_OPTION) {
				// create a new file
				OutputStream out;

				wb = new HSSFWorkbook();
				Font font = wb.createFont();
				// set font 1 to 12 point type
				font.setFontHeightInPoints((short) 12);
				// make it blue
				font.setColor((short) 0xc);
				Sheet[] s = new Sheet[anzahlGruppen];
				Sheet[] s2 = new Sheet[anzahlGruppen];

				CellStyle cellStyle = wb.createCellStyle();
				cellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
				cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
				cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
				cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
				cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
				cellStyle.setFont(font);

				for (int i = 0; i < anzahlGruppen; i++) {

					if (this.mainControl.getCrossTable()[i] == null) {
						this.mainControl.getCrossTableControl().makeSimpleTableView(i);

						this.mainControl.getMeetingTableControl().makeSimpleTableView(i);

					}

					CrossTable turnierTabelle = mainControl.getCrossTable()[i];

					int spalte = this.mainControl.getCrossTableView()[i].getTable().getModel().getColumnCount();
					int zeile = this.mainControl.getCrossTableView()[i].getTable().getModel().getRowCount();
					int spalte2 = this.mainControl.getMeetingTable()[i].getSpaltenAnzahl();
					int zeile2 = this.mainControl.getMeetingTable()[i].getZeilenAnzahl();
					s[i] = wb.createSheet(
							this.mainControl.getTournament().getGruppe()[i].getGruppenName() + " - Kreuztabelle");
					s2[i] = wb.createSheet(
							this.mainControl.getTournament().getGruppe()[i].getGruppenName() + " - Termintabelle");
					PrintSetup ps = s[i].getPrintSetup();

					s[i].setAutobreaks(true);

					ps.setFitHeight((short) 1);
					ps.setFitWidth((short) 1);
					PrintSetup ps2 = s2[i].getPrintSetup();

					s2[i].setAutobreaks(true);

					ps2.setFitHeight((short) 1);
					ps2.setFitWidth((short) 1);
					// declare a row object reference
					Row r = null;
					// declare a cell object reference
					Cell c = null;
					short rownum;
					String rep;

					// declare a row object reference
					Row r2 = null;
					// declare a cell object reference
					Cell c2 = null;
					short rownum2;

					for (int y = 0; y < zeile + 1; y++) {
						rownum = (short) y;
						// create a row
						r = s[i].createRow(rownum);
						int inc = 0;
						short cellnum;
						for (int x = 0; x < spalte; x++) {

							if (y < zeile) {
								turnierTabelle.getTabellenMatrix()[x][y
										+ 1] = (String) this.mainControl.getCrossTableView()[i].getTable().getValueAt(y,
												x);
							}
							cellnum = (short) (x - inc);
							// create a numeric cell
							c = r.createCell(cellnum);

							c.setCellStyle(cellStyle);

							rep = turnierTabelle.getTabellenMatrix()[x][y];
							rep = rep.replaceAll("<br />", "");
							c.setCellValue(rep);
							s[i].autoSizeColumn((x - inc));

						}

						r.setHeight((short) (r.getHeight() * 2));

					}
					for (int y = 0; y < zeile2; y++) {
						rownum2 = (short) y;
						// create a row
						r2 = s2[i].createRow(rownum2);

						short cellnum2;
						for (int x = 0; x < spalte2; x++) {
							cellnum2 = (short) x;
							// create a numeric cell
							c2 = r2.createCell(cellnum2);

							c2.setCellStyle(cellStyle);

							c2.setCellValue(this.mainControl.getMeetingTableControl().getTerminTabelle()[i]
									.getTabellenMatrix()[x][y]);

							s2[i].autoSizeColumn(x);
						}

						r2.setHeight((short) (r2.getHeight() * 2));

					}

				}
				File filename1 = new File(savefile.getCurrentDirectory() + "/" //$NON-NLS-1$
						+ filename + ".xls"); //$NON-NLS-1$
				int n = 0;

				if (filename1.exists()) {

					Object[] options = { Messages.getString("SaveDialog.2"), Messages.getString("SaveDialog.3") };
					n = JOptionPane.showOptionDialog(null,
							Messages.getString("SaveDialog.0") + filename1.getAbsolutePath()
									+ Messages.getString("SaveDialog.1"),
							"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
							options[1]);

				}
				if (n == 0) {

					try {
						out = new FileOutputStream(filename1);

						wb.write(out);
						out.close();

					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.16")); //$NON-NLS-1$
					}
				}

				// JOptionPane.showMessageDialog(null,
				// Messages.getString("HTMLSaveControler.18")); //$NON-NLS-1$
				// first check if Desktop is supported by
				// Platform or not
				if (!Desktop.isDesktopSupported())

				{
					JOptionPane.showMessageDialog(null, Messages.getString("HTMLSaveControler.19"), //$NON-NLS-1$
							Messages.getString("HTMLSaveControler.20"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
				} else

				{
					Desktop desktop = Desktop.getDesktop();

					try {
						desktop.open(savefile.getCurrentDirectory());
					} catch (IOException e) {
						// TODO Auto-generated catch block

					}
				}

			}

		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

		}

	}
}
