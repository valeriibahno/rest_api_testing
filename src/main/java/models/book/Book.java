package models.book;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Book {

    public Integer bookId;
    public String bookName;
    public String bookDescription;
    public String bookLanguage;
    public Additional additional;
    public Integer publicationYear;
}
