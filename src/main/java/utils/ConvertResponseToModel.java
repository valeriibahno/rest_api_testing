package utils;

import io.restassured.response.Response;
import models.ResponseError;
import models.book.Book;
import models.genre.Genre;

import java.util.Arrays;
import java.util.List;

public class ConvertResponseToModel {

    public List<Book> getAsBookClassArray(Response response) {
        return Arrays.asList(response.getBody().as(Book[].class));
    }

    public Book getAsBookClass(Response response) {
        return response.body().as(Book.class);
    }

    public Genre getAsGenreClass(Response response) {
        return response.body().as(Genre.class);
    }

    public ResponseError getError(Response response) {
        return response.body().as(ResponseError.class);
    }
}
