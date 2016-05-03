package servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import database.DbOperationsUser;
import model.User;

/**
 * Servlet implementation class UserAuthentication
 */
@WebServlet("/UserAuthServlet")
public class UserAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAuthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getOutputStream().println("Hurray !! User-Auth-Servlet Works");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
        try {
            int length = request.getContentLength();
            byte[] input = new byte[length];
            ServletInputStream sin = request.getInputStream();
            int c, count = 0 ;
            String jsonString = "";
            while ((c = sin.read(input, count, input.length-count)) != -1) {
              count +=c;
           }
          sin.close();
          jsonString = new String(input);
          System.out.println(jsonString);
          JSONObject json = new JSONObject(jsonString);
          
          //ObjectMapper mapper = new ObjectMapper();
          //User uTemp = mapper.readValue(jsonString, User.class);
          String uName = json.getString("username");
          String pwd = json.getString("password");
          
          String filename1 = getServletContext().getRealPath("/DBConfig.properties");
          String filename2 = getServletContext().getRealPath("/DBSetUp.dat");
          System.out.println("*************"+filename1);
          System.out.println("*************"+filename2);
          
          DbOperationsUser dbOpUser = new DbOperationsUser(filename1, filename2);
          int id = dbOpUser.GetUserIdByUsername(uName);
          System.out.println("#########"+id);
          User resultUser = dbOpUser.GetUserDetailsFromUserId(id);
          
          JSONObject jsonReturn = new JSONObject();

          if(pwd.trim().equals(resultUser.getPassword().trim())){
              jsonReturn.put("userId", resultUser.getUserId());
          }else{
        	  jsonReturn.put("status", "Authentication Failure");
          }

          OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
          writer.write(jsonReturn.toString());
          writer.flush();
          writer.close();
        } catch (IOException | JSONException e) {
          try{
        	  response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
              response.getWriter().print(e.getMessage());
              response.getWriter().close();
            } catch (IOException ioe) {

            }
        }
        
		
	}

}
