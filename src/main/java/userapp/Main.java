
package userapp;

import static spark.Spark.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;



public class Main {
	
	
	private static final int HTTP_BAD_REQUEST = 400;
	
	
	public static Object jsonToData(String body){
		try{
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.readValue(body, User.class);
			return user;
		}
		catch (JsonParseException jpe) {
            return createError("400","Error in creating user");
        }
		catch (NullPointerException jpe) {
            return createError("400","Error in JSON");
        }
		catch (Exception jpe) {
            return createError(Integer.toString(HTTP_BAD_REQUEST),"Error :" + jpe.getMessage());
        }
		
		
	}
	
	
	
/***************************************************************************************************
*Convert list of serializable object of any type to JSON format
*@param Object
*@return String in json format
****************************************************************************************************/
	public static String dataToJson(Object data) {
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.enable(SerializationFeature.INDENT_OUTPUT);
	            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
	            StringWriter sw = new StringWriter();   //convert collection object to json array
	            writer.writeValue(sw, data);
	            return sw.toString();
	        } catch (IOException e){
	            throw new RuntimeException("IOException from a StringWriter?");
	        }
	    }
/***************************************************************************************************
*Convert serializable object of any type to json format
*@param Object
*@return String in json format
****************************************************************************************************/	
	public static String dataToJson(ResponseObject data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            //for pretty printing JSON
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            return mapper.writeValueAsString(data);
        } catch (IOException e){
            return createError("404","Jackson: Error converting object into json format");
        }
    }
	
/***************************************************************************************************
*Create erro in json format
*@param error code
*@param error message
*@return Error String in json format
****************************************************************************************************/	
public static String createError(String errorCode, String error){
		ResponseObject obj = new ResponseObject();
		obj.errorCode = errorCode;
		obj.errorData = error;
		return dataToJson(obj);
	}

/***************************************************************************************************
* Main method
*@param array command line argument 
*@return void
****************************************************************************************************/
	
	public static void main(String[] args) {
		
/***************************************************************************************************
 *  Start data store service
 * UserRepository class maintains hash map of Users and expose 
 * methods to manipulate the data structure
 ***************************************************************************************************/
		UserRepository userTable = new UserRepository();
		
/***************************************************************************************************
 * Route for fetching all the users in json format
 * @return list of users in the database in json array format
 * 		   or error in json format if there are no users 
 * 		   in the database 	
 ***************************************************************************************************/
		get("/users",(request, response)->{
								if(userTable.getUserCount()==0){
									response.status(404);
									response.type("application/json");
									return createError("404","No User Found");
									
								}
								else{
									response.type("application/json");
									return dataToJson(userTable.getUserMap().values());
								}
		});
		
/***************************************************************************************************
 * Route for fetching a user with a particular id
 * @return details of users in json format
 * 			or error in json format if user is not found with a given id
 **************************************************************************************************/
		get("/user/:id",(request,response)->{
						int id = Integer.parseInt(request.params(":id"));
						User user = userTable.getUserMap().get(id);
						response.type("application/json");
						if(user!=null){
							return dataToJson(user);
						}
						else{
							return createError("400","User not found");
						}
		});
		
/****************************************************************************************
 * Route for adding user in the database
 * Details of user are passed in json format, via body of the post request.		
 * @return details of the user in json format
 ***************************************************************************************/
		post("/adduser",(request,response)->{
				try{
					User user = (User)jsonToData(request.body());
					if(user.isValid1()){
						int id = Integer.parseInt(user.getId());
						System.out.println("Id entered in JSON : "+id);
						if(userTable.getUserMap().containsKey(id)){     //check for existing id
							response.status(400);
							return createError("400","User already Exists");
						}
						else{
							userTable.addUser(user);
							response.status(200);
			                response.type("application/json");
			                return dataToJson(user);    //newUser.getId();
						}
					}
					else{
						response.status(400);
						response.type("application/json");
						return createError("400",user.error);
					}
						
				}
				catch (Exception jpe) {
	                response.status(HTTP_BAD_REQUEST);
	                return createError(Integer.toString(HTTP_BAD_REQUEST),"Error :" + jpe.getMessage());
	            }
			
		});

/****************************************************************************************
 *Route for updating the details of user with the given id
 *@return updated state of user in json format 		
 ***************************************************************************************/
		put("/updateuser/:id",(request,response)->{
			
				try{
					int id = Integer.parseInt(request.params(":id"));
					if(userTable.getUserMap().containsKey(id)){
						ObjectMapper mapper = new ObjectMapper();
						User userFromJson = (User)jsonToData(request.body());
						if(userFromJson.isValid1()){
							User userFromMap = userTable.getUserMap().get(id);
							userFromMap.setFirstName(userFromJson.getFirstName());
							userFromMap.setMiddleName(userFromJson.getMiddleName());
							userFromMap.setLastName(userFromJson.getLastName());
							userFromMap.setAge(userFromJson.getAge());
							userFromMap.setGender(userFromJson.getGender());
							userFromMap.setPhone(userFromJson.getPhone());
							userFromMap.setZip(userFromJson.getZip());
							return dataToJson(userFromMap);
						}
						else{
							response.status(404);
							return createError("404",userFromJson.error);
						}
					}
					else{
						response.status(404);
						return createError("404", "User with ID "+id+" Not Found");
					}
				}
				catch (Exception jpe) {
	                response.status(HTTP_BAD_REQUEST);
	                return createError("400","Error :" + jpe.getMessage());
	            }
		});
		
		
		//delete a user
		
		delete("/delete/:id",(request,response)->{
					try{
						int id = Integer.parseInt(request.params("id"));
						if(userTable.getUserMap().containsKey(id)){
							userTable.getUserMap().remove(id);
							response.status(200);
							return "Deleted";
						}
						else{
							response.status(HTTP_BAD_REQUEST);
							response.type("application/json");
							return createError("400","No delete");
						}
					}
					catch (Exception jpe) {
		                response.status(HTTP_BAD_REQUEST);
		                return "Error :" + jpe.getMessage();
		            }
		});
	}

}
