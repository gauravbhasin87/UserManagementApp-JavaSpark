//import junit.framework.TestCase;
import spark.utils.IOUtils;
import userapp.User;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.easymock.EasyMock.eq;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class Tests {
	
	@Test
	public void aNewUserShouldBeCreated() throws JsonParseException, JsonMappingException, IOException {
	
		String json = "{\"id\" : 10, \"firstName\" : \"gaurav2\", \"middleName\" : \" \", \"lastName\" : \"bhasin\", \"age\" : 2, \"gender\" : \"Male\", \"phone\" : \"3157445233\", \"zip\" : \"13210\" }";
		TestResponse res = request("POST", "/adduser", json);
		ObjectMapper mapper = new ObjectMapper();
		User user = null;
		if(res.body !="")
			user = mapper.readValue(res.body,User.class);
		assertNotNull(res.body);
		assertEquals(200, res.status);
		assertEquals("gaurav2", user.getFirstName() );
		assertEquals("bhasin", user.getLastName());
		assertNotNull(user.getId());
	}
	
	@Test
	public void userDeleted() throws JsonParseException, JsonMappingException, IOException{
		String json = "{\"id\" : 2000, \"firstName\" : \"gaurav2\", \"middleName\" : \" \", \"lastName\" : \"bhasin\", \"age\" : 2, \"gender\" : \"Male\", \"phone\" : \"3157445233\", \"zip\" : \"13210\" }";
		TestResponse res = request("POST","/adduser",json);
		assertEquals(200,res.status);
		res = request("DELETE","/delete/2000","");
		assertEquals(200,res.status);
	}
	
	@Test
	public void invalidUSerNotCreated() throws JsonParseException, JsonMappingException, IOException {
		String json = "{\"id\" : 12, \"firstName\" : , \"middleName\" : \" \", \"lastName\" : \"bhasin\", \"age\" : 2, \"gender\" : \"Male\", \"phone\" : \"3157445233\", \"zip\" : \"13210\" }";
		TestResponse res = request("POST", "/adduser", json);
		assertEquals(400, res.status);	
	}
	
	private TestResponse request(String method, String path, String json) throws IOException {
			
			URL url = new URL("http://localhost:4567" + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			//connection.setRequestProperty("Content-Type", "application/json");
			
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(json);
			out.close();
			connection.connect();
			int code = connection.getResponseCode();
			String body = "";
			if(code==200){
				body = IOUtils.toString(connection.getInputStream());
			}
			return new TestResponse(code,body);
				
	}

	private static class TestResponse {

		public final String body;
		public final int status;

		public TestResponse(int status, String body) {
			this.status = status;
			this.body = body;
		}

	
	}
	

}






/*HttpClient httpClient =   HttpClientBuilder.create().build();

try {
    HttpPost request = new HttpPost("http://localhost:4567" + path);
    StringEntity params =new StringEntity(json);
    System.out.println(json);
    request.addHeader("content-type", "application/json");
    request.addHeader("Accept","application/json");
    request.setEntity(params);
    HttpResponse response = httpClient.execute(request);
    HttpEntity entity = response.getEntity();
    String responseString = EntityUtils.toString(entity, "UTF-8");
    System.out.println(responseString);
   // response.
    return new TestResponse(200, responseString);

    // handle response here...
}catch (Exception ex) {
    // handle exception here
	ex.printStackTrace();
	fail("Sending request failed: " + ex.getMessage());
	return null;
} finally {
    httpClient.getConnectionManager().shutdown();
}
*/


//BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//String body = "";
//while (in.readLine() != null) {
	//body += in.readLine();
//}


