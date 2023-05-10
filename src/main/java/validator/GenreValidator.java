package validator;

import io.restassured.response.Response;
import models.genre.Genre;
import org.testng.Assert;
import utils.ConvertResponseToModel;

public class GenreValidator {

    private final Response response;

    private static final ConvertResponseToModel parser = new ConvertResponseToModel();

    public GenreValidator(Response response) {
        this.response = response;
    }

    public GenreValidator verifyGenreExistsInResponse(int genreId) {
        Genre genre = GenreValidator.parser.getAsGenreClass(this.response);
        Assert.assertEquals(genreId, (int) genre.getGenreId(), String.format("Genre doesn't contain %s", genreId));
        return this;
    }
}
