package apitests;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import models.QueryOptions;
import models.book.Additional;
import models.book.Book;
import models.book.Size;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import service.BookService;
import validator.BookValidator;
import validator.ResponseValidator;

import java.util.Arrays;
import java.util.List;

@Epic("Api testing of service REST Library API")
@Owner("Valerii Bahno")
public class BookTests {

    private final Faker faker = new Faker();
    private final BookService bookService = new BookService();

    private final int authorId = 1;
    private final int genreId = 3;

    private final String bookNameNew = faker.book().title();
    private final String bookDescriptionRandom = "Description about " + faker.book().title() + " " +
                                    faker.book().author() + " " + faker.book().genre();
    private final String bookNameNewUpdate = faker.book().title() + faker.book().publisher();
    private Book createdBook;
    private int idCreatedBook;

    @Test(priority = 1)
    @Description("Create new Book")
    public void verifyCreatedBook() {

        createdBook = Book.builder()
                .bookName(bookNameNew)
                .bookDescription(bookDescriptionRandom)
                .bookLanguage(faker.nation().language().toLowerCase())
                .additional(Additional.builder()
                        .pagesCount(faker.number().numberBetween(100, 300))
                        .size(Size.builder()
                                .height(faker.number().randomDouble(1, 1, 50))
                                .width(faker.number().randomDouble(1, 1, 50))
                                .length(faker.number().randomDouble(1, 1, 50))
                                .build())
                        .build())
                .publicationYear(faker.number().numberBetween(1900, 2000))
                .build();

        Response response = bookService.createBook(createdBook, authorId, genreId);
        idCreatedBook = response.body().as(Book.class).getBookId();

        createdBook.setBookId(idCreatedBook);

        new ResponseValidator(response).verifyStatusCode(HttpStatus.SC_CREATED);
        new BookValidator(response)
                .verifyBookExists(createdBook)
                .verifyBookName(bookNameNew);
    }

    @Test(priority = 2)
    @Description("Get one Book object by it 'bookId'")
    public void verifyGetBookById() {
        Response response = bookService.getBookId(idCreatedBook);
        new ResponseValidator(response).verifyStatusCode(HttpStatus.SC_OK);
        new BookValidator(response)
                .verifyBookExists(createdBook)
                .verifyBookName(bookNameNew);
    }

    @Test(priority = 3)
    @Description("Update existed book")
    public void verifyUpdateBook() {
        createdBook.setBookName(bookNameNewUpdate);
        Response response = bookService.updateBook(createdBook, idCreatedBook);
        new ResponseValidator(response).verifyStatusCode(HttpStatus.SC_OK);
        new BookValidator(response)
                .verifyBookExists(createdBook)
                .verifyBookName(bookNameNewUpdate);
    }

    @Test(priority = 4)
    @Description("Get Books in special Genre")
    public void verifyGetBookByGenreId() {
        QueryOptions options = new QueryOptions();
        options.setSortBy("bookId");
        Response response = bookService.getBookByGenreId(options, genreId);
        new ResponseValidator(response).verifyStatusCode(HttpStatus.SC_OK);
        new BookValidator(response)
                .verifyBooksNotEmpty()
                .verifyAnyBookNamesIncludeText(bookNameNewUpdate);
    }

    @Test(priority = 5)
    @Description("Get Books with pagination and sorting")
    public void verifyGetBooksByList() {
        int sizeList5 = 5;
        int sizeList10 = 10;
        int defaultPage = 1;
        Response response10 = bookService.getBooks(new QueryOptions(defaultPage, true, sizeList10));
        List<Book> listBooks10 = Arrays.asList(response10.getBody().as(Book[].class));
        Response response5 = bookService.getBooks(new QueryOptions(defaultPage, true, sizeList5));
        new ResponseValidator(response5).verifyStatusCode(HttpStatus.SC_OK);
        new BookValidator(response5)
                .verifyCountBooks(sizeList5)
                .verifyBiggerListBooksContainsLessListBooks(listBooks10);
    }

    @Test(priority = 6)
    @Description("Search for Books by name, return first 5 the most relevant results")
    public void verifyGetBookBySearch() {
        String partNameBook = "Distinctio";
        Response response = bookService.getBooksByPartName(partNameBook);
        new ResponseValidator(response).verifyStatusCode(HttpStatus.SC_OK);
        new BookValidator(response).verifyBookNamesIncludeText(partNameBook);
    }

    @Test(priority = 7)
    @Description("Get available sorters for Book")
    public void verifyGetSortersForBook() {
        String parameter = "defaultValue";
        List<String> expectedListDefaultValue = Arrays.asList("bookId", "bookName", "pagesCount", "volume", "square", "publicationYear");
        Response response = bookService.getSorters();
        new ResponseValidator(response)
                .verifyStatusCode(HttpStatus.SC_OK)
                .verifyListByParameterExists(parameter, expectedListDefaultValue);
    }

    @Test(priority = 8)
    @Description("Get Books of special Author in special Genre")
    public void verifyGetBookByAuthorIdAndGenreId() {
        Response response = bookService.getBookByAuthorIdAndGenreId(authorId, genreId);
        new ResponseValidator(response).verifyStatusCode(HttpStatus.SC_OK);
        new BookValidator(response)
                .verifyAnyBookNamesIncludeText(bookNameNewUpdate)
                .verifyBooksNotEmpty();
    }

    @Test(priority = 9)
    @Description("Get Books of special Author")
    public void verifyGetBookByAuthorId() {
        Response response = bookService.getBookByAuthorId(new QueryOptions(), authorId);
        new ResponseValidator(response).verifyStatusCode(HttpStatus.SC_OK);
        new BookValidator(response)
                .verifyAnyBookNamesIncludeText(bookNameNewUpdate)
                .verifyBooksNotEmpty();
    }

    @Test(priority = 10)
    @Description("Delete existed book")
    public void verifyDeleteBook() {
        Response response = bookService.deleteBook(idCreatedBook);
        new ResponseValidator(response).verifyStatusCode(HttpStatus.SC_NO_CONTENT);

        Response responseVerifyEntity = bookService.getBookId(idCreatedBook);
        new ResponseValidator(responseVerifyEntity)
                .verifyStatusCode(HttpStatus.SC_NOT_FOUND)
                .verifyErrorMessage(String.format("Book with 'bookId' = '%s' doesn't exist!", idCreatedBook));
    }

    @Test(priority = 11)
    @Description("Create new Book with incorrect body")
    public void verifyCreateIncorrectBook() {
        Response response = bookService.createBook(Book.builder().build(), authorId, genreId);
        new ResponseValidator(response)
                .verifyStatusCode(HttpStatus.SC_BAD_REQUEST)
                .verifyErrorMessage("Value 'bookName' is required!");
    }
}
