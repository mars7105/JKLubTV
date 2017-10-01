package de.turnierverwaltung.model;

public class ELOPlayer {
	private String fideid;
	private String name;
	private String country;
	private String sex;
	private String title;
	private String w_title;
	private String o_title;
	private String foa_title;
	private String rating;
	private String games;
	private String k;
	private String birthday;
	private String flag;

	public ELOPlayer(String fideid, String name, String country, String sex, String title, String w_title,
			String o_title, String foa_title, String rating, String games, String k, String birthday, String flag) {
		super();
		this.fideid = fideid;
		this.name = name;
		this.country = country;
		this.sex = sex;
		this.title = title;
		this.w_title = w_title;
		this.o_title = o_title;
		this.foa_title = foa_title;
		this.rating = rating;
		this.games = games;
		this.k = k;
		this.birthday = birthday;
		this.flag = flag;
	}

	public Player getPlayer() {
		

		Player player = new Player(name, fideid, country, sex, title, w_title, o_title, foa_title, rating, games, k,
				birthday, flag);
		
		return player;
	}

	public String getFideid() {
		return fideid;
	}

	public void setFideid(String fideid) {
		this.fideid = fideid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getW_title() {
		return w_title;
	}

	public void setW_title(String w_title) {
		this.w_title = w_title;
	}

	public String getO_title() {
		return o_title;
	}

	public void setO_title(String o_title) {
		this.o_title = o_title;
	}

	public String getFoa_title() {
		return foa_title;
	}

	public void setFoa_title(String foa_title) {
		this.foa_title = foa_title;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getGames() {
		return games;
	}

	public void setGames(String games) {
		this.games = games;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
