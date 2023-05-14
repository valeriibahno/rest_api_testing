package models.book;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Additional {

    public Integer pagesCount;
    public Size size;
}
