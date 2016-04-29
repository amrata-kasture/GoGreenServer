package resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONObject;

import model.User;

import javax.ws.rs.core.Request;


 
@Path("/user")
public class UserResource {
 
    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static String USERNAME = "uname";
         
    private User person = new User("user1", "uFName", "uLName", "PlantLover",2, "Sunnyvale","California", "http://engineering.unl.edu/images/staff/Kayla_Person-small.jpg");
     
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;
 
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;
     
    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return "Demo service is ready!";
    }
 
    @GET
    @Path("sample")
    @Produces(MediaType.APPLICATION_JSON)
    public User getSampleUser() {
         
        System.out.println("Returning sample person: " + person.getFirstName() + " " + person.getLastName());
        return person;
    }
    
    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    public User trigger(JSONObject notify) throws Exception{            
        //return Response.status(Response.Status.OK).entity("134124").tag("213q").type(MediaType.APPLICATION_JSON).build();   
    	return person;
    }

         
    // Use data from the client source to create a new Person object, returned in JSON format.  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public User postUser(
            MultivaluedMap<String, String> personParams
            ) {
         
        String firstName = personParams.getFirst(FIRST_NAME);
        String lastName = personParams.getFirst(LAST_NAME);
        String uname = personParams.getFirst(USERNAME);
         
        System.out.println("Storing posted " + firstName + " " + lastName + "  " + uname);
         
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setUsername(uname);
         
        System.out.println("person info: " + person.getFirstName() + " " + person.getLastName() + " " + person.getUsername());
         
        return person;
                         
    }
}