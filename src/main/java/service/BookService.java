package service;

import client.RequestClient;
import io.restassured.response.Response;
import models.QueryOptions;
import models.book.Book;
import utils.EndPointBuilder;

public class BookService {

    public Response getBook(QueryOptions options) {
        EndPointBuilder endPoint = new EndPointBuilder().pathParameter("books");

        if(options.orderType != null) {
            endPoint.queryParam("orderType", options.orderType);
        }
        endPoint
                .queryParam("page", options.page)
                .queryParam("pagination", options.pagination)
                .queryParam("size", options.size);
        if(options.sortBy != null) {
            endPoint.queryParam("sortBy", options.sortBy);
        }
        return RequestClient.get(endPoint.build());
    }

    public Response getBookByGenreId(QueryOptions options, int idGenre) {
        EndPointBuilder endPoint = new EndPointBuilder().pathParameter("genre").pathParameter(idGenre).pathParameter("books");
        if(options.orderType != null) {
            endPoint.queryParam("orderType", options.orderType);
        }
        endPoint
                .queryParam("page", options.page)
                .queryParam("pagination", options.pagination)
                .queryParam("size", options.size);
        if(options.sortBy != null) {
            endPoint.queryParam("sortBy", options.sortBy);
        }
        return RequestClient.get(endPoint.build());
    }

    public Response getBookByPartName(QueryOptions options, String partName) {
        EndPointBuilder endPoint = new EndPointBuilder().pathParameter("books").pathParameter("search").queryParam("query", partName);
        if(options.orderType != null) {
            endPoint.queryParam("orderType", options.orderType);
        }
        endPoint
                .queryParam("page", options.page)
                .queryParam("pagination", options.pagination)
                .queryParam("size", options.size);
        if(options.sortBy != null) {
            endPoint.queryParam("sortBy", options.sortBy);
        }
        return RequestClient.get(endPoint.build());
    }

    public Response getBookByAuthorIdAndGenreId(int authorId, int genreId) {
        EndPointBuilder endPoint = new EndPointBuilder().pathParameter("author").pathParameter(authorId).pathParameter("genre").pathParameter(genreId).pathParameter("books");
        return RequestClient.get(endPoint.build());
    }

    public Response getBookByAuthorId(QueryOptions options,int authorId) {
        EndPointBuilder endPoint = new EndPointBuilder().pathParameter("author").pathParameter(authorId).pathParameter("books");
        if(options.orderType != null) {
            endPoint.queryParam("orderType", options.orderType);
        }
        if(options.sortBy != null) {
            endPoint.queryParam("sortBy", options.sortBy);
        }
        return RequestClient.get(endPoint.build());
    }

    public Response createBook(Book book, int authorId, int genreId) {
        String endPoint = new EndPointBuilder().pathParameter("book").queryParam("authorId", authorId).queryParam("genreId", genreId).build();
        return RequestClient.post(endPoint, book);
    }

    public Response updateBook(Book book, int bookId) {
        String endPoint = new EndPointBuilder().pathParameter("book").pathParameter(bookId).build();
        return RequestClient.put(endPoint, book);
    }

    public Response deleteBook(int bookId) {
        String endPoint = new EndPointBuilder().pathParameter("book").pathParameter(bookId).build();
        return RequestClient.delete(endPoint);
    }

    public Response getBookId(Integer id) {
        EndPointBuilder endPoint = new EndPointBuilder().pathParameter("book").pathParameter(id);
        return RequestClient.get(endPoint.build());
    }

    public Response getSorters() {
        EndPointBuilder endPoint = new EndPointBuilder().pathParameter("book").pathParameter("sorters");
        return RequestClient.get(endPoint.build());
    }
}
