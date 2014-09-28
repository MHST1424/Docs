package vn.mhst24.entity;

public class User {
	private String id;
	private String user_name;
	private String password;
	private String name;
	private String birthday;
	private boolean gender;
	private String email;
	private String cnmd;
	private String number_cmnd;
	private boolean validate;
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}


	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}


	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	/**
	 * @return the gender
	 */
	public boolean isGender() {
		return gender;
	}


	/**
	 * @param gender the gender to set
	 */
	public void setGender(boolean gender) {
		this.gender = gender;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the cnmd
	 */
	public String getCnmd() {
		return cnmd;
	}


	/**
	 * @param cnmd the cnmd to set
	 */
	public void setCnmd(String cnmd) {
		this.cnmd = cnmd;
	}


	/**
	 * @return the number_cmnd
	 */
	public String getNumber_cmnd() {
		return number_cmnd;
	}


	/**
	 * @param number_cmnd the number_cmnd to set
	 */
	public void setNumber_cmnd(String number_cmnd) {
		this.number_cmnd = number_cmnd;
	}


	/**
	 * @return the validate
	 */
	public boolean isValidate() {
		return validate;
	}


	/**
	 * @param validate the validate to set
	 */
	public void setValidate(boolean validate) {
		this.validate = validate;
	}


	public User(String id, String user_name, String password, String name,
			String birthday, boolean gender, String email, String cnmd,
			String number_cmnd, boolean validate) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
		this.cnmd = cnmd;
		this.number_cmnd = number_cmnd;
		this.validate = validate;
	}


	public User() {
		super();
	}
	
	
}
