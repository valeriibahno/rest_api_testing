package service;

import client.RequestClient;
import io.restassured.response.Response;
import utils.EndPointBuilder;

public class GenreService {

    public Response getGenreByBookId(Integer bookId) {
        EndPointBuilder endpoint = new EndPointBuilder().pathParameter("book").pathParameter(bookId).pathParameter("genre");
        return RequestClient.get(endpoint.build());
    }
}
