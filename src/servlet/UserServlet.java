package servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import database.DatabaseConnector;
import database.DbOperationsUser;
import model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getOutputStream().println("Hurray !! UserServlet Works");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

        JSONObject json = new JSONObject();
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
          ObjectMapper mapper = new ObjectMapper();
          User usr = mapper.readValue(jsonString, User.class);
          
          String filename1 = getServletContext().getRealPath("/DBConfig.properties");
          String filename2 = getServletContext().getRealPath("/DBSetUp.dat");
          System.out.println("*************"+filename1);
          System.out.println("*************"+filename2);
          
          DbOperationsUser dbOpUser = new DbOperationsUser(filename1, filename2);
          int userid = dbOpUser.AddUser(usr);
		  
          OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
          JSONObject jsonReturn = new JSONObject();
          jsonReturn.put("userId", userid);
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
