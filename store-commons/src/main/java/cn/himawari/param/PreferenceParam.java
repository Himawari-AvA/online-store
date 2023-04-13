package cn.himawari.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class PreferenceParam {

    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
    @JsonProperty("category_id")
    @NotNull
    private Integer categoryId;
    @NotNull
    private Integer frequency;
}
