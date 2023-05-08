package validator;

import io.restassured.response.Response;
import models.book.Book;
import models.genre.Genre;
import org.testng.Assert;
import utils.ConvertResponseToModel;

public class GenreValidator {

    private final Response response;

    private static final ConvertResponseToModel parser = new ConvertResponseToModel();

    public GenreValidator(Response response) {
        this.response = response;
    }

    public GenreValidator verifyGenreExistsInResponse(int genreId) {    // Genre genre,
        Genre genre = GenreValidator.parser.getAsGenreClass(this.response);
        Assert.assertTrue(genre.getGenreId().equals(genreId), String.format("Genre doesn't contain %s", genreId));
        return this;
    }
}
