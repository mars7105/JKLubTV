package de.turnierverwaltung.view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class NewTournamentAllView extends JPanel {
	private NewTournamentGroupsView tGroupsView;
	private NewTournamentPlayerInputView tPlayerInputView;

	public NewTournamentAllView() {
		tGroupsView = new NewTournamentGroupsView();
		tGroupsView.runView(1);
		int spielerAnzahl = 1;
		tPlayerInputView = new NewTournamentPlayerInputView(spielerAnzahl);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(tGroupsView);
		add(tPlayerInputView);

	}

	public NewTournamentGroupsView gettGroupsView() {
		return tGroupsView;
	}

	public void settGroupsView(NewTournamentGroupsView tGroupsView) {
		this.tGroupsView = tGroupsView;
	}

	public NewTournamentPlayerInputView gettPlayerInputView() {
		return tPlayerInputView;
	}

	public void settPlayerInputView(NewTournamentPlayerInputView tPlayerInputView) {
		this.tPlayerInputView = tPlayerInputView;
	}

}
