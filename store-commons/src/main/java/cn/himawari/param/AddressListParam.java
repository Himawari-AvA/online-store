package cn.himawari.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class AddressListParam {
    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
}
