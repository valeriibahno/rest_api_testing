package models;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Builder
@ToString
public class ResponseError {

    public String timestamp;
    public Integer statusCode;
    public String error;
    public String errorMessage;
}
