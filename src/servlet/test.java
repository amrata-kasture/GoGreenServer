package servlet;

import java.io.OutputStreamWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import model.User;
 
@WebServlet("/test")
public class test extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public test() {
        super();
 
    }
 
   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	// TODO Auto-generated method stub

    	response.getOutputStream().println("Hurray !! This Servlet Works");

    	}
 
    /*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        try {
            int length = request.getContentLength();
            byte[] input = new byte[length];
            ServletInputStream sin = request.getInputStream();
            int c, count = 0 ;
            while ((c = sin.read(input, count, input.length-count)) != -1) {
                count +=c;
            }
            sin.close();
 
            String recievedString = new String(input);
            response.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
 
            Integer doubledValue = Integer.parseInt(recievedString) * 2;
 
            writer.write(doubledValue.toString());
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
        }*/
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	// TODO Auto-generated method stub

    	try {

    	            int length = request.getContentLength();

    	            byte[] input = new byte[length];

    	            ServletInputStream sin = request.getInputStream();

    	            int c, count = 0 ;

//    	            String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";

    	            String jsonString = "";

    	            while ((c = sin.read(input, count, input.length-count)) != -1) {

    	              count +=c;

    	          }

    	          sin.close();

    	          jsonString = new String(input);

    	            System.out.println(jsonString);

    	            ObjectMapper mapper = new ObjectMapper();

    	          //Object to JSON Conversion

    	            Student student = mapper.readValue(jsonString, Student.class);
    	            System.out.println("111111111"+student.getFirstName());
    	          //Object to JSON Conversion

    	            //jsonString = mapper.writeValueAsString(student);
    	            jsonString = student.toString();

    	            System.out.println("222222"+jsonString);

//    	            

//    	            

//    	            System.out.println(jsonString);

//    	           

//    	          //Object to JSON Conversion

////    	            recievedString = mapper.writeValueAsString(student);

//    	            

//    	            

//    	            response.setStatus(HttpServletResponse.SC_OK);

//    	            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

//    	            Integer doubledValue = Integer.parseInt(recievedString) * 2;
    	            System.out.println("ARRIVING");
    	            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
    	            System.out.println("JUST THERE");
    	            writer.write(jsonString);
    	            System.out.println("I AM OUT");
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

