package cn.himawari.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryParam {
    @JsonProperty("category_id")
    @NotNull
    private Integer categoryId;
}
