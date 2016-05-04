package servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import database.DbOperationsFollowing;
import database.DbOperationsUser;
import model.Following;
import model.User;

/**
 * Servlet implementation class FollowingServlet
 */
@WebServlet("/FollowingServlet")
public class FollowingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 try {
	          ArrayList<User> follow;
	          String user = request.getParameter("userId");
	          String operation = request.getParameter("opId");
	          int usr = Integer.parseInt(user);
	          int op = Integer.parseInt(operation);
	          
	          System.out.println("User ID:"+usr + "OP" + op);
	          
	          String filename1 = getServletContext().getRealPath("/DBConfig.properties");
	          String filename2 = getServletContext().getRealPath("/DBSetUp.dat");
	          
	          
	          DbOperationsFollowing dbOpFollow = new DbOperationsFollowing(filename1, filename2);
	          if(op==1){
	        	 follow = dbOpFollow.getFollowing(usr);
	          }
	          else{
	        	  follow = dbOpFollow.getFollowers(usr);
	          }
	          
	          ObjectMapper mapper = new ObjectMapper();
	          String jsonfollowing = mapper.writeValueAsString(follow);
	          System.out.println(jsonfollowing);
	          OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
	          
	      
	          writer.write(jsonfollowing);
	          writer.flush();
	          writer.close();
	        } catch (IOException e) {
	          try{
	        	  response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	              response.getWriter().print(e.getMessage());
	              response.getWriter().close();
	            } catch (IOException ioe) {

	            }
	        }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 ObjectMapper mapper = new ObjectMapper();
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
          Following f = mapper.readValue(jsonString, Following.class);
          int usr = f.getUserId();
          int follow = f.getFollowId();
          String stat = f.getStatus();
          boolean op = f.isFollowing();
          
          System.out.println(usr+ " "+ follow+ " "+op);
          
          String filename1 = getServletContext().getRealPath("/DBConfig.properties");
          String filename2 = getServletContext().getRealPath("/DBSetUp.dat");
          
          DbOperationsFollowing dbOpFollowers = new DbOperationsFollowing(filename1, filename2);
          int done;
          if(op){
        	  done = dbOpFollowers.AddFollowing(follow, usr); 
          }
          else{
        	  done = dbOpFollowers.DeleteFollowing(follow, usr);
          }
          
          
          if(done==0){
        	  f.setStatusFollowFailed();
          }
          else{
        	  f.setStatus(stat);
          }
          
          System.out.println(done);
          
          String jsonStr = mapper.writeValueAsString(f);
          System.out.println(jsonStr);
          OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
      
          writer.write(jsonStr);
          writer.flush();
          writer.close();
        } catch (IOException e) {
          try{
        	  response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
              response.getWriter().print(e.getMessage());
              response.getWriter().close();
            } catch (IOException ioe) {

            }
        }
	
	}

}
