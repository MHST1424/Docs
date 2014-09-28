package vn.mhst24.entity;

public class CarPool {
	private int id;
	private String user;
	private String avata;
	private String lat_long;
	private String lcstart;
	private String lcfinish;
	private String timestart;
	private String timeback;
	private boolean owner;
	private String typecar;
	private int numseat;
	private float cost;
	private String note;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the avata
	 */
	public String getAvata() {
		return avata;
	}
	/**
	 * @param avata the avata to set
	 */
	public void setAvata(String avata) {
		this.avata = avata;
	}
	/**
	 * @return the lat_long
	 */
	public String getLat_long() {
		return lat_long;
	}
	/**
	 * @param lat_long the lat_long to set
	 */
	public void setLat_long(String lat_long) {
		this.lat_long = lat_long;
	}
	/**
	 * @return the lcstart
	 */
	public String getLcstart() {
		return lcstart;
	}
	/**
	 * @param lcstart the lcstart to set
	 */
	public void setLcstart(String lcstart) {
		this.lcstart = lcstart;
	}
	/**
	 * @return the lcfinish
	 */
	public String getLcfinish() {
		return lcfinish;
	}
	/**
	 * @param lcfinish the lcfinish to set
	 */
	public void setLcfinish(String lcfinish) {
		this.lcfinish = lcfinish;
	}
	/**
	 * @return the timestart
	 */
	public String getTimestart() {
		return timestart;
	}
	/**
	 * @param timestart the timestart to set
	 */
	public void setTimestart(String timestart) {
		this.timestart = timestart;
	}
	/**
	 * @return the timeback
	 */
	public String getTimeback() {
		return timeback;
	}
	/**
	 * @param timeback the timeback to set
	 */
	public void setTimeback(String timeback) {
		this.timeback = timeback;
	}
	/**
	 * @return the owner
	 */
	public boolean isOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(boolean owner) {
		this.owner = owner;
	}
	/**
	 * @return the typecar
	 */
	public String getTypecar() {
		return typecar;
	}
	/**
	 * @param typecar the typecar to set
	 */
	public void setTypecar(String typecar) {
		this.typecar = typecar;
	}
	/**
	 * @return the numseat
	 */
	public int getNumseat() {
		return numseat;
	}
	/**
	 * @param numseat the numseat to set
	 */
	public void setNumseat(int numseat) {
		this.numseat = numseat;
	}
	/**
	 * @return the cost
	 */
	public float getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(float cost) {
		this.cost = cost;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	public CarPool(int id, String user, String avata, String lat_long,
			String lcstart, String lcfinish, String timestart, String timeback,
			boolean owner, String typecar, int numseat, float cost, String note) {
		super();
		this.id = id;
		this.user = user;
		this.avata = avata;
		this.lat_long = lat_long;
		this.lcstart = lcstart;
		this.lcfinish = lcfinish;
		this.timestart = timestart;
		this.timeback = timeback;
		this.owner = owner;
		this.typecar = typecar;
		this.numseat = numseat;
		this.cost = cost;
		this.note = note;
	}
	public CarPool() {
		super();
	}	
	
	
}
