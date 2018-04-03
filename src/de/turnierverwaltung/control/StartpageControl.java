package de.turnierverwaltung.control;

import de.turnierverwaltung.view.StartpageView;

public class StartpageControl {
	MainControl mainControl;
	StartpageView startpage;

	public StartpageControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		startpage = new StartpageView();
	}

	public StartpageView getStartpage() {
		return startpage;
	}

	public void setStartpage(StartpageView startpage) {
		this.startpage = startpage;
	}

}
