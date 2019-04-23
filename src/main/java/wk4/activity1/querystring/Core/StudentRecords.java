package wk4.activity1.querystring.Core;

import wk4.activity1.querystring.ExampleService;
import wk4.activity1.querystring.logger.ServiceLogger;
import wk4.activity1.querystring.models.SearchStudentRequestModel;
import wk4.activity1.querystring.models.SearchStudentResponseModel;
import wk4.activity1.querystring.models.VerifyPrivilegeRequestModel;
import wk4.activity1.querystring.models.VerifyPrivilegeResponseModel;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        Client client = ClientBuilder.newClient();
        // Get the URI for the IDM
        String IDM_URI = ExampleService.getExampleConfigs().getIdmConfigs().getIdmUri();
        String IDM_ENDPOINT_PATH = ExampleService.getExampleConfigs().getIdmConfigs().getPrivilegePath();
        // Create a WebTarget to send a request at
        WebTarget webTarget = client.target(IDM_URI).path(IDM_ENDPOINT_PATH);
        // Create an InvocationBuilder to create the HTTP request
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        // Set the payload
        VerifyPrivilegeRequestModel requestModel = new VerifyPrivilegeRequestModel(email, 3);
        Response response = invocationBuilder.post(Entity.entity(requestModel, MediaType.APPLICATION_JSON));

        // Check that status code of the request
        if (response.getStatus() == 200) {
            // Success! Map the response to a ResponseModel
            VerifyPrivilegeResponseModel responseModel = response.readEntity(VerifyPrivilegeResponseModel.class);
            if (responseModel.getResultCode() == 140) {
                return true;
            }
        }
        return false;
    }
}
