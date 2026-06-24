package responseObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Getter
@ToString
public class ResponseGetUser {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("books")
    private List<BookObject> books;
}
