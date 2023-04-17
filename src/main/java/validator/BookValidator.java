package validator;

import io.restassured.response.Response;
import models.book.Book;
import org.testng.Assert;
import utils.ConvertResponseToModel;

import java.util.List;

public class BookValidator {

    private final Response response;

    private static final ConvertResponseToModel parser = new ConvertResponseToModel();

    public BookValidator(Response response) {
        this.response = response;
    }

    public BookValidator verifyBookName(String expectedNameBook) {
        String actualBookName = BookValidator.parser.getAsBookClass(this.response).bookName;
        Assert.assertEquals(actualBookName, expectedNameBook, String.format("Book name is unexpected:\n%s - actual\n%s - expected\n", actualBookName, expectedNameBook));
        return this;
    }

    public BookValidator verifyCountBooks(int expectedCount) {
        List<Book> books = BookValidator.parser.getAsBookClassArray(this.response);
        int actualCount = books.size();
        Assert.assertEquals(actualCount, expectedCount, String.format("Books count is unexpected:\n%s - actual\n%s - expected\n", actualCount, expectedCount));
        return this;
    }

    public BookValidator verifyCountBooksNotNull() {
        List<Book> books = BookValidator.parser.getAsBookClassArray(this.response);
        Assert.assertTrue(books.size() > 0, "List of books is empty");
        return this;
    }

    public BookValidator verifyNameBookExistsInListBooks(String nameBook) {
        List<Book> books = BookValidator.parser.getAsBookClassArray(this.response);
        Assert.assertTrue(books.stream().allMatch(x -> x.getBookName().contains(nameBook)), String.format("Name of books don't contain %s", nameBook));
        return this;
    }
}
