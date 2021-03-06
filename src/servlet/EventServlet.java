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

import database.DbOperationsEvent;
import model.Event;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/EventServlet")
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getOutputStream().println("Hurray !! EventServlet Works");
		JSONObject json = new JSONObject();
        try {

          ObjectMapper mapper = new ObjectMapper();
          String filename1 = getServletContext().getRealPath("/DBConfig.properties");
          String filename2 = getServletContext().getRealPath("/DBSetUp.dat");
          String eventId = request.getParameter("eventId");
          System.out.println("####"+eventId);
          DbOperationsEvent dbOpEvent = new DbOperationsEvent(filename1, filename2);
          Event resultEvent = dbOpEvent.GetEventDetailsFromEventId(Integer.parseInt(eventId));
          String outString = mapper.writeValueAsString(resultEvent); 
          System.out.println("####"+resultEvent.toString());
          OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
          writer.write(outString);
          writer.flush();
          writer.close();
          
          response.getOutputStream().println("Hurray !! EventServlet Works END");
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
	          Event e = mapper.readValue(jsonString, Event.class);
	          System.out.println("!!!!!!!!!!!!!"+e.toString());
	          
	          String filename1 = getServletContext().getRealPath("/DBConfig.properties");
	          String filename2 = getServletContext().getRealPath("/DBSetUp.dat");
	          System.out.println("*************"+filename1);
	          System.out.println("*************"+filename2);
	          
	          DbOperationsEvent dbOpEvent = new DbOperationsEvent(filename1, filename2);
	          int eventid = dbOpEvent.AddEvent(e);
	          e.setEventId(eventid);
	          System.out.println("*************"+eventid);
			  
	          OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
	          //JSONObject jsonReturn = new JSONObject();
	          //jsonReturn.put("eventId", eventid);
	          //writer.write(jsonReturn.toString());
	          String outString = mapper.writeValueAsString(e); 
	          writer.write(outString);
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
