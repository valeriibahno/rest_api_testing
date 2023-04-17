package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class QueryOptions {

    public int genreId;
    public int authorId;
    public boolean pagination;
    public int page;
    public int size;
    public String sortBy;
    public String orderType;

    public QueryOptions() {
        this.page = 1;
        this.pagination = true;
        this.size = 10;
    }

    public QueryOptions(int page, boolean pagination, int size) {
        this.page = page;
        this.pagination = pagination;
        this.size = size;
    }
}
