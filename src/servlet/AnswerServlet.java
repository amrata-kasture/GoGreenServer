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
import database.DbOperationsGreenEntry;
import database.DbOperationsQARelation;
import model.GreenEntry;
import model.User;

/**
 * Servlet implementation class AnswerServlet
 */
@WebServlet("/AnswerServlet")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	          ArrayList<GreenEntry> answers;
	          String question = request.getParameter("qId");
	          int qId = Integer.parseInt(question);
	          
	          String filename1 = getServletContext().getRealPath("/DBConfig.properties");
	          String filename2 = getServletContext().getRealPath("/DBSetUp.dat");
	          
	          DbOperationsGreenEntry dbOpAnswers = new DbOperationsGreenEntry(filename1, filename2);
	          answers = dbOpAnswers.getAnswers(qId);
	          
	          
	          ObjectMapper mapper = new ObjectMapper();
	          String jsonanswers = mapper.writeValueAsString(answers);
	          System.out.println(jsonanswers);
	          OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
	          JSONObject sendanswers = new JSONObject();
	          sendanswers.put("Answers", jsonanswers);
	      
	          writer.write(sendanswers.toString());
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
          int usr = json.getInt("postedByUserId");
          String postType = json.getString("postType");
          String postMsg = json.getString("postMessage");
          String pic = json.getString("postImageURL");
          int qId = json.getInt("qId");
          
          GreenEntry ge = new GreenEntry(usr, postType, postMsg, pic);
          
          String filename1 = getServletContext().getRealPath("/DBConfig.properties");
          String filename2 = getServletContext().getRealPath("/DBSetUp.dat");
          
         DbOperationsGreenEntry dbOpGE = new DbOperationsGreenEntry(filename1, filename2);
         int ansPostId = dbOpGE.AddGreenEntry(ge);
         
         DbOperationsQARelation dbOpQA = new DbOperationsQARelation(filename1, filename2);
         int done = dbOpQA.AddQARelation(qId, ansPostId);
         
          ObjectMapper mapper = new ObjectMapper();
          String jsonStr = mapper.writeValueAsString(done);
          System.out.println(jsonStr);
          OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
          JSONObject sendRes = new JSONObject();
          sendRes.put("Done", jsonStr);
      
          writer.write(sendRes.toString());
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
