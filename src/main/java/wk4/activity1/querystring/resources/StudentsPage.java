package wk4.activity1.querystring.resources;

import wk4.activity1.querystring.Core.StudentRecords;
import wk4.activity1.querystring.logger.ServiceLogger;
import wk4.activity1.querystring.models.SearchStudentRequestModel;
import wk4.activity1.querystring.models.SearchStudentResponseModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

@Path("student")
public class StudentsPage {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentRequest(@Context HttpHeaders headers, @QueryParam("id") int id,
                                      @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName,
                                      @QueryParam("standing") String standing) {
        ServiceLogger.LOGGER.info("Received request for student records.");
        ServiceLogger.LOGGER.info("id: " + id);
        ServiceLogger.LOGGER.info("firstName: " + firstName);
        ServiceLogger.LOGGER.info("lastName: " + lastName);
        ServiceLogger.LOGGER.info("standing: " + standing);

        // Get the email and sessionID from the HTTP header
        String email = headers.getHeaderString("email");
        String sessionID = headers.getHeaderString("sessionID");
        ServiceLogger.LOGGER.info("EMAIL: " +  email);
        ServiceLogger.LOGGER.info("SESSIONID: " + sessionID);

        // Build a JSON node from the query parameters. You'll have to figure this out.

        // Map the new JSON node into a RequestModel. You already know how to do this.

        // Do your error checking here

        // For example purposes, we're going to create an empty RequestModel and then set the fields manually. This is
        // the WRONG WAY to do this. DO NOT DO THIS ON YOUR HOMEWORK. THIS IS ONLY FOR DEMONSTRATION PURPOSES
        SearchStudentRequestModel requestModel = new SearchStudentRequestModel();
        requestModel.setEmail(email);
        requestModel.setSessionID(sessionID);
        requestModel.setId(id);
        requestModel.setFirstName(firstName);
        requestModel.setLastName(lastName);
        requestModel.setSessionID(standing);

        // Pass the request model off to the logical handler in StudentRecords.java
        SearchStudentResponseModel responseModel = StudentRecords.searchForStudents(requestModel);

        // Do your error checking here

        // Return an HTTP Response
        return Response.status(Status.OK).entity(responseModel).build();
    }
}
