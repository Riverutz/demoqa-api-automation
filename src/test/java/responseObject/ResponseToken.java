package responseObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseToken {
    @JsonProperty
    public String token;

    @JsonProperty
    public String expires;

    @JsonProperty
    public String status;

    @JsonProperty
    public String result;
}
