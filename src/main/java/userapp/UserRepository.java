package userapp;
import java.util.*;

public class UserRepository {
	
	private HashMap<Integer,User> userMap;
	private int userCount;
	
	public UserRepository(){
		setUserMap(new HashMap<Integer,User>());
		setUserCount(0);
	}
	
	
	//getter and setters
	public HashMap<Integer,User> getUserMap() {
		return userMap;
	}

	public void setUserMap(HashMap<Integer,User> userMap) {
		this.userMap = userMap;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	
	//add user in map
	public void addUser(User user){
		userMap.put(Integer.parseInt(user.getId()), user);
		userCount++;
		
	}
	
	//print all users
	public String printAllUsers(HashMap<Integer,User> map){
		String userDetails = "";

		Set<Map.Entry<Integer,User>> set = map.entrySet();
		Iterator<Map.Entry<Integer,User>> it = set.iterator();
		while(it.hasNext()){
			Map.Entry<Integer, User> entry = it.next();
			User user = (User)entry.getValue();
			userDetails += String.format("<br>%-8s%-20s%-15s%-20s%-4d%-10s%-15s%-7s\n",user.getId(),user.getFirstName(),user.getMiddleName(),
					user.getLastName(),user.getAge(),user.getGender(),
					user.getPhone(),user.getZip());
		}
		
		return userDetails;
	}

}


//userDetails += String.format("%-8s%-20s%-15s%-20s%-4s%-10s%-15s%-7s\n","ID","First Name","Middle Name",
//"Last Name","Age","Gender",
//"Phone","Zip"); 
