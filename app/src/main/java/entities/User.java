package entities;

import java.util.Date;

public class User {

    // Declare that this attribute is the id

    private Long id;
    
    private String username;
    private String pass; 
    private String fullName;
    private Date birthday;

    private int height;
    private int weight;
    private int BMI;

    public User() { }
    
    public User(String username, String pass, String fullName, Date birthday, int height, int weight) {
        this.username = username;
    	this.pass = pass;
        this.fullName = fullName;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;

    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getBMI() {
		return BMI;
	}

	public void setBMI(int BMI) {
		this.BMI = BMI;
	}

}
