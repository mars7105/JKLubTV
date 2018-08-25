package de.turnierverwaltung.control.settingsdialog;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.view.settingsdialog.SettingsView;

public class SettingsControl {
	private final MainControl mainControl;
	private SettingsView eigenschaftenView;

	private ActionListenerSettingsControl actionListenerControl;
	private ItemListenerSettingsControl itemListenerControl;

	/**
	 * @param mainControl
	 */
	public SettingsControl(final MainControl mainControl) {
		this.mainControl = mainControl;
		eigenschaftenView = new SettingsView();
		actionListenerControl = new ActionListenerSettingsControl(this.mainControl, this);
		itemListenerControl = new ItemListenerSettingsControl(this.mainControl, this);

	}

	public ActionListenerSettingsControl getActionListenerControl() {
		return actionListenerControl;
	}

	public SettingsView getEigenschaftenView() {
		return eigenschaftenView;
	}

	public ItemListenerSettingsControl getItemListenerControl() {
		return itemListenerControl;
	}

	public void setActionListenerControl(final ActionListenerSettingsControl actionListenerControl) {
		this.actionListenerControl = actionListenerControl;
	}

	public void setEigenschaftenView(final SettingsView eigenschaftenView) {
		this.eigenschaftenView = eigenschaftenView;
	}

	public void setItemListenerControl(final ItemListenerSettingsControl itemListenerControl) {
		this.itemListenerControl = itemListenerControl;
	}

	public void setTableColumns() {
		final PropertiesControl ppC = mainControl.getPropertiesControl();
		eigenschaftenView.getWhiteTextField().setText(ppC.getTableComumnWhite());
		eigenschaftenView.getBlackTextField().setText(ppC.getTableComumnBlack());
		eigenschaftenView.getMeetingTextField().setText(ppC.getTableComumnMeeting());
		eigenschaftenView.getOldDWZTextField().setText(ppC.getTableComumnOldDWZ());
		eigenschaftenView.getNewDWZTextField().setText(ppC.getTableComumnNewDWZ());
		eigenschaftenView.getOldELOTextField().setText(ppC.getTableComumnOldELO());
		eigenschaftenView.getNewELOTextField().setText(ppC.getTableComumnNewELO());
		eigenschaftenView.getPlayerTextField().setText(ppC.getTableComumnPlayer());
		eigenschaftenView.getPointsTextField().setText(ppC.getTableComumnPoints());
		eigenschaftenView.getRankingTextField().setText(ppC.getTableComumnRanking());
		eigenschaftenView.getSbbTextField().setText(ppC.getTableComumnSonnebornBerger());
		eigenschaftenView.getResultTextField().setText(ppC.getTableComumnResult());
		eigenschaftenView.getRoundTextField().setText(ppC.getTableComumnRound());
		eigenschaftenView.getForenameLengthBox().setValue(ppC.getCutForename());
		eigenschaftenView.getSurnameLengthBox().setValue(ppC.getCutSurname());

	}

}
