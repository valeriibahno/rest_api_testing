package models.book;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Size {

    public Double height;
    public Double width;
    public Double length;
}
