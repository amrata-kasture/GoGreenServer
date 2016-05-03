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
import database.DbOperationsShare;
import model.User;

/**
 * Servlet implementation class ShareServlet
 */
@WebServlet("/ShareServlet")
public class ShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShareServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
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
          int usr = json.getInt("userId");
          int post = json.getInt("postId");
          
          System.out.println("User ID:"+usr + "post" + post);
          
          String filename1 = getServletContext().getRealPath("/DBConfig.properties");
          String filename2 = getServletContext().getRealPath("/DBSetUp.dat");
          
          
          DbOperationsShare dbOpShare = new DbOperationsShare(filename1, filename2);
          int done = dbOpShare.AddShare(usr, post);
          
          ObjectMapper mapper = new ObjectMapper();
          String jsonshare = mapper.writeValueAsString(done);
          System.out.println(jsonshare);
          OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
          JSONObject sendshare = new JSONObject();
          sendshare.put("Done", jsonshare);
      
          writer.write(sendshare.toString());
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
