package servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import database.DbOperationsEvent;
import database.DbOperationsGreenEntry;
import model.GreenEntry;

/**
 * Servlet implementation class TimelineServlet
 */
@WebServlet("/TimelineServlet")
public class TimelineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimelineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getOutputStream().println("Hurray !! TimelineServlet Works");
		ArrayList<GreenEntry> arrGE = new ArrayList<GreenEntry>();
		String filename1 = getServletContext().getRealPath("/DBConfig.properties");
        String filename2 = getServletContext().getRealPath("/DBSetUp.dat");
        System.out.println("*************"+filename1);
        System.out.println("*************"+filename2);
        
        DbOperationsGreenEntry dbOpGE = new DbOperationsGreenEntry(filename1, filename2);
        arrGE= dbOpGE.ReadGreenEntry();
        System.out.println("*************"+arrGE.toString());
		  
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
        //JSONObject jsonReturn = new JSONObject();
        //jsonReturn.put("eventId", eventid);
        //writer.write(jsonReturn.toString());
        ObjectMapper mapper = new ObjectMapper();
        String outString = mapper.writeValueAsString(arrGE); 
        writer.write(outString);
        writer.flush();
        writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
