package de.turnierverwaltung.model;

public class ELOPlayer {

	private ELOData eloData;

	public ELOPlayer(String fideid, String name, String country, String sex, String title, String w_title,
			String o_title, String foa_title, String rating, String games, String k, String birthday, String flag) {
		super();
		eloData = new ELOData();
		eloData.setFideid(fideid);
		eloData.setName(name);
		eloData.setCountry(country);
		eloData.setSex(sex);
		eloData.setTitle(title);
		eloData.setW_title(w_title);
		eloData.setO_title(o_title);
		eloData.setFoa_title(foa_title);
		eloData.setRating(rating);
		eloData.setGames(games);
		eloData.setK(k);
		eloData.setBirthday(birthday);
		eloData.setFlag(flag);
	}

	public Player getPlayer() {

		Player player = new Player(eloData);
		return player;
	}

	public ELOData getEloData() {
		return eloData;
	}

	public void setEloData(ELOData eloData) {
		this.eloData = eloData;
	}

}
