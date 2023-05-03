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

    public BookValidator verifyBook(Book expectedBook) {
        Book actualBook = BookValidator.parser.getAsBookClass(this.response);
        Assert.assertEquals(actualBook, expectedBook, String.format("Book is unexpected:\n%s - actual\n%s - expected\n", actualBook, expectedBook));
        return this;
    }

    public BookValidator verifyCountBooks(int expectedCount) {
        List<Book> books = BookValidator.parser.getAsBookClassArray(this.response);
        int actualCount = books.size();
        Assert.assertEquals(actualCount, expectedCount, String.format("Books count is unexpected:\n%s - actual\n%s - expected\n", actualCount, expectedCount));
        return this;
    }

    public BookValidator verifyBooksNotEmpty() {
        List<Book> books = BookValidator.parser.getAsBookClassArray(this.response);
        Assert.assertTrue(books.size() > 0, "List of books is empty");
        return this;
    }

    public BookValidator verifyBookNamesIncludeText(String nameBook) {
        List<Book> books = BookValidator.parser.getAsBookClassArray(this.response);
        Assert.assertTrue(books.stream().allMatch(x -> x.getBookName().contains(nameBook)), String.format("Name of books doesn't contain %s", nameBook));
        return this;
    }

    public BookValidator verifyAnyBookNamesIncludeText(String nameBook) {
        List<Book> books = BookValidator.parser.getAsBookClassArray(this.response);
        Assert.assertTrue(books.stream().anyMatch(x -> x.getBookName().contains(nameBook)), String.format("Name of any books doesn't contain %s", nameBook));
        return this;
    }

    public BookValidator verifyTwoListBooksEqual(List<Book> listBooks1, List<Book> listBooks2) {
        Assert.assertEquals(listBooks1, listBooks2, "Two lists of books doesn't equal");
        return this;
    }
}
