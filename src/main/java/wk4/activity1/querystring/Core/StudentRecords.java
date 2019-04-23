package wk4.activity1.querystring.Core;

import org.glassfish.jersey.jackson.JacksonFeature;
import wk4.activity1.querystring.ExampleService;
import wk4.activity1.querystring.logger.ServiceLogger;
import wk4.activity1.querystring.models.SearchStudentRequestModel;
import wk4.activity1.querystring.models.SearchStudentResponseModel;
import wk4.activity1.querystring.models.VerifyPrivilegeRequestModel;
import wk4.activity1.querystring.models.VerifyPrivilegeResponseModel;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.Service;

public class StudentRecords {
    public static SearchStudentResponseModel searchForStudents(SearchStudentRequestModel requestModel) {
        ServiceLogger.LOGGER.info("Searching for students...");

        // Verify that the requester has the correct privilege level to make the request
        if (!isUserAllowedToMakeRequest(requestModel.getEmail())) {
            return new SearchStudentResponseModel(2, "Unauthorized");
        } else {
            // make the query, build the ResponseModel, and return it
            return new SearchStudentResponseModel(1, "Success");
        }
    }

    private static boolean isUserAllowedToMakeRequest(String email) {
        ServiceLogger.LOGGER.info("Verifying privilege level with IDM...");

        // Create a new Client
        ServiceLogger.LOGGER.info("Building client...");
        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);

        // Get the URI for the IDM
        ServiceLogger.LOGGER.info("Building URI...");
        String IDM_URI = ExampleService.getExampleConfigs().getIdmConfigs().getIdmUri();

        ServiceLogger.LOGGER.info("Setting path to endpoint...");
        String IDM_ENDPOINT_PATH = ExampleService.getExampleConfigs().getIdmConfigs().getPrivilegePath();

        // Create a WebTarget to send a request at
        ServiceLogger.LOGGER.info("Building WebTarget...");
        WebTarget webTarget = client.target(IDM_URI).path(IDM_ENDPOINT_PATH);

        // Create an InvocationBuilder to create the HTTP request
        ServiceLogger.LOGGER.info("Starting invocation builder...");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        // Set the payload
        ServiceLogger.LOGGER.info("Setting payload of the request");
        VerifyPrivilegeRequestModel requestModel = new VerifyPrivilegeRequestModel(email, 3);

        // Send the request and save it to a Response
        ServiceLogger.LOGGER.info("Sending request...");
        Response response = invocationBuilder.post(Entity.entity(requestModel, MediaType.APPLICATION_JSON));
        ServiceLogger.LOGGER.info("Sent!");

        // Check that status code of the request
        if (response.getStatus() == 200) {
            ServiceLogger.LOGGER.info("Received Status 200");
            // Success! Map the response to a ResponseModel
//            VerifyPrivilegeResponseModel responseModel = response.readEntity(VerifyPrivilegeResponseModel.class);
            String jsonText = response.readEntity(String.class);
            ServiceLogger.LOGGER.info("JsonText: " + jsonText);
            /*
            if (responseModel.getResultCode() == 140) {
                return true;
            }
             */
        } else {
            ServiceLogger.LOGGER.info("Received Status " + response.getStatus() + " -- you lose.");
        }
        return false;
    }
}
