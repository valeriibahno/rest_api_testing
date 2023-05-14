package validator;

import io.restassured.response.Response;
import org.testng.Assert;
import utils.ConvertResponseToModel;

import java.util.List;

public class ResponseValidator {

    private final Response response;

    private static final ConvertResponseToModel parser = new ConvertResponseToModel();

    public ResponseValidator(Response response) {
        this.response = response;
    }

    public ResponseValidator verifyStatusCode(int expectedCode) {
        int actualCode = this.response.statusCode();
        Assert.assertEquals(actualCode, expectedCode, String.format("Status code is: '%s'.\nExpected: %s", actualCode, expectedCode));
        return this;
    }

    public ResponseValidator verifyListByParameterExists(String parameter, List<String> expectedList) {
        List<String> actualList = response.body().jsonPath().getList(parameter);
        Assert.assertEquals(actualList, expectedList, String.format("List is unexpected:\n%s - actual\n%s - expected\n", actualList, expectedList));
        return this;
    }

    public ResponseValidator verifyErrorMessage(String expectedMessage) {
        String actualMessage = ResponseValidator.parser.getError(this.response).errorMessage;
        Assert.assertEquals(actualMessage, expectedMessage, String.format("Error message is unexpected:\n%s - actual\n%s - expected\n", actualMessage, expectedMessage));
        return this;
    }
}