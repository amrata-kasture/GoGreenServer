package servlet;

public class Student {

	private String firstName;
	private String lastName;
	private String roleType;
	private int userInterest;
	private String city;
	private String state;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public int getUserInterest() {
		return userInterest;
	}
	public void setUserInterest(int userInterest) {
		this.userInterest = userInterest;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Student [firstName=" + firstName + ", lastName=" + lastName + ", roleType=" + roleType
				+ ", userInterest=" + userInterest + ", city=" + city + ", state=" + state + "]";
	}
	
	

	  
	}

