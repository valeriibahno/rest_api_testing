package models.genre;

import lombok.*;

@AllArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Genre {
    public Integer genreId;
    public String name;
    public String description;
}