package userapp;

public class User {
	
	private static int count=0;
	//user fields
	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private int age;
	private String gender;
	private String phone;
	private String zip;
	
	public String error="";
	
	//constructors
	public User(){
		count++;
		System.out.println("default "+count);
	}
	
	public User(String first, String middle, String last,int age,String gender,String phone,String zip){
		//count++;
		System.out.println("user "+count);
		this.id = ""+count;
		firstName = first;
		middleName = middle;
		lastName = last;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.zip = zip;
				
	}
	
	//getter and setter for id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	//getter and setter for firstName
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	//getter and setter for middleName
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	//getter and setter for lastName
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	//getter and setter for age
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	//getter and setter for gender
	public String getGender() {
		return gender;
	}
	public void setGender(String genger) {
		this.gender = genger;
	}
	//getter and setter for phone
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	//getter and setter for zip
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
/********************************************************************
 * Method to check the validity of state of User	 
 * @return true or false depending whether the user data is valid
 ********************************************************************/
	boolean isValid1(){
		if(id.isEmpty()){
			error = "ID is empty";
			return false;
		}
		else if(firstName.isEmpty()){
			error = "First Name is empty";
			return false;
		}
		else if(lastName.isEmpty()){
			error = "Last Name is empty";
			return false;
		}
		else if(gender.isEmpty()){
			error = "Gender is empty";
			return false;
		}
		else if(phone.length()!=10){
			error = "Wrong Phone number";
			return false;
		}
		else if(age<=0){
			error = "Age is wrong!!";
			return false;
		}
		
		return true;
	}

}
